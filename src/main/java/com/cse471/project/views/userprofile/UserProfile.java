package com.cse471.project.views.userprofile;

import com.cse471.project.service.UserService.UserService;
import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.security.RolesAllowed;
import java.io.ByteArrayInputStream;

@Route(value = "user-profile", layout = MainLayout.class)
@PageTitle("User Profile")
@RolesAllowed({"ADMIN", "USER", "LAWYER"})
public class UserProfile extends VerticalLayout {
    private final UserService userService;

    public UserProfile(UserService userService) {
        addClassName("user-profile-view");
        this.userService = userService;
        Div userProfileDiv = new Div();
        userProfileDiv.addClassName("user-profile-main-div");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        var curUser = userService.findByUserName(userDetails.getUsername());
        if (curUser.isEmpty()) throw new IllegalStateException("User can't be empty");
        Div profilePictureDiv = new Div();
        profilePictureDiv.addClassName("user-profile-picture-div");
        if (curUser.get().getProfilePicture() != null) {
            Image profilePicture = new Image(
                    new StreamResource("profile-picture.jpg",
                            () -> new ByteArrayInputStream(curUser.get().getProfilePicture())),
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
        H2 name = new H2(curUser.get().getName());
        name.addClassName("user-profile-name");
        userDetailsDiv.add(name);
        Paragraph email = new Paragraph(curUser.get().getEmail());
        email.addClassName("user-profile-email");
        userDetailsDiv.add(email);
        Paragraph phoneNumber = new Paragraph(curUser.get().getPhoneNumber());
        phoneNumber.addClassName("user-profile-phone-number");
        userDetailsDiv.add(phoneNumber);

        Div aboutMeDiv = new Div();

        Button aboutMeEditButton = new Button(new Icon(VaadinIcon.EDIT));
        aboutMeEditButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        aboutMeEditButton.addClassName("user-profile-aboutMeButtonEditIcon");
        aboutMeEditButton.setTooltipText("Click here to Edit About me");
        aboutMeEditButton.addClickListener(event -> {
            System.out.println("Yay!! Icon got clicked");
            Notification.show("Icon Got Clicked");
        });

        H3 aboutMeH3 = new H3("About me");
        aboutMeH3.addClassName("user-profile-about-me-h3");
        var hl = new HorizontalLayout();
        hl.setSpacing(false);
        hl.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        hl.add(aboutMeH3, aboutMeEditButton);
        aboutMeDiv.add(hl);
        aboutMeDiv.addClassName("user-profile-about-me-div");

        if (curUser.get().getAboutYourSelf() != null) {
            Paragraph aboutMeParagraph = new Paragraph(curUser.get().getAboutYourSelf());
            aboutMeParagraph.addClassName("user-profile-about-me-paragraph");
            aboutMeDiv.add(aboutMeParagraph);
        } else {
            Paragraph aboutMeParagraph = new Paragraph("Tell us about yourself!");
            aboutMeParagraph.addClassName("user-profile-about-me-placeholder");
            aboutMeDiv.add(aboutMeParagraph);
        }


        Div buttonDiv = new Div();
        buttonDiv.addClassName("user-profile-button-div");
        userProfileDiv.add(profilePictureDiv, userDetailsDiv, aboutMeDiv, buttonDiv);
        add(userProfileDiv);
    }
}
