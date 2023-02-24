package com.cse471.project.views.RegisterUser;

import com.cse471.project.entity.Role;
import com.cse471.project.entity.User;
import com.cse471.project.service.UserService.UserService;
import com.cse471.project.views.login.LoginView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
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
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@PageTitle("User Registration")
@Uses(Icon.class)
@Route("register-user")
@AnonymousAllowed
public class RegisterUser extends VerticalLayout {
    private final TextField name = new TextField("Name");
    private final TextField username = new TextField("Username");
    private final EmailField email = new EmailField("Email");
    private final PhoneNumberField phoneNumberField = new PhoneNumberField("Phone number");
    private final PasswordField password = new PasswordField("Password");
    private final PasswordField confirmPassword = new PasswordField("Confirm" +
            " Password");
    private final UserService userService;

    public RegisterUser(UserService userService) {
        addClassName("r-v-registration-view");
        setAlignItems(FlexComponent.Alignment.CENTER);
        this.userService = userService;
        Div layout = getDivLayout();
        add(layout);
    }

    private Div getDivLayout() {
        Div layout = new Div();
        layout.addClassName("r-div");
        var header = new H3("Registration Form");
        header.addClassName("r-v-h3");
        var registerButton = new Button("Register", e -> registerUser());
        registerButton.addClassName("r-v-button");
        var link = new RouterLink("Already have an account? Login",
                LoginView.class);
        FormLayout formLayout = getFormLayout(link);
        layout.add(header, formLayout,
                registerButton, link);
        return layout;
    }

    private FormLayout getFormLayout(RouterLink link) {
        link.addClassName("forgot-password-router-link");
        setUpUserNameTextField();
        setUpNameField();
        setUpPhoneNumberField();
        setUpPasswordFiled();
        setUpConfirmPasswordFiled();
        setEmailField();
        FormLayout formLayout = new FormLayout(name, username, email, phoneNumberField,
                password, confirmPassword);
        formLayout.addClassName("r-v-form-layout");
        return formLayout;
    }

    private void registerUser() {
        // Add register button login here to check if everything is correct or not
        // If not correct, then show an error message
        // based on the error.
        if (!isTheFormOkay()) {
            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

            Div text = new Div(new Text("Please fill up the properly" +
                    " before proceeding any further"));

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
        User user = new User();
        user.setEmail(email.getValue());
        user.setPhoneNumber(phoneNumberField.countryCode.getValue()
                + phoneNumberField.number.getValue());
        user.setName(name.getValue());
        user.setUsername(username.getValue());
        user.setRoles(new HashSet<>());
        // Todo: probably do a try catch to catch any error
        // and log it out.
        if (email.getValue().endsWith("@g.bracu.ac.bd")) {
            user.getRoles().add(Role.ADMIN);
        }
        user.getRoles().add(Role.USER);
        userService.registerUser(user, password.getValue());
        showSuccessConfirmation();
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
        Div successMessage = new Div(new Span("Registration " +
                "successful! Please check your email for further instructions."));
        successMessage.addClassName("success-message");
        successCard.add(successMessage);
        add(successCard);
    }

    @SuppressWarnings("DuplicatedCode")
    private boolean isTheFormOkay() {
        boolean isOkay = true;
        if (name.isEmpty() || name.isInvalid()) {
            name.setInvalid(true);
            isOkay = false;
        }
        if (username.isInvalid() || username.isEmpty()) {
            username.setInvalid(true);
            isOkay = false;
        }
        if (password.isEmpty() || password.isInvalid()) {
            password.setInvalid(true);
            isOkay = false;
        }
        if (confirmPassword.isEmpty() ||
                confirmPassword.isInvalid()) {
            confirmPassword.setInvalid(true);
            isOkay = false;
        }
        if (phoneNumberField.isEmpty() ||
                phoneNumberField.isInvalid()) {
            phoneNumberField.setInvalid(true);
            isOkay = false;
        }
        if (email.isEmpty() || email.isInvalid()) {
            email.setInvalid(true);
            isOkay = false;
        }
        return isOkay;
    }

    private void setUpNameField() {
        name.setPlaceholder("Enter your full name");
        name.setRequiredIndicatorVisible(true);
        name.setErrorMessage("This field is required and can't be empty");
        name.setRequired(true);
        name.addClassName("r-v-text-field");
        name.addValueChangeListener(event -> checkIfEmpty(event.getValue()));
    }

    private void setUpPhoneNumberField() {
        phoneNumberField.setErrorMessage("Please enter a valid phone number");
        phoneNumberField.setRequiredIndicatorVisible(true);
        phoneNumberField.number.setPlaceholder("Please enter your phone number");
    }

    private void checkIfEmpty(String value) {
        System.out.println(value.equals(""));
        System.out.println("Value = " + value);
        name.setErrorMessage("Please enter your fullname. It's can't be empty");
        name.setInvalid(value.equals(""));
    }

    private void setEmailField() {
        email.setRequiredIndicatorVisible(true);
        email.setPlaceholder("Enter your email");
        email.setClearButtonVisible(true);
        email.setErrorMessage("Please enter a valid email address");
        email.addClassName("r-v-email-field");
        email.addValueChangeListener(event ->
                isEmailInUse(event.getValue()
                ));
    }

    private void isEmailInUse(String emailAddress) {
        final String pattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,63}$";
        if (userService.findUserByEmail(emailAddress)) {
            email.setErrorMessage("This email address is already in use");
            email.setInvalid(true);
        } else if (email.getValue().equals("")) {
            email.setErrorMessage("Email address can't be empty!");
            email.setInvalid(true);
        } else if (!emailAddress.matches(pattern)) {
            email.setErrorMessage("Please enter a valid email address");
        } else email.setInvalid(false);
    }

