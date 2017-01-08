package com.jvm_bloggers.domain.published_newsletter_issue;


import javaslang.collection.List;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public class PublishedNewsletterIssue {

    public long number;
    public LocalDate publishedDate;
    public String heading;
    public String varia;
    public List<PublishedPost> posts;
    public List<RecentlyAddedBlog> newBlogs;

    public List<PublishedPost> getPersonalPosts() {
        return posts.filter(post -> post.isPersonalBlog).toList();
    }

    public List<PublishedPost> getCompaniesPosts() {
        return posts.filter(post -> post.isCompanyBlog).toList();
    }

    public List<PublishedPost> getVideos() {
        return posts.filter(post -> post.isVideoChannel).toList();
    }

}
