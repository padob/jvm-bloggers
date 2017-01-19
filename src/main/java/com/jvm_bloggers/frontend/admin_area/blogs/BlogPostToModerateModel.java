package com.jvm_bloggers.frontend.admin_area.blogs;

import com.jvm_bloggers.domain.posts_to_moderate.BlogPostToModerate;
import com.jvm_bloggers.domain.posts_to_moderate.BlogPostToModerateFinder;

import lombok.extern.slf4j.Slf4j;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

@Slf4j
public class BlogPostToModerateModel extends LoadableDetachableModel<BlogPostToModerate> {

    @SpringBean
    private BlogPostToModerateFinder finder;

    private Long blogPostId;

    public BlogPostToModerateModel(BlogPostToModerate blogPost) {
        super(blogPost);
        Injector.get().inject(this);
        blogPostId = blogPost.getId();
    }

    @Override
    protected BlogPostToModerate load() {
        log.debug("Loading post with id " + blogPostId);
        return finder.findById(blogPostId);
    }
    
}
