package com.cse471.project.views.faq;

import com.cse471.project.entity.FAQ;
import com.cse471.project.entity.Role;
import com.cse471.project.security.AuthenticatedUser;
import com.cse471.project.service.FAQService.FAQService;
import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@PageTitle("FAQ")
@Route(value = "faq", layout = MainLayout.class)
@AnonymousAllowed
public class FAQView extends VerticalLayout {
    private final FAQService faqService;
    private final AtomicBoolean isAdmin =
            new AtomicBoolean(false);
    private final Div faqSections = new Div();
    private final UI currentUI;

    public FAQView(FAQService faqService, AuthenticatedUser authenticatedUser) {
        this.faqService = faqService;
        addClassName("faq-view-parent-v");
        currentUI = UI.getCurrent();

        // Create a container for the FAQ sections
        faqSections.addClassName("faq-view-main-div-card");


        var curUser = authenticatedUser.get();
        if (curUser.isPresent() &&
                curUser.get().getRoles().contains(Role.ADMIN)) {
            isAdmin.set(true);
        }
        addFaqSectionToDom();
        getElement().executeJs(
                "const elements = document.querySelectorAll('.faq-view-faqTab');" +
                        "const observer = new IntersectionObserver(entries => {" +
                        "  entries.forEach(entry => {" +
                        "    if (entry.isIntersecting && entry.intersectionRatio > 0) {" +
                        "      if (entry.boundingClientRect.top < 0) {" +
                        "        entry.target.classList.add('animate-down');" +
                        "      } else {" +
                        "        entry.target.classList.add('animate-up');" +
                        "      }" +
                        "      const onTransitionEnd = () => {" +
                        "        entry.target.removeEventListener('transitionend', onTransitionEnd);" +
                        "        entry.target.classList.remove('animate-down', 'animate-up');" +
                        "        observer.observe(entry.target);" +
                        "      };" +
                        "      entry.target.addEventListener('transitionend', onTransitionEnd);" +
                        "      observer.unobserve(entry.target);" +
                        "    }" +
                        "  });" +
                        "});" +
                        "elements.forEach(element => {" +
                        "  observer.observe(element);" +
                        "});"
        );
        // getting the hold of the current user
    }

    private void addFaqSectionToDom() {
        // Fetch the FAQs from the database
        List<FAQ> faqs = faqService.findAllFaq();

        // Loop through each FAQ and create a section for it
        for (FAQ faq : faqs) {
            var faqSection = createFAQSection(faq);
            faqSections.add(faqSection);
        }

        // Add the FAQ sections container to the layout
        currentUI.access(() -> add(faqSections));
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
        if (isAdmin.get()) {
            Icon editIcon = new Icon(VaadinIcon.PENCIL);
            Button faqEditButton = new Button(editIcon);
            faqEditButton.addClickListener(event -> editFaqPost(faq));
            faqTab.add(faqEditButton);
        }
        return faqSection;
    }

    private void editFaqPost(FAQ faq) {
        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("FAQ Modification");

        TextArea faqQuestion = new TextArea("Question");
        faqQuestion.setValue(faq.getQuestion());
        faqQuestion.setPlaceholder("Edit or Update the Question");
        faqQuestion.addThemeVariants(TextAreaVariant.LUMO_HELPER_ABOVE_FIELD);
        TextArea faqAnswer = new TextArea("Answer");
        faqAnswer.setValue(faq.getAnswer());
        VerticalLayout dialogLayout = new VerticalLayout(faqQuestion,
                faqAnswer);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");
        dialog.add(dialogLayout);

        Button saveButton = new Button("Add", e ->
                handleSaveRequest(faq, dialog, faqQuestion, faqAnswer));
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);

        dialog.setDraggable(true);
        dialog.setResizable(true);
        dialog.open();
        add(dialog);
    }

    private void handleSaveRequest(FAQ faq, Dialog dialog, TextArea faqQuestion, TextArea faqAnswer) {
        if (!faqQuestion.getValue().equals(faq.getQuestion())) {
            faq.setFaqModifiedAt(LocalDateTime.now());
            faq.setQuestion(faqQuestion.getValue());
        }
        if (!faqAnswer.getValue().equals(faq.getAnswer())) {
            faq.setFaqModifiedAt(LocalDateTime.now());
            faq.setAnswer(faqAnswer.getValue());
        }
        faqService.update(faq);
        dialog.close();
        // Reload to re-render everything.
        currentUI.getPage().reload();
    }
}
