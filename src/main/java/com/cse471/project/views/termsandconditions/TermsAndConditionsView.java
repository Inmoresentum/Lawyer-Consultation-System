package com.cse471.project.views.termsandconditions;

import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("Terms and Conditions")
@Route(value = "terms-and-conditions", layout = MainLayout.class)
@AnonymousAllowed
public class TermsAndConditionsView extends VerticalLayout {

    public TermsAndConditionsView() {
        addClassName("tos-main");
        setSpacing(false);

        Image img = new Image("images/application-main.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        H2 header = new H2("Terms");
        header.addClassNames("our-terms");
        add(header);
        add(new Paragraph("By accessing this Website, accessible from localhost:8080, you are agreeing to be bound by these Website Terms and Conditions of Use and agree that you are responsible for the agreement with any applicable local laws. If you disagree with any of these terms, you are prohibited from accessing this site. The materials contained in this Website are protected by copyright"));

        H2 title = new H2("Use License");
        title.addClassNames("license-agreement");
        add(title);
        add(new Paragraph("Permission is granted to temporarily download one copy of the materials on Lawyer consultancy's" +
                " Website for personal, non-commercial transitory viewing only. This is the grant of a license," +
                "not a transfer of title, and under this license you may not: "));
        ListItem item1 = new ListItem("modify or copy the materials;");
        item1.addClassName("tos-list-item");
        ListItem item2 = new ListItem("use the materials for any commercial purpose or for any public display;\n");
        item2.addClassName("tos-list-item");
        ListItem item3 = new ListItem("remove any copyright or other proprietary notations from the materials; or\n");
        item3.addClassName("tos-list-item");
        ListItem item4 = new ListItem("transferring the materials to another person or \"mirror\" the materials on any other server.\n");
        item4.addClassName("tos-list-item");
        UnorderedList list = new UnorderedList(item1, item2, item3, item4);
        list.addClassName("terms-of-conditions-list-items");
        add(list);

        add(new Paragraph("This will let Lawyer consultancy to terminate upon violations of any of these restrictions." +
                " Upon termination, your viewing right will also be terminated and you should destroy any downloaded materials " +
                " in your possession whether it is printed or electronic format. These Terms of Service have been created with the help of the Terms Of Service Generator."));

        H2 newHeader = new H2("Limitations");
        newHeader.addClassNames("our-limits");
        add(newHeader);
        add(new Paragraph("Lawyer consultancy or its suppliers will not be held accountable for any damages that" +
                " will arise with the use or inability to use the materials on Lawyer consultancy's Website," +
                " even if Lawyer consultancy or an authorized representative of this Website has been " +
                " notified, orally or written, of the possibility of such damage. Some jurisdiction does not " +
                " allow limitations on implied warranties or limitations of liability for incidental damages," +
                " these limitations may not apply to you."));


        H2 otherHeader = new H2("Links");
        otherHeader.addClassNames("our-links");
        add(otherHeader);
        add(new Paragraph("Lawyer consultancy has not reviewed all of the sites linked to its Website and is not" +
                " responsible for the contents of any such linked site. The presence of any link " +
                " does not imply endorsement by Lawyer consultancy of the site. The use of any " +
                "  linked website is at the user's own risk."));

        H2 fourthHeader = new H2("Your privacy");
        fourthHeader.addClassNames("your-pri");
        add(fourthHeader);
        add(new Paragraph("Please read our Privacy Policy mentioned above."));

        H2 fifthHeader = new H2("Governing Law");
        fifthHeader.addClassNames("our-law");
        add(fifthHeader);
        add(new Paragraph("Any claim related to Lawyer consultancy's " +
                " Website shall be governed by the laws of bd without regards to its conflict of law provisions."));






        ///setSizeFull();
        ///setJustifyContentMode(JustifyContentMode.CENTER);
        ///setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        ///getStyle().set("text-align", "center");
    }

}
