package com.cse471.project.views.forgotpassword;


import com.cse471.project.entity.ForgotPasswordVerificationToken;
import com.cse471.project.entity.User;
import com.cse471.project.repository.UserForgotPasswordVerificationRepository;
import com.cse471.project.repository.UserRepository;
import com.cse471.project.service.Email.EmailService;
import com.cse471.project.service.Email.EmailUtils;
import com.cse471.project.views.login.LoginView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Route("forgot-pass-verify")
@PageTitle("Account Recovery")
@AnonymousAllowed
public class ForgotPasswordTokenVerification extends
        VerticalLayout implements BeforeEnterObserver {
    private final UserForgotPasswordVerificationRepository
            userForgotPasswordVerificationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordField passwordField =
            new PasswordField("Password");
    private final PasswordField confirmPasswordField =
            new PasswordField("Confirm Password");
    private final Button submitButton =
            new Button("RESET PASSWORD", e -> resetPassword());
    private String verificationToken;
    private ForgotPasswordVerificationToken userForgotPasswordVerification;
    private final EmailUtils emailUtils;
    private final EmailService emailService;

    public ForgotPasswordTokenVerification(UserForgotPasswordVerificationRepository
                                                   userForgotPasswordVerificationRepository,
                                           UserRepository userRepository,
                                           PasswordEncoder passwordEncoder, EmailUtils emailUtils, EmailService emailService) {
        this.userForgotPasswordVerificationRepository = userForgotPasswordVerificationRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailUtils = emailUtils;
        this.emailService = emailService;
        addClassName("forgot-pass-view");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        var params = beforeEnterEvent.getLocation()
                .getQueryParameters().getParameters();
        try {
            verifyToken(params);
            setUpResetPasswordForm();
        } catch (IllegalStateException e) {
            createUnsuccessfulView();
        }
    }

    private void setUpResetPasswordForm() {
        Div layout = new Div();
        layout.addClassName("r-div");
        var header = new H3("PASSWORD RESET FORM");
        header.addClassName("r-v-h3");
        FormLayout formLayout = getFormLayout();
        layout.add(header, formLayout,
                submitButton);
        add(layout);
    }


    private void resetPassword() {
        if (isFormOkay()) {
            if (userForgotPasswordVerification.getCreatedAt().
                    isAfter(LocalDateTime.now())) {
                createUnsuccessfulView();
                return;
            }
            String userNewPassword = passwordEncoder.encode(confirmPasswordField.getValue());
            User user = userForgotPasswordVerification.getUser();
            user.setHashedPassword(userNewPassword);
            userForgotPasswordVerificationRepository
                    .updateConfirmedAt(verificationToken, LocalDateTime.now());
            userRepository.save(user);
            emailService.send(user.getEmail(), "Account Recovery",
                    emailUtils.buildEmailAccountRecoveryNotifier(user.getName()));
            createSuccessView();
        }
    }

    private boolean isFormOkay() {
        if (confirmPasswordField.isInvalid() || confirmPasswordField.isEmpty()) {
            confirmPasswordField.setErrorMessage("Please properly fill this filed");
            confirmPasswordField.setInvalid(true);
            return false;
        }
        if (passwordField.isInvalid() || passwordField.isEmpty()) {
            passwordField.setErrorMessage("Invalid password! Please enter a valid password");
            passwordField.setInvalid(true);
            return false;
        }
        return true;
    }

    private FormLayout getFormLayout() {
        setUpPasswordFiled();
        setUpConfirmPasswordFiled();
        setUpSubmitButton();
        FormLayout formLayout = new FormLayout(passwordField,
                confirmPasswordField);
        formLayout.addClassName("r-v-form-layout");
        return formLayout;
    }

    private void setUpSubmitButton() {
        submitButton.addClassName("r-v-button");
    }

    private void setUpPasswordFiled() {
        passwordField.addClassName("r-v-password-field");
        passwordField.setPlaceholder("Enter your new password here");
        passwordField.setRequired(true);
        passwordField.setRequiredIndicatorVisible(true);
        passwordField.setErrorMessage("Invalid password");
        passwordField.setRequiredIndicatorVisible(true);
        passwordField.setClearButtonVisible(true);
        passwordField.addValueChangeListener(event ->
                checkIfOldPassword(passwordField.getValue()));
    }

    private void checkIfOldPassword(String value) {
        if (passwordEncoder.matches(value,
                userForgotPasswordVerification.getUser()
                        .getHashedPassword())) {
            passwordField.setErrorMessage("Both old password and old password are the same password");
            passwordField.setInvalid(true);
        } else {
            passwordField.setInvalid(false);
        }
    }

    private void setUpConfirmPasswordFiled() {
        confirmPasswordField.addClassName("r-v-password-field");
        confirmPasswordField.setPlaceholder("Again enter your new password here");
        confirmPasswordField.setRequired(true);
        confirmPasswordField.setRequiredIndicatorVisible(true);
        confirmPasswordField.setErrorMessage("Password is required it can't be empty");
        confirmPasswordField.setRequiredIndicatorVisible(true);
        confirmPasswordField.setClearButtonVisible(true);
        confirmPasswordField.addValueChangeListener(event ->
                checkIfBothPasswordAreEqual(confirmPasswordField.getValue()));
    }

    private void checkIfBothPasswordAreEqual(String value) {
        if (!value.equals(passwordField.getValue())) {
            confirmPasswordField.setErrorMessage("The passwords do not match");
            confirmPasswordField.setInvalid(true);
        } else {
            confirmPasswordField.setInvalid(false);
        }
    }

    private void createUnsuccessfulView() {
        removeAll();
        Div div = new Div();
        div.addClassName("ac-unsuccessful-card");

        Icon firstBrokenIcon = new Icon(VaadinIcon.WARNING);
        firstBrokenIcon.addClassName("ac-warning-1");

        Icon secondBrokenIcon = new Icon(VaadinIcon.WARNING);
        secondBrokenIcon.addClassName("ac-warning-2");

        Icon thirdBrokenIcon = new Icon(VaadinIcon.WARNING);
        thirdBrokenIcon.addClassName("ac-warning-3");

        HorizontalLayout hl = new HorizontalLayout(firstBrokenIcon,
                secondBrokenIcon, thirdBrokenIcon);
        hl.addClassName("ac-un-s-hl");

        div.add(hl);

        H1 h1 = new H1("404");
        h1.addClassName("ac-un-h1");

        Span span = new Span("This link is broken");
        span.addClassName("ac-un-span1");
        div.add(h1, span);
        add(div);
    }

    private void createSuccessView() {
        removeAll();
        Div div = new Div();
        div.addClassName("ac-verify-card");
        H3 h3 = new H3("Password Reset Successful");
        Icon icon = new Icon(VaadinIcon.CHECK_CIRCLE);
        icon.addClassName("ac-verify-icon");
        HorizontalLayout hl = new HorizontalLayout(icon, h3);
        hl.addClassName("ac-verify-hl-s");
        RouterLink routerLink = new RouterLink("Please Click Here To Login",
                LoginView.class);
        routerLink.addClassName("ac-verify-router-link");
        VerticalLayout vl = new VerticalLayout(hl, routerLink);
        vl.addClassName("ac-verify-vl-s");
        div.add(vl);
        add(div);
    }

    private void verifyToken(Map<String, List<String>> params) {
        verificationToken = params.get("token").get(0);
        System.out.println(verificationToken + " from forgot-pass-verification view");
        userForgotPasswordVerification =
                userForgotPasswordVerificationRepository.findByToken(verificationToken).orElseThrow(() ->
                        new IllegalStateException("Token Not Found"));
        if (userForgotPasswordVerification.getConfirmedAt() != null) {
            throw new IllegalStateException("This link have been expired");
        }

        LocalDateTime expireAt = userForgotPasswordVerification.getExpiresAt();
        if (LocalDateTime.now().isAfter(expireAt)) {
            throw new IllegalStateException("This have already been used");
        }
    }
}
