/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/

package com.jakubwawak.clip.frontend;

import com.jakubwawak.clip.ClipApplication;
import com.jakubwawak.clip.database.DatabaseUser;
import com.jakubwawak.clip.entity.User;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

/**
 * Main application web view
 */
@PageTitle("clip account creation")
@Route("register")
public class RegisterPage extends VerticalLayout {

    TextField emailField;
    TextField passwordField;
    TextField passwordConfirmationField;

    Button registerButton;

    Button goToLandingPageButton;

    /**
     * Constructor
     */
    public RegisterPage() {
        addClassName("viewer-page");
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents() {
        emailField = new TextField("email");
        emailField.setPlaceholder("enter your email");
        emailField.addClassName("clip-editor-title");
        emailField.setMaxLength(150);
        emailField.setWidth("100%");

        passwordField = new TextField("password");
        passwordField.setPlaceholder("enter your password");
        passwordField.addClassName("clip-editor-title");
        passwordField.setMaxLength(150);
        passwordField.setWidth("100%");
        passwordField.setMinLength(15);
        passwordField.setTooltipText("Password must be at least 15 characters long");

        passwordConfirmationField = new TextField("password confirmation");
        passwordConfirmationField.setPlaceholder("confirm your password");
        passwordConfirmationField.addClassName("clip-editor-title");
        passwordConfirmationField.setMaxLength(150);
        passwordConfirmationField.setWidth("100%");

        registerButton = new Button("register", VaadinIcon.ROCKET.create(), this::registerUser);
        registerButton.addClassName("landing-page-button-small");
        registerButton.setWidth("100%");

        goToLandingPageButton = new Button("go to landing page", VaadinIcon.HOME.create());
        goToLandingPageButton.addClassName("landing-page-button-small-transparent");
        goToLandingPageButton.setWidth("100%");

        goToLandingPageButton.addClickListener(event -> {
            UI.getCurrent().navigate(LandingPage.class);
        });
    }

    /**
     * Function for preparing layout data
     */
    void prepareLayout() {
        prepareComponents();

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();
        mainLayout.setMaxWidth("1000px");
        mainLayout.setMaxHeight("1000px");
        mainLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        mainLayout.getStyle().set("text-align", "center");

        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.setSizeFull();
        leftLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        leftLayout.getStyle().set("text-align", "center");

        StreamResource res = new StreamResource("logo.png", () -> {
            return RegisterPage.class.getClassLoader().getResourceAsStream("images/clip_icon.png");
        });

        Image logo = new Image(res, "logo");
        logo.setHeight("30rem");
        logo.setWidth("30rem");

        leftLayout.add(logo);
        leftLayout.add(goToLandingPageButton);

        VerticalLayout rightLayout = new VerticalLayout();
        rightLayout.setSizeFull();
        rightLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        rightLayout.getStyle().set("text-align", "center");
        rightLayout.getStyle().set("border", "1px solid orange");

        rightLayout.add(emailField, passwordField, passwordConfirmationField, registerButton);

        mainLayout.add(leftLayout, rightLayout);

        add(mainLayout);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    /**
     * Function for validating fields
     */
    private boolean validateFields() {
        if (emailField.getValue().isEmpty() || passwordField.getValue().isEmpty()
                || passwordConfirmationField.getValue().isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Function for checking password confirmation
     */
    private boolean checkPasswordConfirmation() {
        if (passwordField.getValue().equals(passwordConfirmationField.getValue())) {
            return true;
        }
        return false;
    }

    /**
     * Function for registering user
     */
    private void registerUser(ClickEvent<Button> event) {
        if (validateFields()) {
            if (checkPasswordConfirmation()) {
                User user = new User();
                user.setEmail(emailField.getValue());
                user.setPassword(passwordField.getValue());
                user.setUsername(emailField.getValue().split("@")[0]);
                user.setActive(true);
                user.setAdmin(false);
                user.setBlog_url("");
                user.setBlog_name("");
                user.setAccount_photo_url("");
                user.setBackground_color("black");
                user.setPrimary_color("white");

                DatabaseUser databaseUser = new DatabaseUser();
                if (databaseUser.isUserExists(emailField.getValue())) {
                    ClipApplication.showNotification("User already exists");
                } else {
                    int ans = databaseUser.createUser(user);
                    if (ans == 1) {
                        ClipApplication.showNotification("User registered successfully");
                        emailField.clear();
                        passwordField.clear();
                        passwordConfirmationField.clear();
                        UI.getCurrent().navigate(LandingPage.class);
                    } else {
                        ClipApplication.showNotification("User registration failed");
                    }
                }
            } else {
                ClipApplication.showNotification("Passwords do not match");
            }
        } else {
            ClipApplication.showNotification("Please fill all fields");
        }
    }

}