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

        // Fetch the FAQs from the database
        List<FAQ> faqs = faqService.findAllFaq();

        // Create a container for the FAQ sections
        Div faqSections = new Div();
        faqSections.addClassNames("faq-sections", "mx-auto");

        // Loop through each FAQ and create a section for it
        for (FAQ faq : faqs) {
            Component faqSection = createFAQSection(faq);
            faqSections.add(faqSection);
        }

        // Add the FAQ sections container to the layout
        add(faqSections);

        // Configure the layout
        setSpacing(false);
        setMargin(false);
        setPadding(false);
        setSizeFull();
    }

    private Component createFAQSection(FAQ faq) {
        // Create the section header
        H3 questionHeader = new H3(faq.getQuestion());
        questionHeader.addClassNames("faq-question", "mb-0");

        // Create the section content
        Span answerText = new Span(faq.getAnswer());
        answerText.addClassNames("faq-answer", "mb-3");

        // Create the section tab
        Tab faqTab = new Tab(questionHeader);

        // Create the section container
        Div faqSection = new Div();
        faqSection.addClassNames("faq-section", "mx-auto");

        // Add the section tab and content to the section container
        faqSection.add(faqTab, answerText);

        // Configure the section tab
        faqTab.getElement().addEventListener("click", e -> {
            if (faqTab.isSelected()) {
                faqSection.removeClassNames("open");
            } else {
                faqSection.addClassNames("open");
            }
        });

        // Configure the section container
        faqSection.addClassNames("mb-3", "p-3", "rounded-lg", "bg-light");
        faqSection.getStyle().set("max-width", "800px");

        return faqSection;
    }
}
