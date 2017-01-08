package com.jvm_bloggers.frontend.public_area.all_issues.all_issues_panel;

import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssue;

import javaslang.Tuple2;
import javaslang.collection.List;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

import java.time.YearMonth;

public class AllIssuesPanel extends Panel {

    public AllIssuesPanel(String id, List<Tuple2<YearMonth, List<PublishedNewsletterIssue>>> 
            issuesGroupedByYearMonth) {
        super(id);
        RepeatingView issuesInMonth = new RepeatingView("issuesInMonthPanel");
        issuesGroupedByYearMonth.forEach(tuple -> {
                issuesInMonth.add(
                    new IssuesInMonthPanel(
                        issuesInMonth.newChildId(), 
                        tuple._1, 
                        tuple._2.sortBy(i -> i.publishedDate)
                    )
                );
            }
        );
        add(issuesInMonth);
    }
    
}
