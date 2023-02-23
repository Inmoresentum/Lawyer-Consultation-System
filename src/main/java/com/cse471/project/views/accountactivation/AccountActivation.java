package com.cse471.project.views.accountactivation;

import com.cse471.project.entity.User;
import com.cse471.project.entity.UserVerificationToken;
import com.cse471.project.repository.UserRepository;
import com.cse471.project.repository.UserVerificationTokenRepository;
import com.cse471.project.views.login.LoginView;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Route("/activate")
@AnonymousAllowed
public class AccountActivation extends VerticalLayout implements BeforeEnterObserver {
    private final UserVerificationTokenRepository userVerificationTokenRepository;
    private final UserRepository userRepository;

    public AccountActivation(UserVerificationTokenRepository userVerificationTokenRepository,
                             UserRepository userRepository) {
        this.userVerificationTokenRepository = userVerificationTokenRepository;
        this.userRepository = userRepository;
        addClassName("ac-verify");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        var params = beforeEnterEvent.getLocation()
                .getQueryParameters().getParameters();
        try {
            verifyToken(params);
            createSuccessView();
        } catch (IllegalStateException e) {
            createUnsuccessfulView();
        }
    }

    private void createUnsuccessfulView() {
        setAlignItems(Alignment.CENTER);
        Div div = new Div();
        div.addClassName("ac-unsuccessful-card");
        getStyle().set("position", "absolute");
        getStyle().set("top", "50%");
        getStyle().set("left", "50%");
        getStyle().set("transform", "translate(-50%, -50%)");
        Icon firstBrokenIcon = new Icon(VaadinIcon.WARNING);
        firstBrokenIcon.getStyle().set("animation", "spin 2s linear infinite");
        firstBrokenIcon.getStyle().set("font-size", "6rem");
        firstBrokenIcon.getStyle().set("color", "#f00");
        Icon secondBrokenIcon = new Icon(VaadinIcon.WARNING);
        secondBrokenIcon.getStyle().set("animation", "spin 2s linear infinite");
        secondBrokenIcon.getStyle().set("font-size", "6rem");
        secondBrokenIcon.getStyle().set("color", "#f00");
        Icon thirdBrokenIcon = new Icon(VaadinIcon.WARNING);
        thirdBrokenIcon.getStyle().set("animation", "spin 2s linear infinite");
        thirdBrokenIcon.getStyle().set("font-size", "6rem");
        thirdBrokenIcon.getStyle().set("color", "#f00");
        HorizontalLayout hl = new HorizontalLayout(firstBrokenIcon, secondBrokenIcon, thirdBrokenIcon);
        div.add(hl);
        H1 h1 = new H1("404");
        Span span = new Span("The link is broken");
        VerticalLayout vl = new VerticalLayout(h1, span);
        div.add(vl);
        add(div);
    }

    private void createSuccessView() {
        Div div = new Div();
        div.addClassName("ac-verify-card");
        H3 h3 = new H3("Verification Successful!");
        Icon icon = new Icon(VaadinIcon.CHECK_CIRCLE);
        icon.addClassName("ac-verify-icon");
        HorizontalLayout hl = new HorizontalLayout(icon, h3);
        hl.addClassName("ac-verify-hl-s");
        RouterLink routerLink = new RouterLink("Please Click Here To Login", LoginView.class);
        routerLink.addClassName("ac-verify-router-link");
        VerticalLayout vl = new VerticalLayout(hl, routerLink);
        vl.addClassName("ac-verify-vl-s");
        div.add(vl);
        add(div);
    }

    private void verifyToken(Map<String, List<String>> params) {
        String token = params.get("token").get(0);
        System.out.println(token + " from activate view");
        UserVerificationToken userVerificationToken = userVerificationTokenRepository.findByToken(token).orElseThrow(() ->
                new IllegalStateException("Token Not Found"));
        if (userVerificationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("USER already verified");
        }

        // Verify it later.
        LocalDateTime expireAt = userVerificationToken.getExpiresAt();
        if (LocalDateTime.now().isAfter(expireAt)) {
            throw new IllegalStateException("This is already experienced");
        }
        //Used the query method that I created. We can also update it without the custom query method.
        userVerificationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
        User user = userVerificationToken.getUser();
        user.setActive(true);
        userRepository.save(user);
    }
}
