package com.cse471.project.views.lawyerprofile;

import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "lawyer-profile", layout = MainLayout.class)
public class LawyerProfile extends VerticalLayout {
    public LawyerProfile() {
        add(new H1("Hi from lawyer profile"));
    }
}
