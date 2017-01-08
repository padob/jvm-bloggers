package com.jvm_bloggers.frontend.public_area;

import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssueFinder;
import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssue;
import javaslang.control.Option;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Service;


@Service
public class HomePageRequestHandler {

    @SpringBean
    private PublishedNewsletterIssueFinder publishedNewsletterIssueFinder;

    public HomePageRequestHandler(PublishedNewsletterIssueFinder publishedNewsletterIssueFinder) {
        this.publishedNewsletterIssueFinder = publishedNewsletterIssueFinder;
    }

    public Option<PublishedNewsletterIssue> getLatestIssue() {
        return publishedNewsletterIssueFinder.getLatestIssue();
    }
}
