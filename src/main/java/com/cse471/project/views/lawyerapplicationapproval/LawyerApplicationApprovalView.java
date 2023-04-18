package com.cse471.project.views.lawyerapplicationapproval;

import com.cse471.project.entity.IncludedDocumentType;
import com.cse471.project.entity.LawyerRoleApplication;
import com.cse471.project.entity.User;
import com.cse471.project.service.Lawyer.LawyerRoleApplicationService;
import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import javax.annotation.security.RolesAllowed;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;

import static com.cse471.project.entity.LawyerType.*;


@PageTitle("List Of Applications")
@Route(value = "list-of-lawyer-application", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class LawyerApplicationApprovalView extends VerticalLayout {

    private final Div mainContainerDiv = new Div();
    private final LawyerRoleApplicationService lawyerRoleApplicationService;
    private final UI curUI;
    private final TextField searchApplicationViaId = new
            TextField("Search Application via Id");

    public LawyerApplicationApprovalView(LawyerRoleApplicationService lawyerRoleApplicationService) {
        this.lawyerRoleApplicationService = lawyerRoleApplicationService;
        curUI = UI.getCurrent();
        addClassName("lawyer-app-approval-view-main-page");
        searchAndFilterApplicationSection();
        mainContainerDiv.addClassName("lawyer-app-approval-view-main-container");
        var listOfAllApplication = lawyerRoleApplicationService.findAllLawyerApplicationYetTobeReviewed();
        for (var application : listOfAllApplication) {
            mainContainerDiv.add(createAnApplicationCard(application));
        }
        add(mainContainerDiv);
    }

    private void searchAndFilterApplicationSection() {
        Div searchBoxContainer = new Div();
        searchBoxContainer.addClassName("faq-view-main-content-div");
        // Need to set up some prompts
        Span prompt = new Span();
        prompt.addClassName("faq-view-prompt-span");
        String promptText = "Use the below search boxes to search and" +
                " filter application or fetch metadata regarding applications";
        prompt.setText(promptText);
        // Setting up the search textfield
        setUpSearchTextField();
        // Setting up the icon
        Div searchAndFilterDiv = new Div();
        searchAndFilterDiv.addClassName("faq-view-search-post-div");
        searchAndFilterDiv.add(searchApplicationViaId);
        searchBoxContainer.add(prompt);
        searchBoxContainer.add(searchAndFilterDiv);
        add(searchBoxContainer);
    }

    private Div createAnApplicationCard(LawyerRoleApplication application) {
        Div applicationCard = new Div();
        applicationCard.addClassName("laav-a-application-card");
        User userWhoApplied = application.getUser();
        Span applicationID = new Span();
        applicationID.setText(application.getApplicationID().toString());
        applicationID.addClassName("laav-a-application-id-span");
        var applicantIDContainer = new H4("Application ID: ");
        applicantIDContainer.add(applicationID);
        applicantIDContainer.addClassName("laav-a-application-user-id");
        var applicantName = new H4(userWhoApplied.getUsername());
        applicantName.addClassName("laav-a-application-user-name");
        Tab applicationTab = new Tab();
        applicationTab.add(applicantIDContainer);
        applicationTab.addClassName("laav-a-tab");
        if (userWhoApplied.getProfilePicture() != null && userWhoApplied.getProfilePicture().length != 0) {
            Image profilePicture = new Image(
                    new StreamResource("profile-picture.jpg",
                            () -> new ByteArrayInputStream(userWhoApplied.getProfilePicture())),
                    "Profile Picture");
            profilePicture.addClassName("laav-a-profile-picture");
            applicationTab.add(profilePicture);
        } else {
            Avatar avatar = new Avatar(userWhoApplied.getUsername());
            avatar.addClassName("laav-a-profile-avatar");
            applicationTab.add(avatar);
        }
        applicationTab.add(applicantName);
        LocalDateTime whenWasTheApplicationSubmitted = application.getSubmittedAt();
        Span label = new Span(whenWasTheApplicationSubmitted.toString());
        label.addClassName("laav-a-application-submission-time");
        applicationTab.add(label);
        applicationCard.add(applicationTab);

        Div applicationLayout = createApplicationLayout(application, userWhoApplied);
        applicationLayout.addClassName("submitted-application-form-collapse");

        applicationTab.getElement().addEventListener("click", e -> {
            if (applicationTab.isSelected()) {
                applicationTab.setSelected(false);
                applicationLayout.addClassName("submitted-application-form-collapse");
                applicationLayout.removeClassName("submitted-application-form-expand");
                applicationTab.setTooltipText("Click to Expand");
            } else {
                applicationLayout.addClassName("submitted-application-form-expand");
                applicationLayout.removeClassName("submitted-application-form-collapse");
                applicationTab.setSelected(true);
                applicationTab.setTooltipText("Click to collapse");
            }
        });

        applicationCard.add(applicationLayout);
        return applicationCard;
    }


    private Div createApplicationLayout(LawyerRoleApplication lawyerRoleApplication,
                                        User applicant) {
        Div layout = new Div();
        layout.addClassName("laav-application-layout-div");
        layout.add(createFormLayout(lawyerRoleApplication, applicant));
        var approveApplication = new Button("Approve Application");
        approveApplication.addClassName("laav-app-approval-button");
        approveApplication.addThemeVariants(ButtonVariant.LUMO_SUCCESS,
                ButtonVariant.LUMO_PRIMARY);
        approveApplication.addClickListener(event ->
                handleApplicationApprovalRequest());
        var rejectApplication = new Button("Reject Application");
        rejectApplication.addClassName("laav-app-reject-button");
        rejectApplication.addThemeVariants(ButtonVariant.LUMO_SUCCESS,
                ButtonVariant.LUMO_PRIMARY);
        rejectApplication.addClickListener(event ->
                handleRejectApplicationRequest());
        Div actionButtonLayout = new Div();

        actionButtonLayout.addClassName("laav-action-button-layout");
        actionButtonLayout.add(rejectApplication, approveApplication);

        layout.add(actionButtonLayout);
        return layout;
    }


    private FormLayout createFormLayout(LawyerRoleApplication lawyerRoleApplication,
                                        User applicant) {
        FormLayout formLayout = new FormLayout();
        formLayout.addClassName("lawyer-application-view-layout");

        var name = new TextField("Applicant name");
        name.addClassName("lawyer-application-username");
        name.setValue(applicant.getName());
        name.setReadOnly(true);
        name.setHelperText("It's readonly");
        name.setTooltipText("The name of the Applicant");

        var username = new TextField("Applicant username");
        username.addClassName("lawyer-application-username");
        username.setValue(applicant.getUsername());
        username.setReadOnly(true);
        username.setHelperText("It's readonly");
        username.setTooltipText("The username of the Applicant");

        var email = new EmailField("Applicant email");
        email.addClassName("lawyer-application-email");
        email.setValue(applicant.getEmail());
        email.setReadOnly(true);
        email.setHelperText("It's readonly");

        var lawyerBio = new TextArea("Short Bio");
        lawyerBio.addClassName("lawyer-application-text-area");
        lawyerBio.setTooltipText("Carefully Check the BIO");
        lawyerBio.setHelperText("BIO Entered By the Applicant");
        lawyerBio.setValue(lawyerRoleApplication.getBioForLawyerProfile());
        lawyerBio.setReadOnly(true);

        var whyJoinUs = new TextArea("Why join?");
        whyJoinUs.addClassName("lawyer-application-text-area");
        whyJoinUs.setTooltipText("Motivation Behind Joining");
        whyJoinUs.setHelperText("Information Entered by the Applicant");
        whyJoinUs.setValue(lawyerRoleApplication.getIncludedApplicationMotivation());
        whyJoinUs.setReadOnly(true);


        var multiSelectComboBox = new MultiSelectComboBox<>();
        multiSelectComboBox.setLabel("Selected Categories");
        multiSelectComboBox.setHelperText("Selected Type of Lawyer by the Applicant");
        multiSelectComboBox.setTooltipText("Applicant selected these types of lawyer");
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
                CIVIL_LITIGATION_LAWYER);
        for (var selectedLawyerTypeByApplicant : lawyerRoleApplication.getSelectedLawyerType()) {
            multiSelectComboBox.select(selectedLawyerTypeByApplicant);
        }
        multiSelectComboBox.addClassName("lawyer-application-multi-select-box");
        multiSelectComboBox.setReadOnly(true);

        Anchor anchor = new Anchor();

        var donwloadButton = new Button("Click Here To Download Included Document(s) By The Applicant");
        donwloadButton.addClassName("laav-a-anchor");

        if (lawyerRoleApplication.getIncludedDocuments() != null &&
                lawyerRoleApplication.getIncludedDocuments().length != 0) {
            String fileName;
            if (lawyerRoleApplication.getIncludedDocumentType().equals(IncludedDocumentType.PDF)) {
                fileName = applicant.getUsername() + lawyerRoleApplication.getApplicationID() + ".pdf";
            } else {
                fileName = applicant.getUsername() + lawyerRoleApplication.getApplicationID() + ".zip";
            }
            var streamResource = new StreamResource(fileName,
                    () -> new ByteArrayInputStream(lawyerRoleApplication.getIncludedDocuments()));
            anchor = new Anchor(streamResource, "Download Included Documents");
            anchor.removeAll();
            anchor.add(donwloadButton);
        }


        formLayout.add(name, username, email, lawyerBio, whyJoinUs,
                multiSelectComboBox);
        if (lawyerRoleApplication.getIncludedDocuments() != null &&
                lawyerRoleApplication.getIncludedDocuments().length != 0) {
            formLayout.add(anchor);
        }
        return formLayout;
    }

    private void handleApplicationApprovalRequest() {

    }

    private void handleRejectApplicationRequest() {

    }

    private void setUpSearchTextField() {
        searchApplicationViaId.setTitle("Type to Search here");
        searchApplicationViaId.addClassName("faq-view-search-field");
        searchApplicationViaId.setClearButtonVisible(true);
        searchApplicationViaId.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchApplicationViaId.setTooltipText("Click here to search");
        // We want to search for things as soon they are typed.
        searchApplicationViaId.setValueChangeMode(ValueChangeMode.EAGER);
        searchApplicationViaId.addValueChangeListener(event -> {
            System.out.println("Something");
        });
    }
}
