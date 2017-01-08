package com.jvm_bloggers.frontend.public_area.common_layout;

import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssue;
import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssueFinder;

import javaslang.collection.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Service;

@Service
public class RightFrontendSidebarRequestHandler {

    @SpringBean
    private PublishedNewsletterIssueFinder newsletterIssueDtoService;


    public RightFrontendSidebarRequestHandler(
        PublishedNewsletterIssueFinder newsletterIssueDtoService) {
        this.newsletterIssueDtoService = newsletterIssueDtoService;
    }

    public List<PublishedNewsletterIssue> findLatestFiveIssues() {
        return newsletterIssueDtoService.findTop5ByOrderByPublishedDateDesc();
    }
}
