package com.cse471.project.views.accountactivation;

import com.cse471.project.entity.User;
import com.cse471.project.entity.VerificationToken;
import com.cse471.project.repository.UserRepository;
import com.cse471.project.repository.VerificationTokenRepository;
import com.cse471.project.views.login.LoginView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.time.LocalDateTime;

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
        String token = params.get("token").get(0);
        System.out.println(token + " from activate view");
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(() ->
                new IllegalStateException("Token Not Found"));
        if (verificationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already verified");
        }

        LocalDateTime expireAt = verificationToken.getExpiresAt();

        //Used the query method that I created. We can also update it without the custom query method.
        verificationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
        User user = verificationToken.getUser();
        user.setActive(true);
        userRepository.save(user);
        setSpacing(true);
        add(new VerticalLayout(new Text("Verification Successful!!!")),
                new RouterLink("Click here to login", LoginView.class));
    }
}
