package com.cse471.project.views.userlist;

import com.cse471.project.entity.Role;
import com.cse471.project.entity.User;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

import java.io.ByteArrayInputStream;

public class UserListViewCard extends ListItem {

    public UserListViewCard(User user) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN,
                AlignItems.START, Padding.MEDIUM, BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX,
                AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM,
                Width.FULL);
        div.setHeight("160px");
        if (user.getProfilePicture() != null
                && user.getProfilePicture().length != 0) {
            StreamResource imageResources =
                    new StreamResource("profile-picture.jpg",
                            () -> new ByteArrayInputStream(user.getProfilePicture()));
            Image image = new Image(
                    imageResources,
                    "ProfilePicture.png");
            image.setWidth("100%");
            image.setAlt(user.getId().toString());
            div.add(image);
        } else {
            var notFoundImage = new Image("images/not-found.png",
                    "not-found-404");
            notFoundImage.setWidth("100%");
            div.add(notFoundImage);
        }

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(user.getUsername());

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText(user.getAccountCreated().toString());

        Paragraph description = new Paragraph(user.getAboutYourSelf());
        description.addClassName(Margin.Vertical.MEDIUM);

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        if (user.getRoles().contains(Role.ADMIN)) {
            badge.setText("ADMIN");
        } else if (user.getRoles().contains(Role.LAWYER)) {
            badge.setText("Lawyer");
        } else badge.setText("Member");
        add(div, header, subtitle, description, badge);

    }
}
