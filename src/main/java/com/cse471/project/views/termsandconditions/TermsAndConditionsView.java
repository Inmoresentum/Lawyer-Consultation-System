package com.cse471.project.views.termsandconditions;

import com.cse471.project.views.MainLayout;
import com.cse471.project.views.privacypolicy.PrivacyAndPolicyView;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Terms and Conditions")
@Route(value = "terms-and-conditions", layout = MainLayout.class)
@AnonymousAllowed
public class TermsAndConditionsView extends VerticalLayout {

    public TermsAndConditionsView() {
        addClassName("tos-main-view");

        Div mainContainerDiv = new Div();
        mainContainerDiv.addClassName("tos-main-container-div");

        Image logo = new Image("images/application-main.png", "placeholder plant");
        logo.addClassName("tos-app-logo");
        mainContainerDiv.add(logo);

        Div firstDiv = new Div();
        firstDiv.addClassName("tos-view-div1");
        H2 header = new H2("Terms");
        header.addClassNames("tos-view-h2");
        firstDiv.add(header);
        Paragraph firstParagraph = new Paragraph("By accessing this Website, accessible from localhost:8080, " +
                "you are agreeing to be bound by these Website Terms and Conditions of Use " +
                "and agree that you are responsible for the agreement with any applicable local" +
                " laws. If you disagree with any of these terms, you are prohibited " +
                "from accessing this site. The materials contained in this Website" +
                " are protected by copyright");
        firstParagraph.addClassName("tos-view-p");
        firstDiv.add(firstParagraph);

        mainContainerDiv.add(firstDiv);

        Div secondDiv = new Div();
        secondDiv.addClassName("tos-view-div2");
        H2 title = new H2("Use License");
        title.addClassNames("tos-view-h2");
        secondDiv.add(title);
        Paragraph secondParagraph = new Paragraph("Permission is granted to temporarily" +
                " download one copy of the materials on Lawyer consultancy's" +
                " Website for personal, non-commercial transitory viewing only." +
                " This is the grant of a license," +
                " not a transfer of title, and under this license you may not: ");
        secondParagraph.addClassName("tos-view-p");
        secondDiv.add(secondParagraph);
        ListItem item1 = new ListItem("modify or copy the materials;");
        item1.addClassName("tos-list-item");
        ListItem item2 = new ListItem("use the materials for any commercial purpose" +
                " or for any public display;\n");
        item2.addClassName("tos-list-item");
        ListItem item3 = new ListItem("remove any copyright or other proprietary" +
                " notations from the materials; or\n");
        item3.addClassName("tos-list-item");
        ListItem item4 = new ListItem("transferring the materials" +
                " to another person or \"mirror\" the materials on any other server.\n");
        item4.addClassName("tos-list-item");
        UnorderedList list = new UnorderedList(item1, item2, item3, item4);
        list.addClassName("tos-view-list-items");
        secondDiv.add(list);

        Paragraph thirdParagraph = new Paragraph("This will let Lawyer consultancy to terminate" +
                " upon violations of any of these restrictions." +
                " Upon termination, your viewing right will also be" +
                " terminated and you should destroy any downloaded materials " +
                " in your possession whether it is printed or electronic format." +
                " These Terms of Service have been created with the help of the Terms" +
                " Of Service Generator.");
        thirdParagraph.addClassName("tos-view-p");
        secondDiv.add(thirdParagraph);

        mainContainerDiv.add(secondDiv);

        Div thirdDiv = new Div();
        thirdDiv.addClassName("tos-view-div-3");
        H2 newHeader = new H2("Limitations");
        newHeader.addClassNames("tos-view-h2");
        thirdDiv.add(newHeader);
        Paragraph fourthParagraph = new Paragraph("Lawyer consultancy or its suppliers will not be held" +
                " accountable for any damages that" +
                " will arise with the use or inability to use the materials on Lawyer consultancy's Website," +
                " even if Lawyer consultancy or an authorized representative of this Website has been " +
                " notified, orally or written, of the possibility of such damage. Some jurisdiction does not " +
                " allow limitations on implied warranties or limitations of liability for incidental damages," +
                " these limitations may not apply to you.");
        fourthParagraph.addClassName("tos-view-p");
        thirdDiv.add(fourthParagraph);

        mainContainerDiv.add(thirdDiv);

        Div fourthDiv = new Div();
        fourthDiv.addClassName("tos-view-div4");
        H2 otherHeader = new H2("Links");
        otherHeader.addClassNames("tos-view-h2");
        fourthDiv.add(otherHeader);
        Paragraph fifithParagraph = new Paragraph("Lawyer consultancy has not reviewed all of" +
                " the sites linked to its Website and is not" +
                " responsible for the contents of any such linked site. The presence of any link " +
                " does not imply endorsement by Lawyer consultancy of the site. The use of any " +
                " linked website is at the user's own risk.");
        fifithParagraph.addClassName("tos-view-p");
        fourthDiv.add(fifithParagraph);

        mainContainerDiv.add(fourthDiv);

        Div fifthDiv = new Div();
        fifthDiv.addClassName("tos-view-div5");
        H2 fourthHeader = new H2("Your privacy");
        fourthHeader.addClassNames("tos-view-h2");
        fifthDiv.add(fourthHeader);
        Paragraph sixthParagraph = new Paragraph("Please read our ");
        RouterLink redirectToPrivacyPolicy = new RouterLink("Privacy Policy",
                PrivacyAndPolicyView.class);
        redirectToPrivacyPolicy.addClassName("tos-view-router-link");
        sixthParagraph.add(redirectToPrivacyPolicy);
        sixthParagraph.add(" mentioned above.");
        sixthParagraph.addClassName("tos-view-p");
        fifthDiv.add(sixthParagraph);

        mainContainerDiv.add(fifthDiv);

        Div sixthDiv = new Div();
        sixthDiv.addClassName("tos-view-div6");
        H2 fifthHeader = new H2("Governing Law");
        fifthHeader.addClassNames("tos-view-h2");
        sixthDiv.add(fifthHeader);
        Paragraph seventhParagraph = new Paragraph("Any claim related to Lawyer consultancy's " +
                " Website shall be governed by the laws of bd without regards to its conflict of law provisions.");
        seventhParagraph.addClassName("tos-view-p");
        sixthDiv.add(seventhParagraph);
        mainContainerDiv.add(sixthDiv);
        add(mainContainerDiv);
    }
}
