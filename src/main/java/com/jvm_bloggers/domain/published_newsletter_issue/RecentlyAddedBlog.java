package com.jvm_bloggers.domain.published_newsletter_issue;

import com.jvm_bloggers.entities.blog_posts.Blog;

import javaslang.collection.List;

import lombok.Builder;

@Builder
public class RecentlyAddedBlog {

    public String author;
    public String url;
    public String authorTwitterHandle;
    public boolean isPersonalBlog;
    public boolean isCompanyBlog;
    public boolean isVideoChannel;


    static RecentlyAddedBlog fromBlog(Blog blog) {
        return RecentlyAddedBlog.builder()
                .author(blog.getAuthor())
                .url(blog.getUrl())
                .authorTwitterHandle(blog.getTwitter())
                .isPersonalBlog(blog.isPersonal())
                .isCompanyBlog(blog.isCompany())
                .isVideoChannel(blog.isVideoChannel())
                .build();
    }

    static List<RecentlyAddedBlog> fromBlogs(List<Blog> blogs) {
        return blogs.map(RecentlyAddedBlog::fromBlog).toList();
    }
}
