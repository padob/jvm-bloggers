package com.jvm_bloggers.entities.newsletter_issues;

import java.time.LocalDate;

public interface NewsletterIssueBaseData {

    Long getId();

    Long getIssueNumber();

    LocalDate getPublishedDate();
}
