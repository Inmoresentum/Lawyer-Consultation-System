package com.cse471.project.views.RegisterUser;

import com.cse471.project.entity.Role;
import com.cse471.project.entity.User;
import com.cse471.project.service.userService.UserService;
import com.cse471.project.views.login.LoginView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.Collections;

@PageTitle("Feedback")
@Uses(Icon.class)
@Route("register-user")
@AnonymousAllowed
public class RegisterUser extends VerticalLayout {
    private final TextField name = new TextField("Name");

    private final TextField username = new TextField("Username");
    private final EmailField email = new EmailField("Email");
    private final PasswordField password = new PasswordField("Password");
    private final PasswordField confirmPassword = new PasswordField("Confirm" +
            " Password");
    private final UserService userService;

    public RegisterUser(UserService userService) {
        this.userService = userService;
        var header = new H3("Register");
        var registerButton = new Button("Register", e -> register());
        var link = new RouterLink("Already have an account? Login",
                LoginView.class);
        // Adding the same css className so that I
        // can reuse the previous css.
        link.addClassName("forgot-password-router-link");
        setUpUserNameTextField();
        setUpNameField();
        setUpPasswordFiled();
        setUpConfirmPasswordFiled();

        add(header, new FormLayout(name, username, email, password, confirmPassword),
                registerButton, link);
    }

    private void register() {
        // Insert logic to save user data to a database or perform other actions
        User user = new User();
        if (name.getValue() != null) {
            user.setName(name.getValue());
        }
        if (username.getValue() != null) {
            user.setUsername(username.getValue());
        }
        if (email.getValue() != null && email.getValue().equals("@g.bracu.ac.bd")) {
            user.setRoles(Collections.singleton(Role.ADMIN));
        }
        if (email.getValue() != null) {
            user.setRoles(Collections.singleton(Role.USER));
        }
        userService.registerUser(user, password.getValue());
        removeAll();
        Notification.show("Registration successful!")
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void setUpNameField() {
        name.setPlaceholder("Enter your name");
        name.setRequiredIndicatorVisible(true);
        name.setErrorMessage("You provide a valid username");
        name.setRequired(true);
    }

    private void setUpUserNameTextField() {
        username.setPlaceholder("Enter your username");
        username.setRequiredIndicatorVisible(true);
        username.setErrorMessage("Username must be between 6-25 char");
        username.setRequired(true);
    }

    private void setUpPasswordFiled() {
        password.setPlaceholder("Enter your password");
        password.setRequired(true);
        password.setRequiredIndicatorVisible(true);
        password.setErrorMessage("The password must need to be a " +
                "valid password between 1-16");
    }

    private void setUpConfirmPasswordFiled() {
        confirmPassword.setPlaceholder("Enter your password again");
        confirmPassword.setRequired(true);
        confirmPassword.setRequiredIndicatorVisible(true);
        confirmPassword.setErrorMessage("The passwords do not match");
    }
}

