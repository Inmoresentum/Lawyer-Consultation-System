package com.cse471.project.views.userprofile;

import com.cse471.project.entity.User;
import com.cse471.project.security.AuthenticatedUser;
import com.cse471.project.service.UserService.UserService;
import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import javax.annotation.security.RolesAllowed;
import java.io.ByteArrayInputStream;

@Route(value = "user-profile", layout = MainLayout.class)
@PageTitle("User Profile")
@RolesAllowed({"ADMIN", "USER", "LAWYER"})
public class UserProfile extends VerticalLayout {
    private final UserService userService;
    private final User curLoggedInUser;
    private final Paragraph aboutMeParagraph;

    public UserProfile(UserService userService, AuthenticatedUser authenticatedUser) {
        addClassName("user-profile-view");
        this.userService = userService;
        curLoggedInUser = authenticatedUser.get().orElseThrow();
        Div userProfileDiv = new Div();
        userProfileDiv.addClassName("user-profile-main-div");
        Div profilePictureDiv = new Div();
        profilePictureDiv.addClassName("user-profile-picture-div");
        if (curLoggedInUser.getProfilePicture() != null) {
            Image profilePicture = new Image(
                    new StreamResource("profile-picture.jpg",
                            () -> new ByteArrayInputStream(curLoggedInUser.getProfilePicture())),
                    "Profile Picture");
            profilePicture.addClassName("user-profile-profile-pic");
            profilePictureDiv.add(profilePicture);
        } else {
            Icon profilePictureIcon = VaadinIcon.USER.create();
            profilePictureIcon.addClassName("user-profile-profile-pic-icon");
            profilePictureDiv.add(profilePictureIcon);
        }

        Div userDetailsDiv = new Div();
        userDetailsDiv.addClassName("user-profile-user-details-div");
        H2 name = new H2(curLoggedInUser.getName());
        name.addClassName("user-profile-name");
        userDetailsDiv.add(name);
        Paragraph email = new Paragraph(curLoggedInUser.getEmail());
        email.addClassName("user-profile-email");
        userDetailsDiv.add(email);
        Paragraph phoneNumber = new Paragraph(curLoggedInUser.getPhoneNumber());
        phoneNumber.addClassName("user-profile-phone-number");
        userDetailsDiv.add(phoneNumber);

        Div aboutMeDiv = new Div();

        var aboutMeEditIcon = new Icon(VaadinIcon.EDIT);
        aboutMeEditIcon.addClassName("user-profile-aboutMeButtonEditIcon");
        aboutMeEditIcon.setTooltipText("Click here to Edit About me");
        aboutMeEditIcon.addClickListener(event ->
                handleEditUserAboutMeRequest());

        H3 aboutMeH3 = new H3("About me");
        aboutMeH3.addClassName("user-profile-about-me-h3");
        var hl = new HorizontalLayout();
        hl.setSpacing(false);
        hl.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        hl.add(aboutMeH3, aboutMeEditIcon);
        aboutMeDiv.add(hl);
        aboutMeDiv.addClassName("user-profile-about-me-div");

        if (curLoggedInUser.getAboutYourSelf() != null) {
            aboutMeParagraph = new Paragraph(curLoggedInUser.getAboutYourSelf());
            aboutMeParagraph.addClassName("user-profile-about-me-paragraph");
        } else {
            aboutMeParagraph = new Paragraph("Tell us about yourself!");
            aboutMeParagraph.addClassName("user-profile-about-me-placeholder");
        }
        aboutMeDiv.add(aboutMeParagraph);


        Div buttonDiv = new Div();
        buttonDiv.addClassName("user-profile-button-div");
        userProfileDiv.add(profilePictureDiv, userDetailsDiv, aboutMeDiv, buttonDiv);
        add(userProfileDiv);
    }

