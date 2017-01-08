package com.jvm_bloggers.frontend.public_area

import com.jvm_bloggers.MockSpringContextAwareSpecification
import com.jvm_bloggers.frontend.public_area.common_layout.RightFrontendSidebar
import com.jvm_bloggers.domain.published_newsletter_issue.PublishedNewsletterIssue
import com.jvm_bloggers.frontend.public_area.common_layout.RightFrontendSidebarRequestHandler
import com.jvm_bloggers.frontend.public_area.newsletter_issue.newsletter_panel.NewsletterIssuePanel
import javaslang.control.Option
import javaslang.collection.List as JavaslangList
import org.apache.wicket.markup.html.basic.Label

import java.text.NumberFormat
import java.time.LocalDate

class HomePageSpec extends MockSpringContextAwareSpecification {

    HomePageRequestHandler homePageRequestHandler = Stub(HomePageRequestHandler)
    RightFrontendSidebarRequestHandler rightFrontendSidebarRequestHandler = Stub(RightFrontendSidebarRequestHandler)

    @Override
    protected void setupContext() {
        addBean(homePageRequestHandler)
        addBean(rightFrontendSidebarRequestHandler)
    }

    def "Should display latest issue"() {
        given:
            PublishedNewsletterIssue issue = prepareExampleIssue()
            homePageRequestHandler.getLatestIssue() >> Option.of(issue)
        when:
            tester.startPage(HomePage)
        then:
            tester.assertComponent(HomePage.LATEST_ISSUE_PANEL_ID, NewsletterIssuePanel)
            tester.assertContains("Wydanie #$issue.number")
            tester.assertContains("$issue.heading")
            tester.assertContains("$issue.varia")
    }

    private PublishedNewsletterIssue prepareExampleIssue() {
        return new PublishedNewsletterIssue(
                22, LocalDate.now(), "Example heading", "Example varia", JavaslangList.empty(), JavaslangList.empty()
        )
    }

    def "Should display 'No issue found' when there are no issues"() {
        given:
            mockEmptyLatestIssue()
        when:
            tester.startPage(HomePage)
        then:
            tester.assertComponent(HomePage.LATEST_ISSUE_PANEL_ID, Label)
            tester.assertContains("Nie znalezion żadnego wydania")
    }

    private void mockEmptyLatestIssue() {
        homePageRequestHandler.getLatestIssue() >> Option.none()
    }

    def "Should list last 5 newsletter issues on right panel"() {
        given:
            mockEmptyLatestIssue()
            List<PublishedNewsletterIssue> latestIssues = []
            (1..5).each {
                latestIssues.add(PublishedNewsletterIssue.builder().number(5_000 + it).publishedDate(LocalDate.of(2015, 5, it)).build())
            }
            rightFrontendSidebarRequestHandler.findLatestFiveIssues() >> JavaslangList.ofAll(latestIssues)
        when:
            tester.startPage(HomePage)
        then:
            latestIssues.each {
                String issueNumber = NumberFormat.getInstance().format(it.number)
                String publishedDate = RightFrontendSidebar.PUBLISHED_DATE_FORMATTER.format(it.publishedDate)
                tester.assertContains("Wydanie #$issueNumber z $publishedDate")
            }
    }

    def "Should show appropriate message on right panel, when there are no newsletters"() {
        given:
            mockEmptyLatestIssue()
            rightFrontendSidebarRequestHandler.findLatestFiveIssues() >> JavaslangList.empty()
        when:
            tester.startPage(HomePage)
        then:
            tester.assertContains("Newsletterów brak")
    }
}
