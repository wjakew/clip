/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.clip.frontend.windows;

import com.jakubwawak.clip.ClipApplication;
import com.jakubwawak.clip.entity.Clip;
import com.jakubwawak.clip.frontend.PublicViewerPage;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Window for logging user to the app
 */
@JsModule("./recipe/copytoclipboard.js")
public class PublishSummaryWindow {

    // variables for setting x and y of window
    public String width = "";
    public String height = "";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;

    Clip clip;

    Button copyLinkButton;
    Button goToViewerButton;

    Button closeButton;

    /**
     * Constructor
     */
    public PublishSummaryWindow(Clip clip) {
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
        copyLinkButton = new Button("Copy");
        copyLinkButton.addClassName("landing-page-button-small-transparent");
        copyLinkButton.addClickListener(this::copybutton_action);
        goToViewerButton = new Button("Viewer");
        goToViewerButton.addClassName("landing-page-button-small");

        goToViewerButton.addClickListener(e -> {
            UI.getCurrent().navigate(PublicViewerPage.class, clip.getClipUrl());
        });

        closeButton = new Button("Close");
        closeButton.addClassName("landing-page-button-small-transparent");

        closeButton.addClickListener(e -> {
            main_dialog.close();
        });
    }

    /**
     * Function for preparing layout
     */
    void prepare_dialog() {
        prepare_components();
        // set layout
        main_layout.add(new H4("Your Clip is live!"));
        main_layout.add(new H6("Your Password:"));

        H4 password = new H4(clip.getClipEditorPassword());
        password.getStyle().set("color", "orange");
        password.getStyle().set("font-weight", "bold");
        password.getStyle().set("font-size", "1.5em");

        main_layout.add(password);

        main_layout.add(new HorizontalLayout(copyLinkButton, goToViewerButton));

        main_layout.add(closeButton);

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
     * Function for copying to clipboard
     */
    private void copybutton_action(ClickEvent<Button> e){
        UI.getCurrent().getPage().executeJs("window.copyToClipboard($0)",
        "clip_url: "+clip.getClipUrl()+"\nclip_editpassword: "+clip.getClipEditorPassword());
        ClipApplication.showNotification("Rezultat skopiowany do schowka!");
    }
}