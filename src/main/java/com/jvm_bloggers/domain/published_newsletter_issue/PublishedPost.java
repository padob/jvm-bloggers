package com.jvm_bloggers.domain.published_newsletter_issue;

import lombok.Builder;

@Builder
public class PublishedPost {

    public String url;
    public String title;
    public String authorName;
    public String authorTwitterHandle;
    public boolean isPersonalBlog;
    public boolean isCompanyBlog;
    public boolean isVideoChannel;

}
