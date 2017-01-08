package com.jvm_bloggers.frontend.public_area.all_issues;

import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssue;
import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssueFinder;

import javaslang.collection.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Service;

@Service
public class AllIssuesPageRequestHandler {

    @SpringBean
    private PublishedNewsletterIssueFinder publishedNewsletterIssueFinder;

    public AllIssuesPageRequestHandler(PublishedNewsletterIssueFinder newsletterIssueDtoService) {
        this.publishedNewsletterIssueFinder = newsletterIssueDtoService;
    }

    public List<PublishedNewsletterIssue> findAllByOrderByPublishedDateDesc() {
        return publishedNewsletterIssueFinder.findAllByOrderByPublishedDateDesc();
    }

}
