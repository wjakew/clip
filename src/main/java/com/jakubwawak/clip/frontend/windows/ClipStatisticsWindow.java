/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.clip.frontend.windows;

import com.jakubwawak.clip.entity.Clip;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Window for logging user to the app
 */
public class ClipStatisticsWindow {

    // variables for setting x and y of window
    public String width = "";
    public String height = "";
    public String backgroundStyle = "";

    // main login components
    public Dialog main_dialog;
    VerticalLayout main_layout;

    Clip clip;

    /**
     * Constructor
     */
    public ClipStatisticsWindow(Clip clip){
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
        main_layout.add(new H1("Clip Statistics"));
        main_layout.add(new H2("Clip ID: "+clip.getId()));
        main_layout.add(new H2("Clip URL: "+clip.getClipUrl()));
        main_layout.add(new H2("Clip Editor Password: "+clip.getClipEditorPassword()));
        main_layout.add(new H2("Clip Created At: "+clip.getClipCreatedAt()));
        main_layout.add(new H2("Clip Last Updated At: "+clip.getClipUpdatedAt()));
        main_layout.add(new H2("Clip Word Count: "+clip.getClipWordCount()));
        main_layout.add(new H2("Clip Signs Count: "+clip.getClipRaw().length()));
        main_layout.add(new H2("Clip Created As: "+clip.getUserId()));
    }

    /**
     * Function for preparing layout
     */
    void prepare_dialog(){
        prepare_components();
        // set layout

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