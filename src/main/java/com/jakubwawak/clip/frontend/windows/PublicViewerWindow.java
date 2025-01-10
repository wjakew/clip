/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.clip.frontend.windows;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.jakubwawak.clip.entity.Clip;
import com.jakubwawak.clip.frontend.components.PublicClipViewer;
import com.vaadin.flow.component.button.Button;

/**
 * Window for logging user to the app
 */
public class PublicViewerWindow {

    // variables for setting x and y of window
    public String width = "85%";
    public String height = "85%";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;
    
    PublicClipViewer publicClipViewer;
    Button closeButton;

    Clip clip;

    /**
     * Constructor
     */
    public PublicViewerWindow(Clip clip){
        this.clip = clip;
        
        main_dialog = new Dialog();
        main_dialog.addClassName("dialog");

        main_layout = new VerticalLayout();
        prepare_dialog();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        // set components
        publicClipViewer = new PublicClipViewer(clip);

        closeButton = new Button("Close");
        closeButton.addClassName("landing-page-button-small");
        closeButton.addClickListener(event -> {
            main_dialog.close();
        });
    }

    /**
     * Function for preparing layout
     */
    void prepare_dialog(){
        prepare_components();

        // set layout
        main_layout.add(publicClipViewer);
        main_layout.add(closeButton);

        main_layout.setSizeFull();
        main_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        main_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        main_layout.getStyle().set("text-align", "center");

        main_layout.getStyle().set("border-radius","25px");
        main_layout.getStyle().set("background-color",backgroundStyle);
        main_layout.getStyle().set("--lumo-font-family","Monospace");
        main_dialog.add(main_layout);
        main_dialog.setWidth(width);main_dialog.setHeight(height);
    }
}