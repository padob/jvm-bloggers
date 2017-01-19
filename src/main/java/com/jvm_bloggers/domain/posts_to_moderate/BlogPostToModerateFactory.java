package com.jvm_bloggers.domain.posts_to_moderate;


import com.jvm_bloggers.entities.blog_posts.BlogPost;

import org.springframework.stereotype.Component;

@Component
public class BlogPostToModerateFactory {

    public BlogPostToModerate create(BlogPost post) {
        return  new BlogPostToModerate(
            post.getId(),
            post.getTitle(),
            post.getUrl(),
            post.getBlog().getAuthor(),
            post.getPublishedDate(),
            post.getApprovedDate(),
            post.getApproved()
        );
    }

}
