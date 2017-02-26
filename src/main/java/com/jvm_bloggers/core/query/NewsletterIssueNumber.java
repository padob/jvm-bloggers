package com.jvm_bloggers.core.query;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class NewsletterIssueNumber {
    
    private final Long value;
    
    public static NewsletterIssueNumber of(Long value) {
        return new NewsletterIssueNumber(value);
    }
    
    public Long asLong() {
        return value;
    }
    
}
