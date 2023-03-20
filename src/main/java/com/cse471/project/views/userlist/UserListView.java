package com.cse471.project.views.userlist;

import com.cse471.project.entity.User;
import com.cse471.project.service.UserService.UserService;
import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@PageTitle("User List")
@Route(value = "", layout = MainLayout.class)
@RolesAllowed({"ADMIN", "USER", "SUPPORT", "LAWYER"})
public class UserListView extends Main implements HasComponents, HasStyle {

    private OrderedList userLists;
    private final UserService userService;

    public UserListView(UserService userService) {
        this.userService = userService;
        constructUI();
        List<User> listOfUser = userService.findAllUser();
        for (var user : listOfUser) {
            userLists.add(new UserListViewCard(user));
        }
    }

    private void constructUI() {
        addClassNames("user-list-view");
        addClassNames(MaxWidth.SCREEN_LARGE,
                Margin.Horizontal.AUTO, Padding.Bottom.LARGE,
                Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("List of users");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE,
                FontSize.XXXLARGE);
        Paragraph description = new Paragraph("Royalty free photos and pictures, courtesy of Unsplash");
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        headerContainer.add(header, description);

        Select<String> sortBy = new Select<>();
        sortBy.setLabel("Sort by");
        sortBy.setItems("Admin", "Lawyer", "Members");
        sortBy.setValue("Members");

        // Need to implement lazy loading.

        userLists = new OrderedList();
        userLists.addClassNames(Gap.MEDIUM, Display.GRID,
                ListStyleType.NONE, Margin.NONE, Padding.NONE);

        container.add(headerContainer, sortBy);
        add(container, userLists);
    }
}
