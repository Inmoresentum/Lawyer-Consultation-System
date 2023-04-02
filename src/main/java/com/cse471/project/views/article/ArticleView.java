package com.cse471.project.views.article;

import com.cse471.project.entity.FAQ;
import com.cse471.project.entity.Role;
import com.cse471.project.entity.User;
import com.cse471.project.security.AuthenticatedUser;
import com.cse471.project.service.FAQService.FAQService;
import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.security.PermitAll;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@Route(value = "article", layout = MainLayout.class)
@PermitAll

public class ArticleView extends VerticalLayout {

    private final AuthenticatedUser authenticatedUser;
    public ArticleView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        var user = authenticatedUser.get().orElseThrow();
        if (user.getRoles().contains(Role.ADMIN)) {
            System.out.println("This user is an Admin");
        }
        add(new H3("Welcome to Article Page"));
    }
}
