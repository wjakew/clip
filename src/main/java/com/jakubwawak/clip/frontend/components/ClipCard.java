/**
 * @author Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.clip.frontend.components;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.jakubwawak.clip.entity.Clip;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.jakubwawak.clip.frontend.windows.PublicViewerWindow;
/**
 * ClipCard class for the Clip application
 */
public class ClipCard extends VerticalLayout {

    Clip clip;

    H6 timestamp, type;

    H4 title;

    Icon icon;

    H6 owner;

    /**
     * Constructor for the ClipCard class
     */
    public ClipCard(Clip clip) {
        addClassName("clip-card");
        this.clip = clip;

        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);

        prepareClipCard();
    }

    /**
     * Function for preparing the clip card
     */
    void prepareClipCard() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = LocalDateTime.ofEpochSecond(clip.getClipCreatedAt().getTime() / 1000, 0, ZoneOffset.UTC)
                .format(formatter);

        this.timestamp = new H6(formattedTimestamp);

        this.type = new H6(clip.getClipExtension());
        this.title = new H4(clip.getClipTitle());

        if (!clip.getClipPassword().equals("")) {
            this.icon = new Icon("fas", "lock");
        } else {
            this.icon = new Icon("fas", "unlock");
        }
        if (clip.getUserId() == 0) {
            this.owner = new H6("guest");
        } else {
            this.owner = new H6("logged user");
        }

        HorizontalLayout upperLayout = new HorizontalLayout();

        FlexLayout left_layout = new FlexLayout();
        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(JustifyContentMode.START);
        left_layout.setAlignItems(Alignment.CENTER);
        left_layout.setWidth("80%");
        left_layout.add();
        left_layout.add(this.timestamp);

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(JustifyContentMode.END);
        right_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        right_layout.setWidth("80%");

        right_layout.add(this.type);

        upperLayout.add(left_layout, right_layout);
        upperLayout.setWidthFull();
        upperLayout.setAlignItems(Alignment.CENTER);

        HorizontalLayout bottomLayout = new HorizontalLayout();

        FlexLayout left_layout_btm = new FlexLayout();
        left_layout_btm.setSizeFull();
        left_layout_btm.setJustifyContentMode(JustifyContentMode.START);
        left_layout_btm.setAlignItems(Alignment.CENTER);
        left_layout_btm.setWidth("80%");
        left_layout_btm.add();

        left_layout_btm.add(this.icon);

        FlexLayout right_layout_btm = new FlexLayout();
        right_layout_btm.setSizeFull();
        right_layout_btm.setJustifyContentMode(JustifyContentMode.END);
        right_layout_btm.setAlignItems(FlexComponent.Alignment.CENTER);
        right_layout_btm.setWidth("80%");

        right_layout_btm.add(this.owner);

        bottomLayout.add(left_layout_btm, right_layout_btm);
        bottomLayout.setWidthFull();
        bottomLayout.setAlignItems(Alignment.CENTER);

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setAlignItems(Alignment.CENTER);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        layout.add(title);

        this.add(upperLayout);
        this.add(layout);
        this.add(bottomLayout);


        addClickListener(event -> {
            PublicViewerWindow publicViewerWindow = new PublicViewerWindow(clip);
            add(publicViewerWindow.main_dialog);
            publicViewerWindow.main_dialog.open();
        });
    }

}
