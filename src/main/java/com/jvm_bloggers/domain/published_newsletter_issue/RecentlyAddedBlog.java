package com.jvm_bloggers.domain.published_newsletter_issue;

import com.jvm_bloggers.entities.blog_posts.Blog;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

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
        return blogs.stream()
                .map(RecentlyAddedBlog::fromBlog)
                .collect(Collectors.toList());
    }
}
