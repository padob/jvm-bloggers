package com.jvm_bloggers.domain.published_newsletter_issue;

import com.google.common.annotations.VisibleForTesting;
import com.jvm_bloggers.core.blogpost_redirect.LinkGenerator;
import com.jvm_bloggers.entities.blog_posts.BlogPost;
import com.jvm_bloggers.entities.newsletter_issues.NewsletterIssue;
import com.jvm_bloggers.entities.newsletter_issues.NewsletterIssueBaseData;

import javaslang.collection.List;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PublishedNewsletterIssueBuilder {

    private final LinkGenerator linkGenerator;

    public PublishedNewsletterIssue build(NewsletterIssue issue) {
        return PublishedNewsletterIssue.builder()
            .number(issue.getIssueNumber())
            .heading(issue.getHeading())
            .varia(issue.getVaria())
            .publishedDate(issue.getPublishedDate())
            .newBlogs(RecentlyAddedBlog.fromBlogs(List.ofAll(issue.getNewBlogs())))
            .posts(fromBlogPosts(List.ofAll(issue.getBlogPosts())))
            .build();
    }

    public PublishedNewsletterIssue build(NewsletterIssueBaseData issue) {
        return PublishedNewsletterIssue.builder()
            .number(issue.getIssueNumber())
            .publishedDate(issue.getPublishedDate())
            .build();
    }

    private List<PublishedPost> fromBlogPosts(List<BlogPost> blogPosts) {
        return blogPosts
            .map(this::fromBlogPost)
            .toList();
    }

    @VisibleForTesting
    PublishedPost fromBlogPost(BlogPost blogPost) {
        return PublishedPost.builder()
            .url(linkGenerator.generateRedirectLinkFor(blogPost.getUid()))
            .title(blogPost.getTitle())
            .authorName(blogPost.getBlog().getAuthor())
            .authorTwitterHandle(blogPost.getBlog().getTwitter())
            .isPersonalBlog(blogPost.getBlog().isPersonal())
            .isCompanyBlog(blogPost.getBlog().isCompany())
            .isVideoChannel(blogPost.getBlog().isVideoChannel())
            .build();
    }

}
