package com.cse471.project.views.lawyerroleapplication;

import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Lawyer Application Form")
@Route(value = "lawyer-application", layout = MainLayout.class)
@RolesAllowed({"ADMIN", "USER"})
public class LawyerRoleApplication extends VerticalLayout {
    public LawyerRoleApplication() {
        add(new H1("Hi from lawyer application"));
    }
}
