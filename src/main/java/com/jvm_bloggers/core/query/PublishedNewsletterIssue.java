package com.jvm_bloggers.core.query;

import javaslang.collection.List;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class PublishedNewsletterIssue {

    private NewsletterIssueNumber number;
    private LocalDate publishedDate;
    private String headingSection;
    private String variaSection;
    private List<PublishedPost> personalPosts;
    private List<PublishedPost> companyPosts;
    private List<PublishedPost> videos;
    private List<NewPublishedBlog> newBlogs;

}
