package com.cse471.project.views.login;

import com.cse471.project.views.privacypolicy.PrivacyAndPolicyView;
import com.cse471.project.views.termsandconditions.TermsAndConditionsView;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.cookieconsent.CookieConsent;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login")
public class LoginView extends VerticalLayout
        implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();

    public LoginView() {
        setSizeFull();
        addClassName("parent-login-view");
        login.addClassName("login-view-login-form");
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        Div div = new Div();
        VerticalLayout vl = new VerticalLayout();
        vl.setAlignItems(Alignment.CENTER);
        div.add(vl);
        div.addClassName("container");
        login.setAction("login");
        H1 title = new H1("Lawyer Consultation");
        title.addClassName("login-view-title");
        Button register = new Button("Register");
        register.addClassName("button");
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);
        Span signUpPrompt = new Span("New Here? ");
        signUpPrompt.addClassName("login-view-sing-up-prompt");
        var userRegistrationPrompt =
                new HorizontalLayout(signUpPrompt,
                        register);
        userRegistrationPrompt
                .setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        var cookie = new CookieConsent();
        cookie.setCookieName("Analytic Cookies");
        cookie.setDismissLabel("Got it");
        cookie.setLearnMoreLabel("Click to learn more");
        cookie.setLearnMoreLink("http://localhost:8080/privacy-policy");
        login.addForgotPasswordListener(forgotPasswordEvent ->
                UI.getCurrent().
                        navigate("forgot-password"));
        register.addClickListener(buttonClickEvent -> UI.getCurrent()
                .navigate("register-user"));
        // Below adding the privacy-policy and Terms of condition Router Links
        HorizontalLayout hl = new HorizontalLayout();
        hl.addClassName("login-view-r-hl");
        hl.setSpacing(false);
        RouterLink termsOfServices = new RouterLink("Terms of Conditions",
                TermsAndConditionsView.class);
        termsOfServices.addClassName("login-view-tos");
        RouterLink privacyAndPolicyView = new RouterLink("Privacy & Policy",
                PrivacyAndPolicyView.class);
        privacyAndPolicyView.addClassName("login-view-privacy-and-policy");
        hl.add(termsOfServices, privacyAndPolicyView);
        hl.setWidthFull();
        hl.setJustifyContentMode(JustifyContentMode.BETWEEN);
        vl.add(title, login, userRegistrationPrompt, hl, cookie);
        add(div);
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation().getQueryParameters().
                getParameters().containsKey("error")) {
            login.setError(true);
        }
    }
}
