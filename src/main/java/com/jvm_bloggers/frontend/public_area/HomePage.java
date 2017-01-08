package com.jvm_bloggers.frontend.public_area;

import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssue;
import com.jvm_bloggers.frontend.public_area.newsletter_issue.newsletter_panel.NewsletterIssuePanel;
import javaslang.control.Option;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class HomePage extends AbstractFrontendPage {

    static final String LATEST_ISSUE_PANEL_ID = "latestIssuePanel";

    @SpringBean
    private HomePageRequestHandler requestHandler;

    public HomePage() {
        Option<PublishedNewsletterIssue> latestIssue = requestHandler.getLatestIssue();
        if (latestIssue.isDefined()) {
            add(new NewsletterIssuePanel(LATEST_ISSUE_PANEL_ID, latestIssue.get()));
        } else {
            add(new Label(LATEST_ISSUE_PANEL_ID, "Nie znalezion żadnego newslettera"));
        }
    }

}
