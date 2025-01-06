/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.clip.frontend.windows;

import java.sql.Timestamp;

import com.jakubwawak.clip.ClipApplication;
import com.jakubwawak.clip.database.DatabaseClip;
import com.jakubwawak.clip.entity.Clip;
import com.jakubwawak.clip.frontend.components.ClipEditor;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Window for logging user to the app
 */
public class PublishWindow {

    // variables for setting x and y of window
    public String width = "";
    public String height = "";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;

    private Clip clip;
    private String userSessionToken;
    private boolean proMode;
    private boolean isNewClip;

    private TextField clipTitle;
    private Checkbox clipPrivate;

    private Checkbox passwordProtected;
    private PasswordField passwordField;

    private Button publishButton;

    private ClipEditor clipEditor;

    /**
     * Constructor
     */
    public PublishWindow(Clip clip, String userSessionToken, boolean proMode, boolean isNewClip,
            ClipEditor clipEditor) {
        this.clip = clip;
        this.userSessionToken = userSessionToken;
        this.proMode = proMode;
        this.isNewClip = isNewClip;
        this.clipEditor = clipEditor;
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
        clipTitle = new TextField();
        clipTitle.setPlaceholder("title");
        clipTitle.setPrefixComponent(VaadinIcon.FLAG.create());
        clipTitle.setWidth("100%");
        clipTitle.addClassName("clip-editor-title");
        clipTitle.setValue(clip.getClipTitle());

        clipPrivate = new Checkbox();
        clipPrivate.setLabel("Make private");
        clipPrivate.setValue(clip.isClipPrivate());
        clipPrivate.setTooltipText("If selected, the clip will not be visible in the library");

        passwordProtected = new Checkbox();
        passwordProtected.setLabel("Password protected");
        passwordProtected.setValue(false);
        passwordProtected.setTooltipText("If selected, the clip will be password protected");

        passwordProtected.addValueChangeListener(event -> {
            passwordField.setVisible(passwordProtected.getValue());
        });

        passwordField = new PasswordField();
        passwordField.setPlaceholder("password");
        passwordField.addClassName("clip-editor-title");
        passwordField.setWidth("100%");
        passwordField.setValue(clip.getClipPassword());
        passwordField.setVisible(false);
        passwordField.addClassName("clip-editor-password");

        publishButton = new Button("Publish", this::publishClip);
        publishButton.addClassName("landing-page-button-small");

        if (!proMode) {
            if (clip.getClipRaw().length() > 250000) {
                publishButton.setEnabled(false);
                publishButton.setText("Upgrade to pro to publish");
            }
        }
    }

    /**
     * Function for preparing layout
     */
    void prepare_dialog() {
        prepare_components();
        // set layout

        if (isNewClip) {
            main_layout.add(new H4("Publish"));
            publishButton.setText("Publish");
        } else {
            main_layout.add(new H4("Update"));
            publishButton.setText("Update");
        }
        main_layout.add(clipTitle);
        main_layout.add(passwordProtected);
        main_layout.add(passwordField);

        HorizontalLayout buttons_layout = new HorizontalLayout();
        buttons_layout.setWidth("100%");
        buttons_layout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttons_layout.setAlignItems(Alignment.CENTER);

        FlexLayout left_layout = new FlexLayout();
        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(JustifyContentMode.START);
        left_layout.setAlignItems(Alignment.CENTER);
        left_layout.setWidth("80%");

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(JustifyContentMode.END);
        right_layout.setAlignItems(Alignment.CENTER);
        right_layout.setWidth("80%");

        left_layout.add(clipPrivate);
        right_layout.add(publishButton);

        buttons_layout.add(left_layout, right_layout);

        main_layout.add(buttons_layout);

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
     * Function for publishing the clip
     */
    private void publishClip(ClickEvent<Button> event) {
        DatabaseClip databaseClip = new DatabaseClip();
        if (clipTitle.getValue().isEmpty()) {
            ClipApplication.showNotification("Please fill all the fields");
            return;
        }

        if (passwordProtected.getValue() && passwordField.getValue().isEmpty()) {
            ClipApplication.showNotification("Please fill all the fields");
            return;

        } else {
            clip.setClipPassword(passwordField.getValue());
            clip.setClipUpdatedAt(new Timestamp(System.currentTimeMillis()));
            clip.setClipPrivate(clipPrivate.getValue());

            if (!userSessionToken.equals("")) {
                // TODO: got user session token and update user data
            } else {
                clip.setClipEditorPassword();
            }

            if (isNewClip) {
                int ans = databaseClip.createClip(clip);
                if (ans == 1) {
                    ClipApplication.showNotification("Clip created successfully");
                    PublishSummaryWindow publishSummaryWindow = new PublishSummaryWindow(clip);

                    main_layout.add(publishSummaryWindow.main_dialog);
                    publishSummaryWindow.main_dialog.open();

                    main_dialog.close();
                    clipEditor.clear();
                } else {
                    ClipApplication.showNotification("Failed to create clip");
                }
            } else {
                int ans = databaseClip.updateClip(clip);
                if (ans == 1) {
                    ClipApplication.showNotification("Clip updated successfully");
                    main_dialog.close();
                    clipEditor.clear();
                } else {
                    ClipApplication.showNotification("Failed to update clip");
                }
            }
        }
    }
}
