package com.jvm_bloggers.frontend.public_area.all_issues.all_issues_panel;

import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssue;
import com.jvm_bloggers.frontend.public_area.newsletter_issue.NewsletterIssuePage;

import javaslang.collection.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.time.YearMonth;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ofPattern;

public class IssuesInMonthPanel extends Panel {

    public IssuesInMonthPanel(String id, 
                              YearMonth yearMonth, 
                              List<PublishedNewsletterIssue> issuesInMonth) {
        super(id);
        add(new Label("groupLabel", createPolishMonthYearLabelValue(yearMonth)));
        add(new ListView<PublishedNewsletterIssue>("issuesList", issuesInMonth.toJavaList()) {
            @Override
            protected void populateItem(ListItem<PublishedNewsletterIssue> item) {
                item.add(
                    new BookmarkablePageLink<NewsletterIssuePage>(
                        "issueLink", 
                        NewsletterIssuePage.class,
                        new PageParameters().set(0, item.getModelObject().number)
                    )
                );
            }
        });    
    }

    private String createPolishMonthYearLabelValue(YearMonth yearMonth) {
        return yearMonth.format(ofPattern("MMMM YYYY", new Locale("pl")));
    }
    
}
