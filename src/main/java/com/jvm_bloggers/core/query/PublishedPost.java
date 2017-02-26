package com.jvm_bloggers.core.query;

import lombok.Builder;

@Builder
public class PublishedPost {

    public String url;
    public String title;
    public String authorName;
    public String authorTwitterHandle;
    public BlogTypeDto blogType;

}
