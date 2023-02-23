package com.cse471.project.views.forgotpassword;


import com.cse471.project.service.UserService.UserService;
import com.cse471.project.views.login.LoginView;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@PageTitle("Forgot Password")
@Route(value = "forgot-password")
@AnonymousAllowed
@Uses(Icon.class)
public class ForgotPassword extends VerticalLayout {
    private final EmailField emailField = new EmailField("Enter your email address here");
    private final Button submitRequest = new Button("Submit");
    private final UserService userService;

    public ForgotPassword(UserService userService) {
        this.userService = userService;
        setSizeFull();
        var h1 = new H1("Forgot Password? ");
        Span span = new Span("Please enter your email address here so that we can send you the password reset link.");
        setUpSubmitButton();
        setUpEmailField();
        var link = new RouterLink("Already have an account? Login",
                LoginView.class);
        link.addClassName("forgot-password-router-link");
        add(emailField, submitRequest, link);
    }

    private void setUpSubmitButton() {
        submitRequest.addClickShortcut(Key.ENTER);
        submitRequest.addClickListener(event -> {
            handleRequest();
        });
    }

    private void handleRequest() {
        if (!emailField.isInvalid()) {
            // We will call the password reset service and do the thing.
            userService.sendForgotPasswordResetLink(emailField.getValue());
            //noinspection DuplicatedCode
            removeAll();
            Div successCard = new Div();
            successCard.addClassName("success-card");

            // Success icon
            Div successIcon = new Div(VaadinIcon.CHECK.create());
            successIcon.addClassName("success-icon");
            successCard.add(successIcon);

            // Success message
            Div successMessage = new Div(new Span("Registration " +
                    "successful! Please check your email for further instructions."));
            successMessage.addClassName("success-message");
            successCard.add(successMessage);
            add(successCard);
        } else {
            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

            Div text = new Div(new Text("Please fill up the email field properly" +
                    " before proceeding any further"));

            Button notificationCloseButton = new Button(new Icon("lumo", "cross"));
            notificationCloseButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            notificationCloseButton.getElement().setAttribute("aria-label", "Close");
            notificationCloseButton.addClickListener(event -> notification.close());
            HorizontalLayout layout = new HorizontalLayout(text, notificationCloseButton);
            layout.setAlignItems(Alignment.CENTER);
            notification.add(layout);
            notification.setPosition(Notification.Position.BOTTOM_CENTER);
            notification.setDuration(15000);
            notification.open();
        }
    }

    private void setUpEmailField() {
        emailField.setRequiredIndicatorVisible(true);
        emailField.setTitle("Enter your email");
        emailField.setPlaceholder("Enter your EMAIL here to get the reset password link!");
        emailField.setPattern("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
        emailField.addValueChangeListener(event -> emailChecker(emailField.getValue()));
    }

    private void emailChecker(String emailFieldValue) {
        if (!emailFieldValue.matches(emailField.getPattern())) {
            emailField.setErrorMessage("This is not a valid email address!" +
                    " Please enter a valid email address");
            emailField.setInvalid(true);
        }
        else if (!userService.findUserByEmail(emailFieldValue)) {
            emailField.setErrorMessage("This email address is not associated with any user");
        }
    }
}
