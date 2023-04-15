package com.cse471.project.views.about;

import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;


@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
@AnonymousAllowed
public class AboutView extends VerticalLayout {

    public AboutView() {
        setSpacing(false);

        Image img = new Image("images/application-main.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        H1 header = new H1("What we do?");
        header.addClassNames("what-we-do");
        ///Margin.Top.XLARGE, Margin.Bottom.MEDIUM
        add(header);
        add(new Paragraph("Our motto is to take your legal action's personally. We believe in making your work or life secured." +
                "Lawyer Consultancy is a professional legal platform. Here we will provide you proper guidelines for any" +
                " legal issues. We are dedicated providing you the best service online. We hope you enjoy our lawyer's" +
                " consultancy as much we enjoy offering them to you."));

        H2 newHeader= new H2("Thanks for visiting our site");
        newHeader.addClassNames("new-para");
        add(newHeader);

        H2 otherHeader = new H2("Have a nice day!");
        otherHeader.addClassNames("other-para");
        add(otherHeader);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");






    }

}
