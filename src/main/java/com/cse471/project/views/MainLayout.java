package com.cse471.project.views;

import com.cse471.project.components.appnav.AppNav;
import com.cse471.project.components.appnav.AppNavItem;
import com.cse471.project.entity.User;
import com.cse471.project.security.AuthenticatedUser;
import com.cse471.project.views.about.AboutView;
import com.cse471.project.views.allusers.AllUsersView;
import com.cse471.project.views.communitychat.CommunityChatView;
import com.cse471.project.views.dashboard.DashboardView;
import com.cse471.project.views.feedback.FeedbackView;
import com.cse471.project.views.personalnotes.PersonalNotesView;
import com.cse471.project.views.support.SupportView;
import com.cse471.project.views.userlist.UserListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.io.ByteArrayInputStream;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    private final AuthenticatedUser authenticatedUser;
    private final AccessAnnotationChecker accessChecker;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Lawyer Consultation");
        Image mainLogo = new Image("images/application-main.png", "main-logo-main");
        mainLogo.setWidth("150px");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(new VerticalLayout(mainLogo, appName));

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        // AppNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        AppNav nav = new AppNav();
        nav.addClassName("main-layout-nav-bar");

        if (accessChecker.hasAccess(UserListView.class)) {
            nav.addItem(new AppNavItem("User List", UserListView.class,
                    "la la-user-circle"));

        }
        if (accessChecker.hasAccess(DashboardView.class)) {
            nav.addItem(new AppNavItem("Dashboard",
                    DashboardView.class, "la la-chart-area"));

        }
        if (accessChecker.hasAccess(AllUsersView.class)) {
            nav.addItem(new AppNavItem("All Users", AllUsersView.class,
                    "la la-user-cog"));

        }
        if (accessChecker.hasAccess(CommunityChatView.class)) {
            nav.addItem(new AppNavItem("Community Chat",
                    CommunityChatView.class, "la la-comments"));

        }
        if (accessChecker.hasAccess(PersonalNotesView.class)) {
            nav.addItem(new AppNavItem("Personal Notes",
                    PersonalNotesView.class, "la la-edit"));

        }
        if (accessChecker.hasAccess(FeedbackView.class)) {
            nav.addItem(new AppNavItem("Feedback",
                    FeedbackView.class, "la la-user"));

        }
        if (accessChecker.hasAccess(SupportView.class)) {
            nav.addItem(new AppNavItem("Support",
                    SupportView.class, "lab la-think-peaks"));

        }
        if (accessChecker.hasAccess(AboutView.class)) {
            nav.addItem(new AppNavItem("About",
                    AboutView.class, "la la-info"));

        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            Avatar avatar = new Avatar(user.getName());

            if (user.getProfilePicture() != null) {
                StreamResource resource = new StreamResource("profile-pic",
                        () -> new ByteArrayInputStream(user.getProfilePicture()));
                avatar.setImageResource(resource);
            }
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(user.getName());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Profile", e -> UI.getCurrent().navigate("user-profile"));
            userName.getSubMenu().addItem("Change Password", e -> UI.getCurrent().navigate("change-user-password"));
            // Sign out needs to be at the bottom
            userName.getSubMenu().addItem("Sign out", e -> authenticatedUser.logout());

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
