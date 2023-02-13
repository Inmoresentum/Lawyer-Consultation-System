package com.cse471.project.views.forgotpassword;


import com.cse471.project.views.login.LoginView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.concurrent.atomic.AtomicBoolean;

@PageTitle("Forgot Password")
@Route(value = "forgot-password")
@AnonymousAllowed
public class ForgotPassword extends VerticalLayout {
    public ForgotPassword() {
        setSizeFull();
        add(new H1("Forgot Password?"));
        add(new Text("Please enter your email address so that we can send you the password reset link."));
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);
        EmailField emailField = new EmailField("Enter your email here");
        emailField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        emailField.setRequiredIndicatorVisible(true);
        emailField.setTitle("Enter your EMAIL here to get the reset password link!");
        AtomicBoolean containsError = new AtomicBoolean(true);
        emailField.addValueChangeListener(event -> {
            String email = event.getValue();
            String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
            if (!email.isEmpty()) {
                if (!email.matches(pattern)) {
                    emailField.setErrorMessage("Invalid email address!!");
                    containsError.set(true);
                } else {
                    emailField.setErrorMessage(null);
                    containsError.set(false);
                }
            }
        });
        Button submitRequest = new Button("Submit");
        submitRequest.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        emailField.getStyle().set("width", "45%");
        submitRequest.getStyle().set("width", "25%");
        submitRequest.addClickListener(event -> {
            if (!containsError.get()) {
                // We call the password reset service and do the thing.
                removeAll();
                add(new Text("If the provided email address have any account associated with us" +
                        " then you should receive an email with further instructions!"));
                Notification notification = Notification
                        .show("Application submitted!");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } else {

                Notification notification = Notification
                        .show("Invalid email address!!");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        var link = new RouterLink("Already have an account? Login", LoginView.class);
        add(emailField, submitRequest, link);
    }
}
