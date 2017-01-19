package com.jvm_bloggers.domain.posts_to_moderate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Getter
@ToString
@AllArgsConstructor
public class BlogPostToModerate {

    private long id;
    private String title;
    private String url;
    private String authorName;
    private LocalDateTime datePublished;
    private LocalDateTime dateApproved;
    private Boolean approved;

    public String getApprovalState() {
        if (approved == null) {
            return " -- ";
        } else if (approved) {
            return "Approved";
        } else {
            return "Rejected";
        }
    }

    public boolean isGoingInNewsletter(final LocalDateTime lastPublicationDate) {
        return isApproved() && dateApproved.isAfter(lastPublicationDate);
    }

    public boolean isApproved() {
        return TRUE.equals(approved);
    }

    public boolean isRejected() {
        return FALSE.equals(approved);
    }

}
