/**
 * @author Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.clip.frontend.components;

import com.jakubwawak.clip.entity.Clip;
import com.jakubwawak.clip.maintanance.LibraryContentEngine;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.component.Component;

/**
 * LibraryComponent class for the Clip application
 */
public class LibraryComponent extends VerticalLayout {

    LibraryContentEngine libraryContentEngine;

    VirtualList<Clip> virtualList;

    Button beforePage, nextPage;

    /**
     * Constructor for the LibraryComponent class
     */
    public LibraryComponent() {
        addClassName("library-component");

        libraryContentEngine = new LibraryContentEngine();
        libraryContentEngine.loadClipCards(0);


        virtualList = new VirtualList<>();
        virtualList.setSizeFull();

        virtualList.setItems(libraryContentEngine.currentPage);

        virtualList.setRenderer(clipCardRenderer);

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.add(virtualList);
        mainLayout.setSizeFull();
        mainLayout.setAlignItems(Alignment.CENTER);
        mainLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        
        
        
        add(mainLayout);

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    /**
     * Renderer for the clip cards
     */
    private ComponentRenderer<Component, Clip> clipCardRenderer = new ComponentRenderer<>(
        clip -> {
            ClipCard clipCard = new ClipCard(clip);
            return clipCard;
        }
    );

}
