package com.jvm_bloggers.domain.published_newsletter_issue;


import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@Builder
public class PublishedNewsletterIssue {

    public long number;
    public LocalDate publishedDate;
    public String heading;
    public String varia;
    public List<PublishedPost> posts;
    public List<RecentlyAddedBlog> newBlogs;

    public List<PublishedPost> getPersonalPosts() {
        return posts.stream().filter(post -> post.isPersonalBlog).collect(toList());
    }

    public List<PublishedPost> getCompaniesPosts() {
        return posts.stream().filter(post -> post.isCompanyBlog).collect(toList());
    }

    public List<PublishedPost> getVideos() {
        return posts.stream().filter(post -> post.isVideoChannel).collect(toList());
    }

}