    private void setUpUserNameTextField() {
        username.setPlaceholder("Enter your username");
        username.setRequiredIndicatorVisible(true);
        username.setClearButtonVisible(true);
        username.setErrorMessage("Username must be between 6-25 char");
        username.setRequired(true);
        username.addClassName("r-v-text-field");
        username.setMaxLength(12);
        username.setMinLength(4);
        username.addValueChangeListener(event ->
                usernameIsInUse(event.getValue()));
    }

    private void usernameIsInUse(String curUsernameValue) {
        if (userService.userNameIsInUse(curUsernameValue)) {
            username.setErrorMessage("This username is already taken");
            username.setInvalid(true);
        } else if (username.getValue().equals("")) {
            username.setErrorMessage("username can't be empty");
            username.setInvalid(true);
        } else
            username.setInvalid(false);
    }

    private void setUpPasswordFiled() {
        password.setPlaceholder("Enter your password");
        password.setRequired(true);
        password.setRequiredIndicatorVisible(true);
        password.setErrorMessage("The password must need to be a " +
                "valid password between 1-16");
        password.setMinLength(1);
        password.setMaxLength(16);
        password.addValueChangeListener(event ->
                checkIfPasswordsMatches());
        password.addClassName("r-v-password-field");
    }

    private void checkIfPasswordsMatches() {
        if (!confirmPassword.isEmpty() &&
                !confirmPassword.getValue()
                        .equals(password.getValue())) {
            password.setErrorMessage("The passwords do not match");
            password.setInvalid(true);
        } else {
            password.setInvalid(false);
        }
    }

    private void setUpConfirmPasswordFiled() {
        confirmPassword.setPlaceholder("Enter your password again");
        confirmPassword.setRequired(true);
        confirmPassword.setRequiredIndicatorVisible(true);
        confirmPassword.setErrorMessage("The passwords do not match");
        confirmPassword.addValueChangeListener(event -> {
            if (!confirmPassword.getValue().equals(password.getValue())) {
                confirmPassword.setInvalid(true);
                return;
            }
            confirmPassword.setInvalid(false);
        });
        confirmPassword.addClassName("r-v-password-field");
    }

    // Setting up a custom field.
    private static class PhoneNumberField extends CustomField<String> {
        private final ComboBox<String> countryCode = new ComboBox<>();
        private final TextField number = new TextField();

        public PhoneNumberField(String label) {
            setLabel(label);
            number.addClassName("r-v-text-field");
            countryCode.addClassName("r-v-text-field");
            countryCode.setWidth("120px");
            countryCode.setPlaceholder("Country");
            ArrayList<String> list = new ArrayList<>(Arrays.asList("+880", "+91", "+62", "+98",
                    "+964", "+353", "+44", "+972", "+39", "+225"));
            countryCode.setItems(list);
            countryCode.setValue("+880");
            number.setAllowedCharPattern("\\d");
            HorizontalLayout layout = new HorizontalLayout(countryCode, number);
            layout.setFlexGrow(1.0, number);
            add(layout);
        }

        @Override
        protected String generateModelValue() {
            if (countryCode.getValue() != null && number.getValue() != null) {
                return countryCode.getValue() + " " + number.getValue();
            }
            return "";
        }

        @Override
        @SuppressWarnings("DuplicatedCode")
        protected void setPresentationValue(String phoneNumber) {
            String[] parts = phoneNumber != null ?
                    phoneNumber.split(" ", 2)
                    : new String[0];
            if (parts.length == 1) {
                countryCode.clear();
                number.setValue(parts[0]);
            } else if (parts.length == 2) {
                countryCode.setValue(parts[0]);
                number.setValue(parts[1]);
            } else {
                countryCode.clear();
                number.clear();
            }
        }
    }
}

