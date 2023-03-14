package com.cse471.project.views.faq;

import com.cse471.project.entity.FAQ;
import com.cse471.project.service.FAQService.FAQService;
import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.List;


@PageTitle("FAQ")
@Route(value = "faq", layout = MainLayout.class)
@AnonymousAllowed
public class FAQView extends VerticalLayout {
    private final FAQService faqService;

    public FAQView(FAQService faqService) {
        this.faqService = faqService;
        addClassName("faq-view-parent-v");
        // Fetch the FAQs from the database
        List<FAQ> faqs = faqService.findAllFaq();

        // Create a container for the FAQ sections
        Div faqSections = new Div();
        faqSections.addClassName("faq-view-main-div-card");

        // Loop through each FAQ and create a section for it
        for (FAQ faq : faqs) {
            var faqSection = createFAQSection(faq);
            faqSections.add(faqSection);
        }

        // Add the FAQ sections container to the layout
        add(faqSections);
    }

    private Component createFAQSection(FAQ faq) {
        // Create the section header
        H3 questionHeader = new H3(faq.getQuestion());
        questionHeader.addClassName("faq-view-faq-question-h3");

        // Create the section content
        Span answerText = new Span(faq.getAnswer());
        answerText.addClassName("faq-view-section-answer");

        // Create the section tab
        Tab faqTab = new Tab(questionHeader);
        faqTab.addClassName("faq-view-faqTab");

        // Create the section container
        Div faqSection = new Div();
        faqSection.addClassName("faq-view-faq-section");

        // Add the section tab and content to the section container
        faqSection.add(faqTab, answerText);

        // Adding the tooltip for better accessibility
        faqTab.setTooltipText("Click to Expand");
        // Configure the section tab
        faqTab.getElement().addEventListener("click", e -> {
            if (faqTab.isSelected()) {
                faqTab.setSelected(false);
                faqSection.addClassName("faq-view-collapse-answer");
                faqSection.removeClassName("faq-view-expand-answer");
                faqTab.setTooltipText("Click to Expand");
            } else {
                faqSection.addClassName("faq-view-expand-answer");
                faqSection.removeClassName("faq-view-collapse-answer");
                faqTab.setSelected(true);
                faqTab.setTooltipText("Click to collapse");
            }
        });
        return faqSection;
    }
}
