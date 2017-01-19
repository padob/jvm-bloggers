package com.jvm_bloggers.frontend.admin_area.blogs;

import com.jvm_bloggers.domain.posts_to_moderate.BlogPostToModerate;
import com.jvm_bloggers.domain.posts_to_moderate.BlogPostToModerateFinder;
import com.jvm_bloggers.entities.blog_posts.BlogRepository;
import com.jvm_bloggers.frontend.admin_area.PaginationConfiguration;

import lombok.RequiredArgsConstructor;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.springframework.data.domain.PageRequest;

import java.util.Iterator;
import java.util.Optional;

@RequiredArgsConstructor
public class BlogPostsPageRequestHandler implements IDataProvider<BlogPostToModerate> {

    private final PaginationConfiguration paginationConfiguration;
    private final BlogPostToModerateFinder blogPostToModerateFinder;
    private final BlogRepository blogRepository;
    private final Long blogId;

    @Override
    public Iterator<? extends BlogPostToModerate> iterator(long first, long count) {
        int page = Long.valueOf(first / paginationConfiguration.getDefaultPageSize()).intValue();
        return blogPostToModerateFinder
            .findByBlogIdOrderByPublishedDateDesc(blogId, new PageRequest(page,
                paginationConfiguration.getDefaultPageSize())
            ).iterator();
    }

    @Override
    public long size() {
        return blogPostToModerateFinder.countByBlogId(blogId);
    }

    @Override
    public IModel<BlogPostToModerate> model(BlogPostToModerate post) {
        return new BlogPostToModerateModel(post);
    }

    @Override
    public void detach() {

    }

    String getPageHeader() {
        return Optional.ofNullable(blogRepository.findOne(blogId))
            .map(b -> b.getAuthor() + "'s posts")
            .orElse("No such blog found");
    }
}
