package com.cse471.project.views.login;

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
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

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
        VerticalLayout header = new VerticalLayout();
        header.addClassName("header");
        H1 title = new H1("Lawyer Consultation");
        title.addClassName("login-view-title");
        header.add(title);
        header.setAlignItems(Alignment.CENTER);
        Button register = new Button("Register");
        register.addClassName("button");
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);
        var footer = new HorizontalLayout(new Span("New Here? "), register);
        footer.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        var cookie = new CookieConsent();
        cookie.setCookieName("Analytic Cookies");
        cookie.setDismissLabel("Got it");
        cookie.setLearnMoreLabel("Click to learn more");
        cookie.setLearnMoreLink("https://www.eatthis.com/surprising-" +
                "side-effects-eating-cookies/");
        login.addForgotPasswordListener(forgotPasswordEvent -> UI.getCurrent().
                navigate("forgot-password"));
        register.addClickListener(buttonClickEvent -> UI.getCurrent()
                .navigate("register-user"));
        vl.add(header, login, footer, cookie);
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
