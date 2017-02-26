package com.jvm_bloggers.frontend.public_area.newsletter_issue

import com.jvm_bloggers.core.blogpost_redirect.LinkGenerator
import com.jvm_bloggers.core.query.PublishedPost
import com.jvm_bloggers.core.query.BlogTypeDto
import com.jvm_bloggers.core.query.PublishedNewsletterIssue
import com.jvm_bloggers.core.query.PublishedNewsletterIssueBuilder
import com.jvm_bloggers.entities.blog.Blog
import com.jvm_bloggers.entities.blog.BlogType
import com.jvm_bloggers.entities.blog_post.BlogPost
import com.jvm_bloggers.entities.newsletter_issue.NewsletterIssue
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

import static java.time.LocalDateTime.now

class NewsletterIssueDtoBuilderSpec extends Specification {

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
            NewsletterIssue issue = new NewsletterIssue(1, 2, LocalDate.now(), "Some headingSection", [post],
                    [blog], "Some variaSection")
        when:
            PublishedNewsletterIssue issueDto = builder.build(issue)
        then:
            issueDto.headingSection == issue.getHeading()
            issueDto.variaSection == issue.getVaria()
            issueDto.number == issue.getIssueNumber()
            issueDto.publishedDate == issue.getPublishedDate()
            issueDto.newBlogs.first().author == blog.getAuthor()
            issueDto.publishedPosts.first().title == post.getTitle()
    }

    def "Should convert blog post to its DTO representation"() {
        given:
            Blog blog = sampleBlog()
            BlogPost post = sampleBlogPost(blog)
        when:
            PublishedPost blogPostJson = builder.fromBlogPost(post)
        then:
            blogPostJson.url == SHORT_URL
            blogPostJson.authorName == blog.getAuthor()
            blogPostJson.authorTwitterHandle == blog.getTwitter()
            blogPostJson.blogType == BlogTypeDto.fromBlogType(blog.getBlogType())
            blogPostJson.title == post.getTitle()
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
