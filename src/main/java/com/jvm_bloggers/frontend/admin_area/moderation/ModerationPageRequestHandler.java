package com.jvm_bloggers.frontend.admin_area.moderation;

import com.jvm_bloggers.domain.posts_to_moderate.BlogPostToModerate;
import com.jvm_bloggers.domain.posts_to_moderate.BlogPostToModerateFinder;
import com.jvm_bloggers.frontend.admin_area.PaginationConfiguration;
import com.jvm_bloggers.frontend.admin_area.blogs.BlogPostToModerateModel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ModerationPageRequestHandler implements IDataProvider<BlogPostToModerate> {

    private final BlogPostToModerateFinder blogPostToModerateFinder;
    private final PaginationConfiguration paginationConfiguration;

    @Override
    public Iterator<? extends BlogPostToModerate> iterator(long first, long count) {
        int page = Long.valueOf(first / paginationConfiguration.getDefaultPageSize()).intValue();
        return blogPostToModerateFinder
            .findLatestPosts(new PageRequest(page, paginationConfiguration.getDefaultPageSize()))
            .iterator();
    }

    @Override
    public long size() {
        return blogPostToModerateFinder.count();
    }

    @Override
    public IModel<BlogPostToModerate> model(BlogPostToModerate object) {
        return new BlogPostToModerateModel(object);
    }

    @Override
    public void detach() {

    }
}
