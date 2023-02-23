package com.cse471.project.views.accountactivation;

import com.cse471.project.entity.User;
import com.cse471.project.entity.VerificationToken;
import com.cse471.project.repository.UserRepository;
import com.cse471.project.repository.VerificationTokenRepository;
import com.cse471.project.views.login.LoginView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
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
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    public AccountActivation(VerificationTokenRepository verificationTokenRepository,
                             UserRepository userRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        var params = beforeEnterEvent.getLocation()
                .getQueryParameters().getParameters();
        try {
            verifyToken(params);
            createUnsuccessfulView();
        } catch (IllegalStateException e) {
            createSuccessView();
        }
    }

    private void createUnsuccessfulView() {
        addClassName("ac-verify");
        add(new H1("There is something with the link"));
    }

    private void createSuccessView() {
        addClassName("ac-verify");
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
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(() ->
                new IllegalStateException("Token Not Found"));
        if (verificationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already verified");
        }

        // Verify it later.
        LocalDateTime expireAt = verificationToken.getExpiresAt();

        //Used the query method that I created. We can also update it without the custom query method.
        verificationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
        User user = verificationToken.getUser();
        user.setActive(true);
        userRepository.save(user);
    }
}
