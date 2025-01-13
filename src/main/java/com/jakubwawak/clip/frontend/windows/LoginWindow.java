/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.clip.frontend.windows;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;

/**
 * Window for logging user to the app
 */
public class LoginWindow {

    // variables for setting x and y of window
    public String width = "60%";
    public String height = "60%";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;

    TextField usernameField;
    TextField passwordField;

    Button loginButton;
    Button registerButton;

    /**
     * Constructor
     */
    public LoginWindow() {

        main_dialog = new Dialog();
        main_dialog.addClassName("dialog");

        main_layout = new VerticalLayout();
        prepare_dialog();
    }

    /**
     * Function for preparing components
     */
    void prepare_components() {
        // set components
        usernameField = new TextField("Username");
        usernameField.addClassName("clip-editor-title");
        usernameField.setWidth("100%");

        passwordField = new TextField("Password");
        passwordField.addClassName("clip-editor-title");
        passwordField.setWidth("100%");

        loginButton = new Button("Login");
        loginButton.addClassName("landing-page-button-small");
        loginButton.setWidth("100%");

        registerButton = new Button("Register");
        registerButton.addClassName("landing-page-button-small-transparent");
        registerButton.setWidth("100%");
    }

    /**
     * Function for preparing layout
     */
    void prepare_dialog() {
        prepare_components();
        // set layout

        main_layout.add(new H6("Login"));
        main_layout.add(usernameField);
        main_layout.add(passwordField);
        main_layout.add(loginButton);
        main_layout.add(registerButton);

        main_layout.setSizeFull();
        main_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        main_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        main_layout.getStyle().set("text-align", "center");

        main_layout.getStyle().set("border-radius", "25px");
        main_layout.getStyle().set("background-color", backgroundStyle);
        main_layout.getStyle().set("--lumo-font-family", "Monospace");
        main_dialog.add(main_layout);
        main_dialog.setWidth(width);
        main_dialog.setHeight(height);
    }
}