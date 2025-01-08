/**
 * @author Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.clip.frontend.components;

import com.jakubwawak.clip.maintanance.LibraryContentEngine;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;

/**
 * LibraryComponent class for the Clip application
 */
public class LibraryComponent extends VerticalLayout {

    LibraryContentEngine libraryContentEngine;

    VirtualList<ClipCard> virtualList;

    /**
     * Constructor for the LibraryComponent class
     */
    public LibraryComponent() {
        addClassName("library-component");

        libraryContentEngine = new LibraryContentEngine();
        virtualList = new VirtualList<>();
        virtualList.setSizeFull();
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }



}
