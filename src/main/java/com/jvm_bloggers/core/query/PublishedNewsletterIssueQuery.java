package com.jvm_bloggers.core.query;

import com.jvm_bloggers.entities.newsletter_issue.NewsletterIssueRepository;
import javaslang.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublishedNewsletterIssueQuery {

    private final NewsletterIssueRepository newsletterIssueRepository;
    private final PublishedNewsletterIssueBuilder builder;

    @Autowired
    public PublishedNewsletterIssueQuery(NewsletterIssueRepository newsletterIssueRepository,
                                         PublishedNewsletterIssueBuilder builder) {
        this.newsletterIssueRepository = newsletterIssueRepository;
        this.builder = builder;
    }

    public Option<PublishedNewsletterIssue> findBy(NewsletterIssueNumber number) {
        return newsletterIssueRepository.findByIssueNumber(number.asLong())
                .map(builder::build);
    }
}
