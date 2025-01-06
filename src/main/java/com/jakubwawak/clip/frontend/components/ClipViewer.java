/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.clip.frontend.components;

import com.jakubwawak.clip.entity.Clip;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Component for viewing a clip
 */
public class ClipViewer extends VerticalLayout {

    Clip clip;

    public ClipViewer(Clip clip) {
        this.clip = clip;

        addClassName("clip-viewer");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}
