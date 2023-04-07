package com.cse471.project.views.lawyerroleapplication;

import com.cse471.project.entity.LawyerRoleApplicationStatus;
import com.cse471.project.entity.LawyerType;
import com.cse471.project.entity.User;
import com.cse471.project.security.AuthenticatedUser;
import com.cse471.project.service.Lawyer.LawyerRoleApplicationService;
import com.cse471.project.service.UserService.UserService;
import com.cse471.project.views.MainLayout;
import com.cse471.project.views.faq.FAQView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.cse471.project.entity.LawyerType.*;

@PageTitle("LAWYER APPLICATION FORM")
@Route(value = "lawyer-application", layout = MainLayout.class)
@RolesAllowed("USER")
public class LawyerRoleApplicationView extends VerticalLayout {
    private TextField name;
    private TextField username;
    private TextField email;
    private User curUser;
    private TextArea lawyerBio;
    private TextArea whyJoinUs;
    private LawyerComplementaryDocumentUploadComponent documentUploadComponent;
    private MultiSelectComboBox<LawyerType> multiSelectComboBox;
    private Button submitButton;
    private RouterLink faqPage;
    private final UserService userService;
    private final LawyerRoleApplicationService lawyerRoleApplicationService;

    public LawyerRoleApplicationView(AuthenticatedUser authenticatedUser, UserService userService, LawyerRoleApplicationService lawyerRoleApplicationService) {
        this.curUser = authenticatedUser.get().orElseThrow();
        this.userService = userService;
        this.lawyerRoleApplicationService = lawyerRoleApplicationService;
        if (lawyerRoleApplicationService.containsPendingApplication(curUser)) {
            showApplicationAlreadyExistCard();
            return;
        }
        createApplicationForm();
    }

    private void createApplicationForm() {
        addClassName("lawyer-application-main-page");
        Div layout = new Div();
        layout.addClassName("lawyer-application-layout-div");
        H1 header = new H1();
        header.setText("APPLICATION FORM");
        header.addClassName("lawyer-application-form-header");
        FormLayout formLayout = createFormLayout();
        faqPage = new RouterLink("Got Questions? Visit FAQ",
                FAQView.class);
        faqPage.addClassName("lawyer-application-faq-page-router-link");
        layout.add(header, formLayout, submitButton, faqPage);
        add(layout);
    }

    private FormLayout createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.addClassName("lawyer-application-view-layout");

        name = new TextField("Applicant name");
        name.addClassName("lawyer-application-username");
        name.setValue(curUser.getName());
        name.setReadOnly(true);
        name.setHelperText("It's readonly");
        name.setTooltipText("The name you used to register");

        username = new TextField("Applicant username");
        username.addClassName("lawyer-application-username");
        username.setValue(curUser.getUsername());
        username.setReadOnly(true);
        username.setHelperText("It's readonly");
        username.setTooltipText("The username you used to register");

        email = new TextField("Applicant email");
        email.addClassName("lawyer-application-email");
        email.setValue(curUser.getEmail());
        email.setReadOnly(true);
        email.setHelperText("It's readonly");

        lawyerBio = new TextArea("Short Bio");
        lawyerBio.addClassName("lawyer-application-text-area");
        lawyerBio.setTooltipText("Enter your short bio here");
        lawyerBio.setHelperText("Enter your short bio here");

        whyJoinUs = new TextArea("Why join?");
        whyJoinUs.addClassName("lawyer-application-text-area");
        whyJoinUs.setTooltipText("Tell us about your motivation");
        whyJoinUs.setHelperText("Write a bit why do you want to join here");


        multiSelectComboBox = new MultiSelectComboBox<>();
        multiSelectComboBox.setLabel("Select the type of Lawyer You are");
        multiSelectComboBox.setHelperText("Please select all the types that applies to you");
        multiSelectComboBox.setTooltipText("Select types");
        multiSelectComboBox.setItems(BUSINESS_LAWYER, BANKRUPTCY_LAWYER, TAX_LAWYER,
                DEFENSE_LAWYER, CONSTITUTIONAL_LAWYER, FAMILY_LAWYER, LABOR_LAWYER,
                ESTATE_PLANNING_LAWYER, IMMIGRATION_LAWYER, PERSONAL_INJURY_LAWYER,
                INTELLECTUAL_PROPERTY_LAWYER, ENTERTAINMENT_LAWYER,
                MEDICAL_MALPRACTICE_LAWYER, CONTRACT_LAWYER,
                SOCIAL_SECURITY_DISABILITY_LAWYER, GOVERNMENT_LAWYER, MILITARY_LAWYER,
                MERGERS_AND_ACQUISITIONS_LAWYER, ENVIRONMENTAL_LAWYER,
                REAL_ESTATE_ATTORNEY_OR_PROPERTY_LAWYER,
                TOXIC_TORT_LAWYER, VIDEO_GAME_LAWYER, PUBLIC_INTEREST_LAWYER,
                DIGITAL_MEDIA_AND_INTERNET_LAWYER, FINANCE_AND_SECURITIES_LAWYER,
                CIVIL_RIGHTS_LAWYER, WORKERS_COMPENSATION_LAWYER,
                CIVIL_LITIGATION_LAWYER
        );
        multiSelectComboBox.addClassName("lawyer-application-multi-select-box");

        documentUploadComponent = new LawyerComplementaryDocumentUploadComponent();
        documentUploadComponent.addClassName("lawyer-application-doc-upload");
        documentUploadComponent.setTooltipText("Upload necessary documents if any");

        submitButton = new Button("SUBMIT APPLICATION");
        submitButton.addClassName("lawyer-application-view-submit-button");
        submitButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS,
                ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener(event ->
                handleApplicationSubmitRequest());

