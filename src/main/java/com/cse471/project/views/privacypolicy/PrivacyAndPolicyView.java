package com.cse471.project.views.privacypolicy;

import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import org.bouncycastle.jcajce.provider.symmetric.DES;

@PageTitle("Privacy And Policy")
@Route(value = "privacy-policy", layout = MainLayout.class)
@AnonymousAllowed
public class PrivacyAndPolicyView extends VerticalLayout {

    public PrivacyAndPolicyView() {
        Section mySection = new Section();
        H1 title = new H1("Privacy Policy");
        title.addClassName("page-title");
        mySection.add(title);
        Paragraph myParagraph = new Paragraph();
        myParagraph.setText("At Lawyer consultancy,accessible from localhost:8080, one of our main priorities is the privacy of our visitors. This Privacy Policy document contains types of information that is collected and recorded by Lawyer consultancy and all the information of the users are encrypted in the database.If you have additional questions or require more information about our Privacy Policy, do not hesitate to contact us. This Privacy Policy applies only to our online activities and is valid for visitors to our website with regards to the information that they shared and/or collected in Lawyer consultancy. This policy is not applicable to any information collected offline or via channels other than this website.");
        mySection.add(myParagraph);
        addClassName("privacy-policy-view-main");

        H3 anotherTitle = new H3("Consent");
        mySection.add(anotherTitle);
        Paragraph anotherParagraph = new Paragraph();
        anotherParagraph.setText("By using our website, you hereby consent to our Privacy Policy and agree to its terms.");
        mySection.add(anotherParagraph);
        ///anotherParagraph.addClassName("p-p-consent");

        H3 otherTitle = new H3("Information we collect");
        mySection.add(otherTitle);
        Paragraph otherParagraph = new Paragraph();
        otherParagraph.setText("The personal information that you are asked to provide, and the reasons why you are asked to provide it, will be made clear to you at the point we ask you to provide your personal information. If you contact us directly, we may receive additional information about you such as your name, email address, phone number, the contents of the message and/or attachments you may send us, and any other information you may choose to provide. When you register for an account we may ask you to provide your name, mobile phone number, gender, email, address, birth date.You will also be asked to update your profile picture but that is optional and depends on you.");
        mySection.add(otherParagraph);



        H3 fourthTitle = new H3("How we use your information");
        mySection.add(fourthTitle);
        Paragraph fourthParagraph = new Paragraph();
        fourthParagraph.setText("We use the information we collect in various ways, including to:");
        ListItem item1 = new ListItem("Provide, operate, and maintain our website");
        ListItem item2= new ListItem("Improve, personalize, and expand our website");
        ListItem item3 = new ListItem("Understand and analyze how you use our website");
        ListItem item4 = new ListItem("Develop new services, features, and functionality");
        ListItem item5 = new ListItem("Communicate with you through phone or email");
        ListItem item6 = new ListItem("Send you emails");
        ListItem item7 = new ListItem("Find and prevent fraud");
        UnorderedList list = new UnorderedList(item1, item2, item3, item4, item5, item6, item7);
        mySection.add(fourthParagraph);
        ///add(list);
        add(mySection);




        H3 fifthTitle = new H3("Cookies");
        mySection.add(fifthTitle);
        Paragraph fifthParagraph = new Paragraph();
        fifthParagraph.setText("Like any other website, Lawyer consultancy uses \"cookies\". These cookies are used to store information including visitors' preferences, and the pages on the website that the visitor accessed or visited. The information is used to optimize the users' experience by customizing our web page content based on visitors' browser type and/or other information.");
        mySection.add(fifthParagraph);


        H3 sixthTitle = new H3("Third Party Privacy Policies");
        mySection.add(sixthTitle);
        Paragraph sixthParagraph = new Paragraph();
        sixthParagraph.setText("Lawyer consultancy's Privacy Policy does not apply to other advertisers or websites. Thus, we are advising you to consult the respective Privacy Policies of these third-party ad servers for more detailed information. It may include their practices and instructions about how to opt-out of certain options. You can choose to disable cookies through your individual browser options. To know more detailed information about cookie management with specific web browsers, it can be found at the browsers' respective websites.");
        mySection.add(sixthParagraph);

        H3 seventhTitle = new H3("Changes to this privacy policy");
        mySection.add(seventhTitle);
        Paragraph seventhParagraph = new Paragraph();
        seventhParagraph.setText("We may update our Privacy Policy from time to time. Thus, we advise you to review this page periodically for any changes. We will notify you of any changes by posting the new Privacy Policy on this page. These changes are effective immediately, after they are posted on this page.");
        mySection.add(seventhParagraph);

        H3 eightTitle = new H3("Contact us");
        mySection.add(eightTitle);
        Paragraph eightParagraph = new Paragraph();
        eightParagraph.setText("If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us.");
        mySection.add(eightParagraph);


        add(mySection);








    }
}