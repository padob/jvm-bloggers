package com.jvm_bloggers.frontend.public_area.newsletter_issue

import com.jvm_bloggers.MockSpringContextAwareSpecification
import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssue
import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssueFinder
import com.jvm_bloggers.frontend.public_area.newsletter_issue.newsletter_panel.NewsletterIssuePanel
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.request.mapper.parameter.PageParameters

import java.time.LocalDate

class NewsletterIssuePageSpec extends MockSpringContextAwareSpecification {

    PublishedNewsletterIssueFinder newsletterIssueService = Stub(PublishedNewsletterIssueFinder)

    @Override
    protected void setupContext() {
        addBean(newsletterIssueService)
    }

    def "Should display selected issue"() {
        given:
            PublishedNewsletterIssue issue = prepareExampleIssue()
            newsletterIssueService.findByIssueNumber(issue.number) >> Optional.of(issue)
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
                22, LocalDate.now(), "Example heading", "Example varia", Collections.emptyList(), Collections.emptyList()
        )
    }

    def "Should display 'No issue found' when there is issue with a given number"() {
        given:
            int issueNumber = 34
            newsletterIssueService.findByIssueNumber(issueNumber) >> Optional.empty()
        when:
            tester.startPage(NewsletterIssuePage, new PageParameters().set(0, issueNumber))
        then:
            tester.assertComponent(NewsletterIssuePage.ISSUE_PANEL_ID, Label)
            tester.assertContains("Nie znaleziono takiego wydania")
    }



}
