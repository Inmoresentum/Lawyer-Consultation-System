package com.cse471.project.views.userprofile;

import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@Route(value = "user-profile", layout = MainLayout.class)
@PageTitle("User Profile")
@PermitAll
public class UserProfile extends VerticalLayout {
    public UserProfile() {
        add(new H1("This is where we will display " +
                "user profile and related information"));
    }
}
