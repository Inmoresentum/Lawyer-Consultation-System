package com.cse471.project.views.lawyerlist;

import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@Route(value = "lawyer-list", layout = MainLayout.class)
@RolesAllowed({"ADMIN", "USER", "LAWYER"})
@PageTitle("Lawyer List")
public class LawyerListView extends VerticalLayout {
    public LawyerListView() {
        add(new H1("Hi from lawyer list view"));
    }
}
