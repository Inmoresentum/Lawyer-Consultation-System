package com.cse471.project.views.allusers;

import com.cse471.project.entity.User;
import com.cse471.project.security.AuthenticatedUser;
import com.cse471.project.service.UserService.UserService;
import com.cse471.project.views.MainLayout;
import com.vaadin.collaborationengine.CollaborationAvatarGroup;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;

import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("All Users")
@Route(value = "admin-users-view/:samplePersonID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class AllUsersView extends Div implements BeforeEnterObserver {

    private final String SAMPLEPERSON_ID = "samplePersonID";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "admin-users-view/%s/edit";

    private final Grid<User> grid = new Grid<>(User.class, false);

    CollaborationAvatarGroup avatarGroup;

    private TextField name;
    private TextField username;
    private TextField email;
    private TextField phoneNumber;
    private DatePicker dateOfBirth;
    private Checkbox accountVerificationStatus;
    private Checkbox deactivateAccount;
    private DateTimePicker accountCreated;
    private TextField address;
    private TextArea aboutYourSelf;

    private final Button cancel = new Button("Cancel Changes");
    private final Button save = new Button("Save Changes");

    private final CollaborationBinder<User> binder;

    private User user;

    private final UserService userService;

    public AllUsersView(AuthenticatedUser authenticatedUser, UserService userService) {
        this.userService = userService;
        addClassNames("all-users-view");
        var curLoggedInUser = authenticatedUser.get();
        if (curLoggedInUser.isEmpty())
            throw new RuntimeException("cur logged in user can't be empty");
        // UserInfo is used by Collaboration Engine and is used to share details
        // of users to each other to able collaboration. Replace this with
        // information about the actual user that is logged, providing a user
        // identifier, and the user's real name. You can also provide the users
        // avatar by passing an url to the image as a third parameter, or by
        // configuring an `ImageProvider` to `avatarGroup`.
        UserInfo userInfo = new UserInfo(curLoggedInUser.get().getId().toString()
                , curLoggedInUser.get().getUsername());

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        avatarGroup = new CollaborationAvatarGroup(userInfo, null);
        avatarGroup.getStyle().set("visibility", "hidden");

        if (curLoggedInUser.get().getProfilePicture() != null) {
            avatarGroup.setImageProvider(info -> {
                StreamResource streamResource = new StreamResource(
                        "avatar_" + info.getId(), () -> {
                    var userEntity = userService
                            .findByUserId(info.getId());
                    if (userEntity.isEmpty())
                        throw new IllegalStateException("user can't be null");
                    byte[] imageBytes = userEntity.get().getProfilePicture();
                    assert imageBytes != null;
                    return new ByteArrayInputStream(imageBytes);
                });
                streamResource.setContentType("image/png");
                return streamResource;
            });
        }

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("name").setAutoWidth(true);
        grid.addColumn("username").setAutoWidth(true);
        grid.addColumn("email").setAutoWidth(true);
        grid.addColumn("phoneNumber").setAutoWidth(true);
        grid.addColumn("dateOfBirth").setAutoWidth(true);
        LitRenderer<User> verificationRenderer = LitRenderer.<User>of(
                        "<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>")
                .withProperty("icon", isAccountActive -> isAccountActive.isAccountVerified() ? "check" : "minus").withProperty("color",
                        isAccountActive -> isAccountActive.isAccountVerified()
                                ? "var(--lumo-primary-text-color)"
                                : "var(--lumo-disabled-text-color)");

        grid.addColumn(verificationRenderer).setHeader("Account Verified").setAutoWidth(true);

        grid.setItems(query -> userService.list(
                        PageRequest.of(query.getPage(), query.getPageSize(),
                                VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(AllUsersView.class);
            }
        });

        // Configure Form
        binder = new CollaborationBinder<>(User.class, userInfo);

        // Bind fields. This is where you'd define e.g. validation rules

        binder.bindInstanceFields(this);
        binder.bind(accountVerificationStatus, "accountVerified");
        binder.bind(deactivateAccount, "deactivatedByAdmin");
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e ->
                handleUserUpdateRequest(userService));
    }

    private void handleUserUpdateRequest(UserService userService) {
        try {
            if (this.user == null) {
                this.user = new User();
            }
            if (!isFormValid()) {
                Notification notification = Notification.show(
                        "The form is not valid. Please try again");
                notification.setPosition(Position.BOTTOM_END);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            binder.writeBean(this.user);
            userService.update(this.user);
            clearForm();
            refreshGrid();
            Notification.show("Data updated");
            UI.getCurrent().navigate(AllUsersView.class);
        } catch (ObjectOptimisticLockingFailureException exception) {
            Notification notification = Notification.show(
                    "Error updating the data. Somebody else has updated" +
                            " the record while you were making changes.");
            notification.setPosition(Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        } catch (ValidationException validationException) {
            Notification.show("Failed to update the data. Check again that all values are valid");
        }
    }

    private boolean isFormValid() {
        if (name.isEmpty()) {
            name.setErrorMessage("This field can't be empty");
            name.setInvalid(true);
            return false;
        } else if (name.isInvalid()) {
            name.setErrorMessage("Is This a valid name");
            name.setInvalid(true);
            return false;
        } else if (email.isEmpty()) {
            email.setErrorMessage("This is is required");
            email.setInvalid(true);
            return false;
        } else if (email.isInvalid()) {
            email.setErrorMessage("This is not a valid email address");
            email.setInvalid(true);
            return false;
        } else if (phoneNumber.isEmpty()) {
            phoneNumber.setErrorMessage("This field is required!");
            phoneNumber.setInvalid(true);
            return false;
        } else if (phoneNumber.isInvalid()) {
            phoneNumber.setErrorMessage("This is not a valid phone number!");
            phoneNumber.setInvalid(true);
            return false;
        } else if (dateOfBirth.isEmpty()) {
            dateOfBirth.setErrorMessage("This field is required!");
            dateOfBirth.setInvalid(true);
            return false;
        } else if (address.isEmpty()) {
            address.setErrorMessage("This field is required!");
            address.setInvalid(true);
        }
        return true;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> samplePersonId = event.getRouteParameters().get(SAMPLEPERSON_ID).map(Long::parseLong);
        if (samplePersonId.isPresent()) {
            Optional<User> samplePersonFromBackend = userService.get(samplePersonId.get());
            if (samplePersonFromBackend.isPresent()) {
                populateForm(samplePersonFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested samplePerson was not found, ID = %d", samplePersonId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(AllUsersView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();

        name = new TextField("Name");
        name.setHelperText("This is the Full Name of the user");
        name.setRequired(true);
        name.setTooltipText("Click to edit and update information");
        name.setPrefixComponent(new Icon(VaadinIcon.USERS));

        username = new TextField("Username");
        username.setEnabled(false);
        username.setRequired(true);
        username.setHelperText("This is the username and it can't be changed");
        username.setTooltipText("This field can't be changed");
        username.setPrefixComponent(new Icon(VaadinIcon.USER));

        email = new TextField("Email");
        email.setPrefixComponent(new Icon(VaadinIcon.MAILBOX));
        email.setHelperText("This the user email address and it can be changed");
        email.setValueChangeMode(ValueChangeMode.EAGER);
        email.addValueChangeListener(event -> isEmailInUse(event.getValue()));

        phoneNumber = new TextField("Phone");
        phoneNumber.setRequired(true);
        phoneNumber.setPrefixComponent(new Icon(VaadinIcon.PHONE_LANDLINE));
        phoneNumber.setHelperText("This is the user phone number");
        phoneNumber.setValueChangeMode(ValueChangeMode.EAGER);
        phoneNumber.addValueChangeListener(event -> isValidPhoneNumber(event.getValue()));

        dateOfBirth = new DatePicker("Date Of Birth");

        accountVerificationStatus = new Checkbox("Account Verified?");
        accountVerificationStatus.setEnabled(false);
        accountVerificationStatus.setTooltipText("Indicate verification status");

        deactivateAccount = new Checkbox("Deactivate Account?");
        accountCreated = new DateTimePicker("Time of account creation");
        accountCreated.setEnabled(false);

        address = new TextField("Address");
        address.setRequired(true);
        address.addValueChangeListener(event -> checkIfEmpty(event.getValue()));
        aboutYourSelf = new TextArea("User bio");
        formLayout.add(name, username, email, phoneNumber,
                dateOfBirth, accountVerificationStatus,
                accountCreated, deactivateAccount,
                address, aboutYourSelf);
        editorDiv.add(avatarGroup, formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void checkIfEmpty(String userAddress) {
        if (userAddress.equals("")) {
            address.setErrorMessage("This field can't be empty");
            address.setInvalid(true);
            return;
        }
        address.setInvalid(false);
    }

    private void isValidPhoneNumber(String value) {
        final String phoneNumberRegex = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s./0-9]*$";
        if (value.equals("")) {
            phoneNumber.setErrorMessage("This field can't be empty");
            phoneNumber.setInvalid(true);
            System.out.println("I am here");
        } else if (!value.matches(phoneNumberRegex)) {
            phoneNumber.setErrorMessage("This is not a valid phone number");
            phoneNumber.setInvalid(true);
        } else {
            phoneNumber.setInvalid(false);
        }
    }

    private void isEmailInUse(String emailAddress) {
        final String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,63}$";
        if (userService.findUserByEmail(emailAddress)) {
            email.setErrorMessage("This email address is already in use");
            email.setInvalid(true);
            return;
        }
        if (email.getValue().equals("")) {
            email.setErrorMessage("Email address can't be empty!");
            email.setInvalid(true);
            return;
        }
        if (!emailAddress.matches(emailRegex)) {
            email.setErrorMessage("Please enter a valid email address");
            return;
        }
        email.setInvalid(false);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(User value) {
        this.user = value;
        String topic = null;
        if (this.user != null && this.user.getId() != null) {
            topic = "users-list/" + this.user.getId();
            avatarGroup.getStyle().set("visibility", "visible");
        } else {
            avatarGroup.getStyle().set("visibility", "hidden");
        }
        binder.setTopic(topic, () -> this.user);
        avatarGroup.setTopic(topic);
    }
}
