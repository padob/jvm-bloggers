package com.jvm_bloggers.frontend.public_area.newsletter_issue;

import com.jvm_bloggers.core.query.PublishedNewsletterIssue;
import com.jvm_bloggers.core.query.PublishedNewsletterIssueBuilder;
import com.jvm_bloggers.entities.newsletter_issue.NewsletterIssue;
import com.jvm_bloggers.entities.newsletter_issue.NewsletterIssueRepository;
import javaslang.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NewsletterIssueDtoService {

    private final NewsletterIssueRepository newsletterIssueRepository;
    private final PublishedNewsletterIssueBuilder publishedNewsletterIssueBuilder;

    public Optional<PublishedNewsletterIssue> getLatestIssue() {
        Optional<NewsletterIssue> latestIssue = newsletterIssueRepository
            .findFirstByOrderByPublishedDateDesc();
        return latestIssue.map(publishedNewsletterIssueBuilder::build);
    }

    public Option<PublishedNewsletterIssue> findByIssueNumber(long issueNumber) {
        Option<NewsletterIssue> issue = newsletterIssueRepository.findByIssueNumber(issueNumber);
        return issue.map(publishedNewsletterIssueBuilder::build);
    }

    public List<PublishedNewsletterIssue> findTop5ByOrderByPublishedDateDesc() {
        return newsletterIssueRepository.findTop5ByOrderByPublishedDateDesc()
            .stream().map(publishedNewsletterIssueBuilder::build).collect(Collectors.toList());
    }

    public List<PublishedNewsletterIssue> findAllByOrderByPublishedDateDesc() {
        return newsletterIssueRepository.findAllByOrderByPublishedDateDesc().stream()
            .map(publishedNewsletterIssueBuilder::build)
            .collect(Collectors.toList());
    }

}
