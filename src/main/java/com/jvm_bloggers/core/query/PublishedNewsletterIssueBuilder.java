package com.jvm_bloggers.core.query;

import com.google.common.annotations.VisibleForTesting;
import com.jvm_bloggers.core.blogpost_redirect.LinkGenerator;
import com.jvm_bloggers.entities.blog_post.BlogPost;
import com.jvm_bloggers.entities.newsletter_issue.NewsletterIssue;
import com.jvm_bloggers.entities.newsletter_issue.NewsletterIssueBaseData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.jvm_bloggers.core.query.NewPublishedBlog.fromBlogs;
import static com.jvm_bloggers.core.query.NewsletterIssueNumber.of;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PublishedNewsletterIssueBuilder {

    private final LinkGenerator linkGenerator;

    public PublishedNewsletterIssue build(NewsletterIssue issue) {
        return PublishedNewsletterIssue.builder()
            .number(of(issue.getIssueNumber()))
            .headingSection(issue.getHeading())
            .variaSection(issue.getVaria())
            .publishedDate(issue.getPublishedDate())
            .newBlogs(fromBlogs(issue.getNewBlogs()))
            .personalPosts(getPersonalPosts(issue.getBlogPosts()))    
                
            .posts(fromBlogPosts(issue.getBlogPosts()))
            .build();
    }

    public List<PublishedPost> getPersonalPosts(java.util.List<BlogPost> blogPosts) {
        return filterBlogPosts(BlogTypeDto.PERSONAL);
    }

    public List<PublishedPost> getCompaniesPosts() {
        return filterBlogPosts(BlogTypeDto.COMPANY);
    }

    public List<PublishedPost> getVideos() {
        return filterBlogPosts(BlogTypeDto.VIDEOS);
    }

    private List<PublishedPost> filterBlogPosts(BlogTypeDto type) {
        return publishedPosts.stream().filter(p -> p.blogType == type).collect(Collectors.toList());
    }

    public PublishedNewsletterIssue build(NewsletterIssueBaseData issue) {
        return PublishedNewsletterIssue.builder()
            .number(issue.getIssueNumber())
            .publishedDate(issue.getPublishedDate())
            .build();
    }

    private List<PublishedPost> fromBlogPosts(List<BlogPost> blogPosts) {
        return blogPosts.stream()
            .map(this::fromBlogPost)
            .collect(Collectors.toList());
    }

    @VisibleForTesting
    PublishedPost fromBlogPost(BlogPost blogPost) {
        return PublishedPost.builder()
            .url(linkGenerator.generateRedirectLinkFor(blogPost.getUid()))
            .title(blogPost.getTitle())
            .authorName(blogPost.getBlog().getAuthor())
            .authorTwitterHandle(blogPost.getBlog().getTwitter())
            .blogType(BlogTypeDto.fromBlogType(blogPost.getBlog().getBlogType()))
            .build();
    }

}
