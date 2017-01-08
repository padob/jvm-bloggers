package com.jvm_bloggers.domain.published_newsletter_issue

import com.jvm_bloggers.entities.blog_posts.Blog
import com.jvm_bloggers.entities.blog_posts.BlogType
import spock.lang.Specification

import static java.time.LocalDateTime.now

class RecentlyAddedBlogSpec extends Specification {

    def "Should convert blog to its DTO representation"() {
        given:
            Blog blog = new Blog(1, 2, "some author", "some rss", "some url",
                    "some twitter", now(), BlogType.PERSONAL, true)
        when:
            RecentlyAddedBlog recentlyAddedBlog = RecentlyAddedBlog.fromBlog(blog)
        then:
            recentlyAddedBlog.author == blog.getAuthor()
            recentlyAddedBlog.url == blog.getUrl()
            recentlyAddedBlog.authorTwitterHandle == blog.getTwitter()
            recentlyAddedBlog.isPersonalBlog
            !recentlyAddedBlog.isCompanyBlog
            !recentlyAddedBlog.isVideoChannel
    }

}
