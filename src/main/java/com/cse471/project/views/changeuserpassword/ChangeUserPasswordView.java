package com.cse471.project.views.changeuserpassword;

import com.cse471.project.entity.User;
import com.cse471.project.security.AuthenticatedUser;
import com.cse471.project.service.UserService.UserService;
import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.security.RolesAllowed;

@PageTitle("Password Change")
@Route(value = "change-user-password", layout = MainLayout.class)
@RolesAllowed({"ADMIN", "USER", "LAWYER", "SUPPORT"})
public class ChangeUserPasswordView extends VerticalLayout {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticatedUser authenticatedUser;
    private final PasswordField currentPasswordField =
            new PasswordField("Current Password");
    private final PasswordField newPasswordField =
            new PasswordField("New Password");
    private final PasswordField confirmYourNewPasswordField =
            new PasswordField("Confirm New Password");
    private final Button submitRequestButton =
            new Button("Change Password");
    private final User curUser;
    private final UI curUI;

    // Todo: Due to development purpose not adding
    //  password check to verify if the new password
    //  contains special char, number, capital letter
    //  and small letter and at least 8 char long. However,
    //  during the production we should change it.
    public ChangeUserPasswordView(UserService userService, PasswordEncoder passwordEncoder,
                                  AuthenticatedUser authenticatedUser) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticatedUser = authenticatedUser;
        addClassName("change-password-view-parent-vl");
        curUser = authenticatedUser.get().orElseThrow();
        curUI = UI.getCurrent();
        add(getDivLayout());
    }

    private Div getDivLayout() {
        Div layout = new Div();
        layout.addClassName("change-password-div");
//        layout.addClassName("change-password-view-wow");
        var header = new H3("PASSWORD CHANGE FORM");
        header.addClassName("change-password-h3");
        FormLayout formLayout = getFormLayout();
        layout.add(header, formLayout);
        layout.add(submitRequestButton);
        return layout;
    }

    private FormLayout getFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.addClassName("change-password-form-layout");
        setUpCurrentPasswordField();
        setUpNewPasswordField();
        setUpConfirmPasswordField();
        setUpChangePasswordSubmitButton();
        formLayout.add(currentPasswordField, newPasswordField,
                confirmYourNewPasswordField);
        return formLayout;
    }

    private void setUpChangePasswordSubmitButton() {
        submitRequestButton.addClassName("change-password" +
                "-submit-request-button");
        submitRequestButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        submitRequestButton.addClickListener(event -> handleSubmitRequest());
        submitRequestButton.addClickShortcut(Key.ENTER);
        submitRequestButton.setTooltipText("Click here to submit the request");
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleSubmitRequest() {
        if (!isFormOkay()) {
            return;
        }
        if (!passwordEncoder.matches(currentPasswordField.getValue(),
                curUser.getHashedPassword())) {
            curUI.access(() -> {
                Notification notification = new Notification();
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

                Div text = new Div(new Text("Incorrect password! Please try again"));
                Button closeButton = new Button(new Icon("lumo", "cross"));
                closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                closeButton.getElement().setAttribute("aria-label", "Close");
                closeButton.addClickListener(event -> notification.close());
                HorizontalLayout layout = new HorizontalLayout(text, closeButton);
                layout.setAlignItems(Alignment.CENTER);
                notification.add(layout);
                notification.setPosition(Notification.Position.BOTTOM_CENTER);
                notification.setDuration(10000);
                currentPasswordField.setErrorMessage("Wrong password");
                currentPasswordField.setInvalid(true);
            });
            return;
        }
        String newHashedPassword =
                passwordEncoder.encode(newPasswordField.getValue());
        curUser.setHashedPassword(newHashedPassword);
        userService.changeUserAccountPassword(curUser);
        authenticatedUser.logout();
    }

    private boolean isFormOkay() {
        if (currentPasswordField.isEmpty() || currentPasswordField.isInvalid()) {
            currentPasswordField.setErrorMessage("Enter your " +
                    "current password! It can't be empty");
            currentPasswordField.setInvalid(true);
            return false;
        }
        if (newPasswordField.isEmpty()) {
            newPasswordField.setErrorMessage("Enter your new password! It" +
                    " can't be empty");
            newPasswordField.setInvalid(true);
            return false;
        }
        if (confirmYourNewPasswordField.isEmpty()) {
            newPasswordField.setErrorMessage("Again Enter your " +
                    "new password! It can't be empty");
            newPasswordField.setInvalid(true);
            return false;
        }
        return true;
    }

    private void checkIfPasswordsMatches() {
        if (!confirmYourNewPasswordField.isEmpty() &&
                !confirmYourNewPasswordField.getValue()
                        .equals(newPasswordField.getValue())) {
            newPasswordField.setErrorMessage("The passwords do not match");
            newPasswordField.setInvalid(true);
        } else {
            newPasswordField.setInvalid(false);
        }
    }

    private void setUpCurrentPasswordField() {
        currentPasswordField.addClassName("change-password-cur-pass-field");
        currentPasswordField.setPlaceholder("Enter Your Current Password here");
        currentPasswordField.setPrefixComponent(
                new Icon(VaadinIcon.STAR));
        currentPasswordField.setErrorMessage("This field can't be empty");
        currentPasswordField.setRequired(true);
        currentPasswordField.addValueChangeListener(event -> isEmptyField());
    }

    private void isEmptyField() {
        if (currentPasswordField.isEmpty()) {
            currentPasswordField.setErrorMessage("This field can't be empty");
            currentPasswordField.setInvalid(true);
        } else {
            currentPasswordField.setInvalid(false);
        }
    }

    private void setUpNewPasswordField() {
        newPasswordField.addClassName("change-password-new-pass-field");
        newPasswordField.setPlaceholder("Enter Your New Password here");
        newPasswordField.setPrefixComponent(
                new Icon(VaadinIcon.STAR_HALF_LEFT_O));
        newPasswordField.setErrorMessage("This field can't be empty");
        newPasswordField.setRequired(true);
        newPasswordField.addValueChangeListener(event ->
                checkIfPasswordsMatches());
    }

    private void setUpConfirmPasswordField() {
        confirmYourNewPasswordField.addClassName("change-password-confirm-pass-field");
        confirmYourNewPasswordField.setPlaceholder("Again Enter Your New Password here");
        confirmYourNewPasswordField.setPrefixComponent(
                new Icon(VaadinIcon.STAR_HALF_RIGHT_O));
        confirmYourNewPasswordField.setErrorMessage("This field can't be empty");
        confirmYourNewPasswordField.setRequired(true);
        confirmYourNewPasswordField.addValueChangeListener(event -> {
            if (!confirmYourNewPasswordField.getValue().equals(newPasswordField.getValue())) {
                confirmYourNewPasswordField.setErrorMessage("The password don not match");
                confirmYourNewPasswordField.setInvalid(true);
                return;
            }
            confirmYourNewPasswordField.setInvalid(false);
        });
    }
}
