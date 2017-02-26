package com.jvm_bloggers.entities.newsletter_issue;

import javaslang.control.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsletterIssueRepository extends JpaRepository<NewsletterIssue, Long> {

    Option<NewsletterIssue> findByIssueNumber(Long issueNumber);

    Optional<NewsletterIssue> findFirstByOrderByPublishedDateDesc();

    List<NewsletterIssueBaseData> findTop5ByOrderByPublishedDateDesc();
    
    List<NewsletterIssue> findAllByOrderByPublishedDateDesc();
}
