package com.jvm_bloggers.domain.published_newsletter_issue

import com.jvm_bloggers.core.blogpost_redirect.LinkGenerator
import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssue
import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssueBuilder
import com.jvm_bloggers.domain.published_newsletter_issue.PublishedPost
import com.jvm_bloggers.entities.blog_posts.Blog
import com.jvm_bloggers.entities.blog_posts.BlogPost
import com.jvm_bloggers.entities.blog_posts.BlogType
import com.jvm_bloggers.entities.newsletter_issues.NewsletterIssue
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

import static java.time.LocalDateTime.now

class PublishedNewsletterIssueBuilderSpec extends Specification {

    static final String SHORT_URL = "http://shortlink.com"

    LinkGenerator generator = Stub(LinkGenerator) {
        generateRedirectLinkFor(_ as String ) >> SHORT_URL
    }

    @Subject
    PublishedNewsletterIssueBuilder builder = new PublishedNewsletterIssueBuilder(generator)

    def "Should convert newsletter issue to its DTO representation"() {
        given:
            Blog blog = sampleBlog()
            BlogPost post = sampleBlogPost(blog)
            NewsletterIssue issue = new NewsletterIssue(1, 2, LocalDate.now(), "Some heading", [post],
                    [blog], "Some varia")
        when:
            PublishedNewsletterIssue publishedIssue = builder.build(issue)
        then:
            publishedIssue.heading == issue.getHeading()
            publishedIssue.varia == issue.getVaria()
            publishedIssue.number == issue.getIssueNumber()
            publishedIssue.publishedDate == issue.getPublishedDate()
            publishedIssue.newBlogs.first().author == blog.getAuthor()
            publishedIssue.posts.first().title == post.getTitle()
    }

    def "Should convert blog post to its DTO representation"() {
        given:
            Blog blog = sampleBlog()
            BlogPost post = sampleBlogPost(blog)
        when:
            PublishedPost publishedPost = builder.fromBlogPost(post)
        then:
            publishedPost.url == SHORT_URL
            publishedPost.authorName == blog.getAuthor()
            publishedPost.authorTwitterHandle == blog.getTwitter()
            publishedPost.title == post.getTitle()
            publishedPost.isPersonalBlog
    }

    private BlogPost sampleBlogPost(Blog blog) {
        BlogPost.builder()
                .id(1)
                .title("some title")
                .description("some description")
                .url("some url")
                .publishedDate(now())
                .approvedDate(now())
                .approved(true)
                .blog(blog)
                .build()
    }

    private Blog sampleBlog(){
        Blog.builder()
                .id(1)
                .jsonId(2)
                .author("some author")
                .rss("some rss")
                .url("some url")
                .twitter("some twitter")
                .dateAdded(now())
                .blogType(BlogType.PERSONAL)
                .active(true)
                .build()
    }
}
