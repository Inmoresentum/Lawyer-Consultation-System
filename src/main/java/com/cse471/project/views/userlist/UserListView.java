package com.cse471.project.views.userlist;

import com.cse471.project.service.UserService.UserService;
import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
import org.springframework.data.domain.PageRequest;

import javax.annotation.security.RolesAllowed;

@PageTitle("User List")
@Route(value = "", layout = MainLayout.class)
@RolesAllowed({"ADMIN", "USER", "SUPPORT", "LAWYER"})
public class UserListView extends Main implements HasComponents, HasStyle {

    private OrderedList userLists;
    private final UserService userService;
    private int curPage = 0;
    private final int PAGE_SIZE = 10;
    private int totalPageCount;
    private final Button loadPrevious = new Button("Load Previous");
    private final Button loadMoreButton = new Button("Load More");

    public UserListView(UserService userService) {
        this.userService = userService;
        constructUI();
        var listOfUser = userService.list(PageRequest.of(curPage, PAGE_SIZE));
        totalPageCount = listOfUser.getTotalPages();
        for (var user : listOfUser) {
            UserListViewCard userListViewCard = new UserListViewCard(user);
            userLists.add(userListViewCard);
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
        Paragraph description = new Paragraph("The members who built the community");
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        headerContainer.add(header, description);

        Select<String> sortBy = new Select<>();
        sortBy.setLabel("Sort by");
        sortBy.setItems("Admin", "Lawyer", "Members");
        sortBy.setValue("Members");

        sortBy.addValueChangeListener(event -> {
            // Todo: If user changes the value then fetch the
            // new value from the DB.
            // It will be both pageAble and sorted

        });
        loadPrevious.setEnabled(false);
        loadPrevious.addClassName("list-of-users-view-load-previous-button");
        loadPrevious.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
        loadPrevious.addClickListener(event -> {
            //Do the same things but in reverse
            curPage--;
            loadMoreButton.setEnabled(true);
            userLists.removeAll();
            var newlistOfUserWithPage = userService.list(PageRequest
                    .of(curPage, PAGE_SIZE));
            totalPageCount = newlistOfUserWithPage.getTotalPages();
            for (var user : newlistOfUserWithPage) {
                UserListViewCard userListViewCard = new UserListViewCard(user);
                userLists.add(userListViewCard);
            }
            if (curPage <= 0) {
                loadPrevious.setEnabled(false);
            }
        });

        userLists = new OrderedList();
        userLists.addClassNames(Gap.MEDIUM, Display.GRID,
                ListStyleType.NONE, Margin.NONE, Padding.NONE);
        container.add(headerContainer, sortBy);
        add(container, loadPrevious, userLists);
        loadMoreButton.addClassName("user-list-view-load-more-button");
        loadMoreButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);
        add(loadMoreButton);
        loadMoreButton.addClickListener(event -> {
            curPage++;
            loadPrevious.setEnabled(true);
            userLists.removeAll();
            var newlistOfUserWithPage = userService.list(PageRequest
                    .of(curPage, PAGE_SIZE));
            totalPageCount = newlistOfUserWithPage.getTotalPages();
            for (var user : newlistOfUserWithPage) {
                UserListViewCard userListViewCard = new UserListViewCard(user);
                userLists.add(userListViewCard);
            }
            if (curPage >= totalPageCount - 1) {
                loadMoreButton.setEnabled(false);
            }
        });
    }
}
