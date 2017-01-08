package com.jvm_bloggers.frontend.public_area.all_issues;

import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssue;
import com.jvm_bloggers.frontend.public_area.AbstractFrontendPage;
import com.jvm_bloggers.frontend.public_area.all_issues.all_issues_panel.AllIssuesPanel;
import com.jvm_bloggers.frontend.public_area.newsletter_issue.NewsletterIssuePage;

import javaslang.Tuple2;
import javaslang.collection.List;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import java.time.YearMonth;

import static com.jvm_bloggers.common.utils.DateTimeUtilities.DATE_FORMATTER;

@MountPath("all-issues")
public class AllIssuesPage extends AbstractFrontendPage {

    private static final String ALL_ISSUES_PANEL_ID = "allIssuesPanel";

    @SpringBean
    private AllIssuesPageRequestHandler requestHandler;

    public AllIssuesPage() {
        List<Tuple2<YearMonth, List<PublishedNewsletterIssue>>> issuesGroupedByYearMonth =
            createIssuesGroupedByMonth();
        add(new AllIssuesPanel(ALL_ISSUES_PANEL_ID, issuesGroupedByYearMonth));
    }

    private List<Tuple2<YearMonth, List<PublishedNewsletterIssue>>> createIssuesGroupedByMonth() {
        return requestHandler
            .findAllByOrderByPublishedDateDesc()
            .groupBy(this::getPublicationMonth)
            .toList()
            .sortBy(t -> t._1)
            .reverse();
    }

    private YearMonth getPublicationMonth(PublishedNewsletterIssue issue) {
        return YearMonth.from(issue.publishedDate); 
    }

    private Link<?> getLink(PublishedNewsletterIssue issue) {
        return (Link<?>) new BookmarkablePageLink<>("issueLink", NewsletterIssuePage.class,
            NewsletterIssuePage.buildShowIssueParams(issue.number))
            .setBody(Model.of(new StringResourceModel("all.issues.link.label")
                .setParameters(issue.number,
                    DATE_FORMATTER.format(issue.publishedDate))));
    }
}
