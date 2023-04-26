package com.cse471.project.views.personalnotes;

import com.cse471.project.entity.User;
import com.cse471.project.security.AuthenticatedUser;
import com.cse471.project.service.UserService.UserService;
import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.DescriptionList.Description;
import com.vaadin.flow.component.html.DescriptionList.Term;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.richtexteditor.RichTextEditorVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Accessibility;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.Border;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderColor;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Flex;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Height;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@PageTitle("Personal Notes")
@Route(value = "personal-notes", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class PersonalNotesView extends Main {
    private final UserService userService;
    private Description lastModifiedDescription;
    private Description statusDescription;
    private final UI curUI;

    public PersonalNotesView(AuthenticatedUser mayBeUser, UserService userService) throws IllegalAccessException {
        this.userService = userService;
        this.curUI = UI.getCurrent();


        addClassNames(Display.FLEX, Flex.GROW, Height.FULL);

        // Editor
        RichTextEditor editor = new RichTextEditor();
        editor.addClassNames(Border.RIGHT, BorderColor.CONTRAST_10, Flex.GROW);
        editor.addThemeVariants(RichTextEditorVariant.LUMO_NO_BORDER);
        if (mayBeUser.get().isEmpty()) throw new IllegalAccessException("User can't be empty");
        var curUser = mayBeUser.get().get();
        if (mayBeUser.get().isPresent() &&
                (mayBeUser.get().get().getPersonalNotesMadeUsingEditorInHTML() == null ||
                        mayBeUser.get().get().getPersonalNotesMadeUsingEditorInHTML().equals(""))) {
            editor.asHtml().setValue(
                    """     
                            <p><br></p>
                            <h2 style="text-align: center">This is where we can take notes. They are <em><u>Safe</u></em> with us :)</h2>
                            <p><br></p>
                            """);
        } else {
            editor.asHtml().setValue(mayBeUser.get().get().getPersonalNotesMadeUsingEditorInHTML());
        }
        editor.setValueChangeMode(ValueChangeMode.EAGER);
        editor.addValueChangeListener(event -> {
            System.out.println("I am here in the value changed listener");
            curUI.access(() -> {
                System.out.println("I have modified the ui 1st time");
                lastModifiedDescription.removeAll();
                lastModifiedDescription.add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                statusDescription.removeAll();
                statusDescription.add("Saving ");
                statusDescription.add(new Icon(VaadinIcon.COG));
            });
            System.out.println(editor.getHtmlValue());
            // Schedule the task to be executed after 2 seconds
            persistChange(curUser, editor);
            curUI.access(() -> {
                statusDescription.removeAll();
                statusDescription.add("Saved ");
                statusDescription.add(new Icon(VaadinIcon.CHECK_CIRCLE));
            });
        });
        // Sidebar
        add(editor, createSidebar(curUser));
    }

    private void persistChange(User curUser, RichTextEditor editor) {
        userService.saveUserCreatedNotes(editor.getHtmlValue(), curUser);
    }

    private Section createSidebar(User user) {
        Section sidebar = new Section();
        sidebar.addClassNames(Background.CONTRAST_10, BoxSizing.BORDER, Display.FLEX, FlexDirection.COLUMN,
                Flex.SHRINK_NONE, Overflow.AUTO, Padding.LARGE);
        sidebar.setWidth("256px");

        H2 title = new H2("Project details");
        title.addClassName(Accessibility.SCREEN_READER_ONLY);

        DescriptionList dl = new DescriptionList();
        dl.addClassNames(Display.FLEX, FlexDirection.COLUMN, Gap.LARGE, Margin.Bottom.SMALL, Margin.Top.NONE,
                FontSize.SMALL);

        dl.add(createItem("Owner", user.getName()), createItem("Created",
                        user.getAccountCreated()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))),
                createLastModifiedItem("Last modified",
                        user.getLastModifiedPersonalNotes()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))),
                createStatusItem("Status", "Saved"));

        // Add it all together
        sidebar.add(title, dl);
        return sidebar;
    }

    private Div createLastModifiedItem(String label, String value, String... themeNames) {
        Div curDiv = new Div();
        Term term = new Term(label);
        term.addClassNames(FontWeight.MEDIUM, TextColor.SECONDARY);

        curDiv.add(term);

        lastModifiedDescription = new Description(value);
        lastModifiedDescription.addClassName(Margin.Left.NONE);
        for (String themeName : themeNames) {
            lastModifiedDescription.getElement().getThemeList().add(themeName);
        }
        curDiv.add(lastModifiedDescription);
        return curDiv;
    }

    private Div createStatusItem(String label, String value, String... themeNames) {
        Div curDiv = new Div();
        Term term = new Term(label);
        term.addClassNames(FontWeight.MEDIUM, TextColor.SECONDARY);

        curDiv.add(term);

        statusDescription = new Description(value);
        statusDescription.addClassName(Margin.Left.NONE);
        for (String themeName : themeNames) {
            statusDescription.getElement().getThemeList().add(themeName);
        }
        curDiv.add(statusDescription);
        return curDiv;
    }

    private Div createItem(String label, String value) {
        return new Div(createTerm(label), createDescription(value));
    }

    private Div createBadgeItem(String label, String value) {
        return new Div(createTerm(label), createDescription(value, "badge"));
    }

    private Term createTerm(String label) {
        Term term = new Term(label);
        term.addClassNames(FontWeight.MEDIUM, TextColor.SECONDARY);
        return term;
    }

    private Description createDescription(String value, String... themeNames) {
        Description desc = new Description(value);
        desc.addClassName(Margin.Left.NONE);
        for (String themeName : themeNames) {
            desc.getElement().getThemeList().add(themeName);
        }
        return desc;
    }
}
