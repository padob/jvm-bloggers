package com.jvm_bloggers.frontend.public_area.newsletter_issue.newsletter_panel;

import com.jvm_bloggers.domain.published_newsletter_issue.RecentlyAddedBlog;

import javaslang.collection.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

public class BlogLinksSection extends Panel {

    public BlogLinksSection(String id, List<RecentlyAddedBlog> blogs) {
        super(id);
        add(new Label("sectionHeading", "Nowe blogi i kanały video"));
        add(new ListView<RecentlyAddedBlog>("blogItems", blogs.toJavaList()) {
            @Override
            protected void populateItem(ListItem<RecentlyAddedBlog> item) {
                RecentlyAddedBlog blog = item.getModelObject();
                item.add(new ExternalLink("blogLink", blog.url, blog.author));
            }
        });

        setVisible(!blogs.isEmpty());
    }
}
