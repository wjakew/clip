/**
 * @author Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.clip.frontend.components;

import com.jakubwawak.clip.frontend.windows.LoginWindow;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinSession;

public class LoginStateComponent extends HorizontalLayout {

    Button loginActionButton;

    Button myAccountButton;

    String currentSessionToken;

    /**
     * Constructor for the LoginStateComponent class
     */
    public LoginStateComponent() {

        if (VaadinSession.getCurrent().getAttribute("clipAuthToken") == null) {
            // user is not logged, show normal login button
            prepareComponents();
            add(loginActionButton);
        } else {
            currentSessionToken = (String) VaadinSession.getCurrent().getAttribute("clipAuthToken");
            prepareComponents();
            // TODO: add actions and customizations when session token is present
        }
    }

    /**
     * Function for preparing the components
     */
    void prepareComponents() {
        loginActionButton = new Button("login", VaadinIcon.LOCK.create(), this::loginAction);
        loginActionButton.addClassName("landing-page-button-small");

        myAccountButton = new Button("my account", VaadinIcon.USER.create());
        myAccountButton.addClassName("landing-page-button-small");

        // TODO: add actions and customizations when session token is present
    }

    /**
     * Function for handling the login action
     * 
     * @param event
     */
    private void loginAction(ClickEvent<Button> event) {
        if (currentSessionToken == null) {
            // show login window
            LoginWindow loginWindow = new LoginWindow();
            add(loginWindow.main_dialog);
            loginWindow.main_dialog.open();
        }
    }
}