        formLayout.add(name, username, email, lawyerBio, whyJoinUs,
                multiSelectComboBox, documentUploadComponent);

        return formLayout;
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleApplicationSubmitRequest() {
        if (!isFormOkay()) {
            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

            Div text = new Div(new Text("Please properly fill-up the form" +
                    " before submitting it"));

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
            return;
        }
        com.cse471.project.entity.LawyerRoleApplication lawyerRoleApplication = new com.cse471.project.entity.LawyerRoleApplication();
        lawyerRoleApplication.setSubmittedAt(LocalDateTime.now());
        lawyerRoleApplication.setUser(curUser);
        lawyerRoleApplication.setBioForLawyerProfile(lawyerBio.getValue());
        lawyerRoleApplication.setIncludedApplicationMotivation(whyJoinUs.getValue());
        lawyerRoleApplication.setIncludedDocuments(documentUploadComponent.documentData);
        lawyerRoleApplication.setSelectedLawyerType(multiSelectComboBox.getSelectedItems());
        curUser.setAppliedForLawyerRole(true);
        curUser.setLawyerRoleApplicationStatus(LawyerRoleApplicationStatus.ON_HOLD);
        curUser.setAppliedNumberOfTimesForLawyerRole(curUser.getAppliedNumberOfTimesForLawyerRole() + 1);
        userService.update(curUser);
        lawyerRoleApplicationService.saveLawyerApplication(lawyerRoleApplication, curUser);
        showSuccessConfirmation();
    }

    private boolean isFormOkay() {
        AtomicBoolean isValid = new AtomicBoolean(true);
        if (lawyerBio.isEmpty()) {
            lawyerBio.setErrorMessage("This field can't be empty");
            lawyerBio.setInvalid(true);
            isValid.set(false);
        }
        if (whyJoinUs.isEmpty()) {
            whyJoinUs.setErrorMessage("This field can't be empty");
            whyJoinUs.setInvalid(true);
            isValid.set(false);
        }
        if (multiSelectComboBox.getValue().size() < 1) {
            multiSelectComboBox.setErrorMessage("You must select at " +
                    "least one type from here");
            multiSelectComboBox.setInvalid(true);
            isValid.set(false);
        }
        return isValid.get();
    }

    private void showSuccessConfirmation() {
        //noinspection DuplicatedCode
        removeAll();
        Div successCard = new Div();
        successCard.addClassName("success-card");

        // Success icon
        Div successIcon = new Div(VaadinIcon.CHECK.create());
        successIcon.addClassName("success-icon");
        successCard.add(successIcon);

        // Success message
        Div successMessage = new Div(new Span("Successfully submitted the application. " +
                "Please wait for the response"));
        successMessage.addClassName("success-message");
        successCard.add(successMessage);
        add(successCard);
    }


    private void showApplicationAlreadyExistCard() {
        //noinspection DuplicatedCode
        removeAll();
        Div successCard = new Div();
        successCard.addClassName("success-card");

        // Success icon
        Div successIcon = new Div(VaadinIcon.CLOCK.create());
        successIcon.addClassName("success-icon");
        successCard.add(successIcon);

        // Success message
        Div successMessage = new Div(new Span("You already have a pending " +
                "application. Please wait till it's reviewed by an Admin"));
        successMessage.addClassName("success-message");
        successCard.add(successMessage);
        add(successCard);
    }

    @SuppressWarnings("DuplicatedCode")
    private static class LawyerComplementaryDocumentUploadComponent
            extends CustomField<Upload> {

        private final Upload complementaryDocument;
        private final MemoryBuffer memoryBuffer = new MemoryBuffer();
        public byte[] documentData;

        private LawyerComplementaryDocumentUploadComponent() {
            complementaryDocument = new Upload(memoryBuffer);
            complementaryDocument.addClassName("bg-contrast-20");
            complementaryDocument.setAutoUpload(true);
            complementaryDocument.setAcceptedFileTypes(".pdf", ".zip");
            complementaryDocument.setMaxFiles(10 * 1024 * 1024); // 10 MB
            complementaryDocument.setDropLabel(buildDropLabel());
            complementaryDocument.setUploadButton(buildUploadButton());
            complementaryDocument.addClassName("profile-picture-upload-pu");
            complementaryDocument.setMaxFiles(1);
            H5 title = new H5("Complementary Documents");
            title.addClassName("profile-pic-upload-title");
            Div layout = new Div();
            layout.add(title, complementaryDocument);
            layout.addClassName("profile-picture-upload-div");
            complementaryDocument.addSucceededListener(this::setDocumentData);
            add(layout);
        }

        private void setDocumentData(SucceededEvent succeededEvent) {
            try {
                InputStream inputStream = memoryBuffer.getInputStream();
                // Save imageData to your database as a String annotated with @Lob
                documentData = inputStream.readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Component buildDropLabel() {
            Icon icon = VaadinIcon.BULLSEYE.create();
            Paragraph label = new Paragraph("Please upload a single `.pdf` file." +
                    " If you have more than 1 file to upload then zip all the files" +
                    " together thant upload them.  Remember Max file size is 10MB");
            HorizontalLayout layout = new HorizontalLayout(icon, label);
            layout.setAlignItems(Alignment.CENTER);
            return layout;
        }

        private Button buildUploadButton() {
            Button button = new Button("Upload");
            button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            button.addClassName("profile-pic-upload-button");
            return button;
        }

        @Override
        protected Upload generateModelValue() {
            return complementaryDocument;
        }

        @Override
        protected void setPresentationValue(Upload upload) {
            // No need to implement this method for this component
        }
    }
}
