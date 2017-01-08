package com.jvm_bloggers.frontend.public_area.newsletter_issue;

import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssue;
import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssueFinder;

import javaslang.control.Option;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Service;

@Service
public class NewsletterIssuePageRequestHandler {

    @SpringBean
    private PublishedNewsletterIssueFinder publishedNewsletterIssueFinder;

    public NewsletterIssuePageRequestHandler(
        PublishedNewsletterIssueFinder publishedNewsletterIssueFinder
    ) {
        this.publishedNewsletterIssueFinder = publishedNewsletterIssueFinder;
    }

    public Option<PublishedNewsletterIssue> findByIssueNumber(long issueNumber) {
        return publishedNewsletterIssueFinder.findByIssueNumber(issueNumber);
    }
    
}
