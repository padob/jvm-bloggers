package com.jvm_bloggers.core.query;

import com.jvm_bloggers.entities.blog.Blog;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class NewPublishedBlog {

    public String author;
    public String url;
    public String authorTwitterHandle;
    public BlogTypeDto type;

    static NewPublishedBlog fromBlog(Blog blog) {
        return NewPublishedBlog.builder()
            .author(blog.getAuthor())
            .url(blog.getUrl())
            .authorTwitterHandle(blog.getTwitter())
            .type(BlogTypeDto.fromBlogType(blog.getBlogType()))
            .build();
    }

    static List<NewPublishedBlog> fromBlogs(List<Blog> blogs) {
        return blogs.stream()
            .map(NewPublishedBlog::fromBlog)
            .collect(Collectors.toList());
    }
}
