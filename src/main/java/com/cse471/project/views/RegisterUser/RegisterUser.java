package com.cse471.project.views.RegisterUser;

import com.cse471.project.entity.Role;
import com.cse471.project.entity.User;
import com.cse471.project.service.UserService.UserService;
import com.cse471.project.views.login.LoginView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
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

import java.util.Collections;

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
        Div layout = new Div();
        layout.addClassName("r-div");
        var header = new H3("Register");
        header.addClassName("r-v-h3");
        var registerButton = new Button("Register", e -> register());
        registerButton.addClassName("r-v-button");
        var link = new RouterLink("Already have an account? Login",
                LoginView.class);
        // Adding the same css className so that I
        // can reuse the previous css.
        link.addClassName("forgot-password-router-link");
        setUpUserNameTextField();
        setUpNameField();
        setUpPasswordFiled();
        setUpConfirmPasswordFiled();
        setEmailField();
        FormLayout formLayout = new FormLayout(name, username, email, phoneNumberField,
                password, confirmPassword);
        formLayout.addClassName("r-v-form-layout");
        layout.add(header, formLayout,
                registerButton, link);
        add(layout);
    }

    private void register() {
        // Add register button login here to check if everything is correct or not
        // If not correct, then show an error message
        // based on the error.
        User user = new User();
        if (name.getValue() != null) {
            user.setName(name.getValue());
        }
        if (username.getValue() != null) {
            user.setUsername(username.getValue());
        }
        if (email.getValue() != null && email.getValue().endsWith("@g.bracu.ac.bd")) {
            user.setRoles(Collections.singleton(Role.ADMIN));
            user.setEmail(email.getValue());
        } else if (email.getValue() != null) {
            user.setRoles(Collections.singleton(Role.USER));
            user.setEmail(email.getValue());
        }
        if (phoneNumberField.number.getValue() != null) {
            user.setPhoneNumber(phoneNumberField.number.getValue());
        }
        userService.registerUser(user, password.getValue());
        removeAll();
        add(new Text("User registration successfully!! Please check email For further instructions!"));
        Notification.show("Registration successful!")
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void checkError() {
    }

    private void setUpNameField() {
        name.setPlaceholder("Enter your name");
        name.setRequiredIndicatorVisible(true);
        name.setErrorMessage("You provide a valid username");
        name.setRequired(true);
        name.addClassName("r-v-text-field");
    }

    private void setEmailField() {
        email.setRequiredIndicatorVisible(true);
        email.setPlaceholder("Enter your email");
        email.setClearButtonVisible(true);
        email.setErrorMessage("Please enter a valid email address");
        email.addClassName("r-v-email-field");
    }

    private void setUpUserNameTextField() {
        username.setPlaceholder("Enter your username");
        username.setRequiredIndicatorVisible(true);
        username.setErrorMessage("Username must be between 6-25 char");
        username.setRequired(true);
        username.addClassName("r-v-text-field");
    }

    private void setUpPasswordFiled() {
        password.setPlaceholder("Enter your password");
        password.setRequired(true);
        password.setRequiredIndicatorVisible(true);
        password.setErrorMessage("The password must need to be a " +
                "valid password between 1-16");
        password.setMinLength(1);
        password.setMaxLength(16);
        password.addValueChangeListener(event -> {
            if (!confirmPassword.isEmpty() &&
                    !confirmPassword.getValue()
                            .equals(password.getValue())) {
                password.setErrorMessage("The passwords do not match");
                password.setInvalid(true);
            } else {
                password.setInvalid(false);
            }
        });
        password.addClassName("r-v-password-field");
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
        addClassName("r-v-password-field");
    }

    private static class PhoneNumberField extends CustomField<String> {
        private final ComboBox<String> countryCode = new ComboBox<>();
        private final TextField number = new TextField();

        public PhoneNumberField(String label) {
            setLabel(label);
            countryCode.setWidth("120px");
            countryCode.setPlaceholder("Country");
            countryCode.setAllowedCharPattern("[\\+\\d]");
            countryCode.setItems("+880", "+91", "+62", "+98", "+964", "+353", "+44", "+972", "+39", "+225");
            countryCode.addCustomValueSetListener(e -> countryCode.setValue(e.getDetail()));
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
        protected void setPresentationValue(String phoneNumber) {
            String[] parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0];
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

