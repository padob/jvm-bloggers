package com.jvm_bloggers.frontend.public_area.newsletter_issue


import com.jvm_bloggers.MockSpringContextAwareSpecification
import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssue
import com.jvm_bloggers.frontend.public_area.common_layout.RightFrontendSidebarRequestHandler
import com.jvm_bloggers.frontend.public_area.newsletter_issue.newsletter_panel.NewsletterIssuePanel
import javaslang.collection.List as JavaslangList
import javaslang.control.Option
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.request.mapper.parameter.PageParameters

import java.time.LocalDate

class NewsletterIssuePageSpec extends MockSpringContextAwareSpecification {

    NewsletterIssuePageRequestHandler requestHandler = Stub(NewsletterIssuePageRequestHandler)
    RightFrontendSidebarRequestHandler rightFrontendSidebarRequestHandler = Stub(RightFrontendSidebarRequestHandler)

    @Override
    protected void setupContext() {
        addBean(requestHandler)
        addBean(rightFrontendSidebarRequestHandler)
    }

    def "Should display selected issue"() {
        given:
            PublishedNewsletterIssue issue = prepareExampleIssue()
            requestHandler.findByIssueNumber(issue.number) >> Option.of(issue)
        when:
            tester.startPage(NewsletterIssuePage, new PageParameters().set(0, issue.number))
        then:
            tester.assertComponent(NewsletterIssuePage.ISSUE_PANEL_ID, NewsletterIssuePanel)
            tester.assertContains("Wydanie #$issue.number")
            tester.assertContains("$issue.heading")
            tester.assertContains("$issue.varia")
    }

    private PublishedNewsletterIssue prepareExampleIssue() {
        return new PublishedNewsletterIssue(
                22, LocalDate.now(), "Example heading", "Example varia", JavaslangList.empty(), JavaslangList.empty()
        )
    }

    def "Should display 'No issue found' when there is issue with a given number"() {
        given:
            int issueNumber = 34
            requestHandler.findByIssueNumber(issueNumber) >> Option.none()
        when:
            tester.startPage(NewsletterIssuePage, new PageParameters().set(0, issueNumber))
        then:
            tester.assertComponent(NewsletterIssuePage.ISSUE_PANEL_ID, Label)
            tester.assertContains("Nie znaleziono wydania nr " + issueNumber)
    }

}
