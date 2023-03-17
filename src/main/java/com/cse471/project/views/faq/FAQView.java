package com.cse471.project.views.faq;

import com.cse471.project.entity.FAQ;
import com.cse471.project.entity.Role;
import com.cse471.project.security.AuthenticatedUser;
import com.cse471.project.service.FAQService.FAQService;
import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
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
    private final TextField searchFaq = new TextField();
    private final Icon postNewFAQ = new Icon(VaadinIcon.PLUS);

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
        addSearchAndPostFAQSection();
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

    private void addSearchAndPostFAQSection() {
        Div searchAndPostSectionMainContainer = new Div();
        searchAndPostSectionMainContainer.addClassName("faq-view-main-content-div");
        // Need to set up some prompts
        Span prompt = new Span();
        prompt.addClassName("faq-view-prompt-span");
        String promptText = "Check out our faq and see if this resolves your issues." +
                " Or you can shoot us an email! We will be happy to answer.";
        prompt.setText(promptText);
        // Setting up the search textfield
        setUpSearchTextField();
        // Setting up the icon
        setUpPostNewFAQIcon();
        Div searchAndPostDiv = new Div();
        searchAndPostDiv.addClassName("faq-view-search-post-div");
        searchAndPostDiv.add(searchFaq, postNewFAQ);
        searchAndPostSectionMainContainer.add(prompt);
        searchAndPostSectionMainContainer.add(searchAndPostDiv);
        add(searchAndPostSectionMainContainer);
    }

    private void setUpPostNewFAQIcon() {
        postNewFAQ.addClassName("faq-view-post-new-faq");
        postNewFAQ.addClickListener(event -> {
            // Todo: implement the method
        });
        postNewFAQ.setTooltipText("Click here to add new FAQ");
    }

    private void setUpSearchTextField() {
        searchFaq.setLabel("Type the search Term");
        searchFaq.setTitle("Type to Search here");
        searchFaq.addClassName("faq-view-search-field");
        searchFaq.setClearButtonVisible(true);
        searchFaq.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchFaq.setTooltipText("Click here to search");
        // We want to search for things as soon they are typed.
        searchFaq.setValueChangeMode(ValueChangeMode.EAGER);
        searchFaq.addValueChangeListener(event -> {
            var searchTerm = event.getValue();
            var listOfFaqs = faqService.findAllFAQsContainingSearchTerm(searchTerm);
            currentUI.access(faqSections::removeAll);
            for (FAQ faq : listOfFaqs) {
                var faqSection = createFAQSection(faq);
                currentUI.access(() -> {
                    faqSections.add(faqSection);
                });
            }
        });
    }

    private Div createFAQSection(FAQ faq) {
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
            editIcon.addClassName("faq-view-faq-edit-icon");
            editIcon.addClickListener(event -> editFaqPost(faq,
                    questionHeader, answerText));
            // Adding Tooltip for faqEditButton
            editIcon.setTooltipText("Click to Edit the Text");
            faqTab.add(editIcon);
            Icon deleteIcon = new Icon(VaadinIcon.TRASH);
            deleteIcon.addClassName("faq-view-faq-delete-icon");
            faqTab.add(deleteIcon);
            deleteIcon.addClickListener(event ->
                    handFaqDeleteRequest(faqSection, faq));
            // Adding the tooltip for deleteFAQButton
            deleteIcon.setTooltipText("Click to Delete the This FAQ");
        }
        return faqSection;
    }

    private void handFaqDeleteRequest(Div faqSection, FAQ faq) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("DELETE?");
        dialog.setText("Are you sure you want" +
                " to delete this FAQ");

        dialog.setCancelable(true);
        dialog.addCancelListener(event -> dialog.close());
        dialog.setCancelText("Nope");

        dialog.setConfirmText("Delete");
        dialog.setCloseOnEsc(true);
        dialog.addConfirmListener(event -> handleFAQDeleteOperation(dialog,
                faqSection, faq));
        dialog.open();
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleFAQDeleteOperation(ConfirmDialog confirmDialog,
                                          Div faqSection, FAQ faq) {
        try {
            faqService.delete(faq);
        } catch (Exception e) {
            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

            Div text = new Div(new Text("There seems to be some Internal Error" +
                    ". Please refresh the page and try again!"));

            Button closeButton = new Button(new Icon("lumo", "cross"));
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            closeButton.getElement().setAttribute("aria-label", "Close");
            closeButton.addClickListener(event -> notification.close());
            HorizontalLayout layout = new HorizontalLayout(text, closeButton);
            layout.setAlignItems(Alignment.CENTER);
            notification.add(layout);
            notification.setPosition(Notification.Position.BOTTOM_CENTER);
            notification.setDuration(10000);
            notification.open();
        }
        faqSections.remove(faqSection);
        confirmDialog.close();
    }

    private void editFaqPost(FAQ faq, H3 questionHeader, Span answerText) {
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
                handleSaveRequest(faq, dialog, faqQuestion,
                        faqAnswer, questionHeader, answerText));
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);

        dialog.setDraggable(true);
        dialog.setResizable(true);
        dialog.open();
        add(dialog);
    }

    private void handleSaveRequest(FAQ faq, Dialog dialog,
                                   TextArea faqQuestion,
                                   TextArea faqAnswer,
                                   H3 questionHeader,
                                   Span answerText) {
        if (!faqQuestion.getValue().equals(faq.getQuestion())) {
            faq.setFaqModifiedAt(LocalDateTime.now());
            faq.setQuestion(faqQuestion.getValue());
        }
        if (!faqAnswer.getValue().equals(faq.getAnswer())) {
            faq.setFaqModifiedAt(LocalDateTime.now());
            faq.setAnswer(faqAnswer.getValue());
        }
        questionHeader.removeAll();
        questionHeader.add(faqQuestion.getValue());
        answerText.removeAll();
        answerText.add(faqAnswer.getValue());
        faqService.update(faq);
        dialog.close();
    }
}
