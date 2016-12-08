package com.jvm_bloggers.services.kafka.message;

import lombok.Value;

@KafkaMessage
@Value
public class NewIssuePublishedMessage {
    private Long issueNumber;
    private String url;
}
