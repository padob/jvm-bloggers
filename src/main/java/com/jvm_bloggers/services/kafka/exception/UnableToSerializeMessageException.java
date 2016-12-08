package com.jvm_bloggers.services.kafka.exception;

public class UnableToSerializeMessageException extends RuntimeException {
    public <T> UnableToSerializeMessageException(Throwable cause, T message) {
        super("Unable to serialize message : " + message, cause);
    }
}
