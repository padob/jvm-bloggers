package com.jvm_bloggers.frontend.public_area.newsletter_issue.newsletter_panel;

import com.jvm_bloggers.core.query.NewPublishedBlog;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.List;

public class BlogLinksSection extends Panel {

    public BlogLinksSection(String id, List<NewPublishedBlog> blogs) {
        super(id);
        add(new Label("sectionHeading", "Nowe blogi i kanały video"));
        add(new ListView<NewPublishedBlog>("blogItems", blogs) {
            @Override
            protected void populateItem(ListItem<NewPublishedBlog> item) {
                NewPublishedBlog blog = item.getModelObject();
                item.add(new ExternalLink("blogLink", blog.url, blog.author));
            }
        });

        setVisible(!blogs.isEmpty());
    }
}
