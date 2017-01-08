package com.jvm_bloggers.domain.published_newsletter_issue;

import com.jvm_bloggers.entities.newsletter_issues.NewsletterIssue;
import com.jvm_bloggers.entities.newsletter_issues.NewsletterIssueRepository;
import javaslang.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PublishedNewsletterIssueFinder {

    private final NewsletterIssueRepository newsletterIssueRepository;
    private final PublishedNewsletterIssueBuilder publishedNewsletterIssueBuilder;

    public Option<PublishedNewsletterIssue> getLatestIssue() {
        Option<NewsletterIssue> latestIssue = Option.ofOptional(
                newsletterIssueRepository.findFirstByOrderByPublishedDateDesc()
        );
        return latestIssue.map(publishedNewsletterIssueBuilder::build);
    }

    public Option<PublishedNewsletterIssue> findByIssueNumber(long issueNumber) {
        Option<NewsletterIssue> issue = Option.ofOptional(
                newsletterIssueRepository.findByIssueNumber(issueNumber)
        );
        return issue.map(publishedNewsletterIssueBuilder::build);
    }

    public List<PublishedNewsletterIssue> findTop5ByOrderByPublishedDateDesc() {
        return newsletterIssueRepository.findTop5ByOrderByPublishedDateDesc()
                .stream()
                .map(publishedNewsletterIssueBuilder::build)
                .collect(Collectors.toList());
    }

    public List<PublishedNewsletterIssue> findAllByOrderByPublishedDateDesc() {
        return newsletterIssueRepository.findAllByOrderByPublishedDateDesc()
                .stream()
                .map(publishedNewsletterIssueBuilder::build)
                .collect(Collectors.toList());
    }

}
