/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.clip.frontend.windows;

import com.jakubwawak.clip.ClipApplication;
import com.jakubwawak.clip.entity.Clip;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;

/**
 * Window for logging user to the app
 */
public class EditorPasswordWindow {

    // variables for setting x and y of window
    public String width = "";
    public String height = "";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;

    Clip clip;

    PasswordField passwordField;

    Button submitButton;

    /**
     * Constructor
     */
    public EditorPasswordWindow(Clip clip) {
        this.clip = clip;
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
        passwordField = new PasswordField();
        passwordField.setPlaceholder("password");
        passwordField.addClassName("clip-editor-title");
        passwordField.setWidth("100%");

        submitButton = new Button("Unlock", VaadinIcon.KEY.create(), this::submitPassword);
        submitButton.addClassName("landing-page-button-small");
    }

    /**
     * Function for preparing layout
     */
    void prepare_dialog() {
        prepare_components();
        // set layout
        main_dialog.add(new HorizontalLayout(VaadinIcon.LOCK.create(), new H4("Editor Locked")));
        main_layout.add(passwordField);
        main_layout.add(submitButton);

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

    /**
     * Function for submitting the password
     */
    private void submitPassword(ClickEvent<Button> event) {
        if (passwordField.getValue().equals(clip.getClipEditorPassword())) {
            main_dialog.close();
            // open editor window
            EditorWindow editorWindow = new EditorWindow(clip, "", false);
            editorWindow.main_dialog.open();

        } else {
            ClipApplication.showNotification("Wrong password!");
        }
    }
}