    private void handleEditUserAboutMeRequest() {
        Dialog userAboutMeEditDialog = new Dialog();

        userAboutMeEditDialog.setHeaderTitle("Update your BIO");
        userAboutMeEditDialog.addClassName("user-profile-view-dialog-box");

        TextArea aboutMeTextArea = createAndSetUpFAQAnswerTextArea();
        var dialogLayout = new VerticalLayout(aboutMeTextArea);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(Alignment.STRETCH);
        dialogLayout.setFlexGrow(1);
        dialogLayout.getStyle().set("width", "25rem").set("max-width", "100%");
        userAboutMeEditDialog.add(dialogLayout);

        Button makeChangesButton = new Button("Change", postButtonEvent ->
                handleMakeChangeRequest(aboutMeTextArea, userAboutMeEditDialog));

        //noinspection DuplicatedCode
        makeChangesButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        makeChangesButton.addClassName("user-profile-view-about-me-change-button");
        Button cancelButton = new Button("Cancel", e ->
                userAboutMeEditDialog.close());
        cancelButton.addClassName("user-profile-about-me-cancel-button");
        userAboutMeEditDialog.getFooter().add(cancelButton);
        userAboutMeEditDialog.getFooter().add(makeChangesButton);

        userAboutMeEditDialog.setDraggable(true);
        userAboutMeEditDialog.setResizable(true);
        userAboutMeEditDialog.open();
        add(userAboutMeEditDialog);
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleMakeChangeRequest(TextArea aboutMeTextArea, Dialog userAboutMeEditDialog) {
        if (curLoggedInUser.getAboutYourSelf().equals(aboutMeTextArea.getValue())) {
            Notification editAboutMeErrorNotification = new Notification();
            editAboutMeErrorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);

            Div text = new Div(new Text("It's same as the old one"));

            Button closeButton = new Button(new Icon("lumo", "cross"));
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            closeButton.getElement().setAttribute("aria-label", "Close");
            closeButton.addClickListener(event -> editAboutMeErrorNotification.close());
            HorizontalLayout layout = new HorizontalLayout(text, closeButton);
            layout.setAlignItems(Alignment.CENTER);
            editAboutMeErrorNotification.add(layout);
            editAboutMeErrorNotification
                    .setPosition(Notification.Position.BOTTOM_CENTER);
            editAboutMeErrorNotification.setDuration(10000);
            editAboutMeErrorNotification.open();
            aboutMeTextArea.setErrorMessage("New Bio and the old bio is the same!");
            aboutMeTextArea.setInvalid(true);
            return;
        }
        curLoggedInUser.setAboutYourSelf(aboutMeTextArea.getValue());
        userService.update(curLoggedInUser);
        Notification editAboutMeSuccessNotification = new Notification();
        editAboutMeSuccessNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        Div text = new Div(new Text("Successfully updated your bio"));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> editAboutMeSuccessNotification.close());
        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(Alignment.CENTER);
        editAboutMeSuccessNotification.add(layout);
        editAboutMeSuccessNotification
                .setPosition(Notification.Position.BOTTOM_CENTER);
        editAboutMeSuccessNotification.setDuration(10000);
        editAboutMeSuccessNotification.open();
        userAboutMeEditDialog.close();
        aboutMeParagraph.removeAll();
        aboutMeParagraph.add(aboutMeTextArea.getValue());
    }

    private TextArea createAndSetUpFAQAnswerTextArea() {
        TextArea textArea = new TextArea("Edit your bio");
        textArea.addClassName("user-profile-view-edit-about-me-text-area");
        textArea.setTooltipText("Click here to edit");
        textArea.setPlaceholder("Add new stuff");
        textArea.setHelperText("Update your bio here");
        if (curLoggedInUser.getAboutYourSelf() != null) {
            textArea.setValue(curLoggedInUser.getAboutYourSelf());
        }
        textArea.addValueChangeListener(event -> checkIfSameAsOldValue(textArea, event));
        textArea.setValueChangeMode(ValueChangeMode.EAGER);
        return textArea;
    }

    private void checkIfSameAsOldValue(TextArea textArea, AbstractField.ComponentValueChangeEvent<TextArea, String> event) {
        if (event.getValue().equals(curLoggedInUser.getAboutYourSelf())) {
            textArea.setErrorMessage("Old bio and new bio are the same");
            textArea.setInvalid(true);
        } else textArea.setInvalid(false);
    }
}
