/**
 * @author Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.clip.frontend;

import com.jakubwawak.clip.ClipApplication;
import com.jakubwawak.clip.database.DatabaseClip;
import com.jakubwawak.clip.entity.Clip;
import com.jakubwawak.clip.frontend.components.PublicClipViewer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Landing Page for the application
 */
@Route(value = "/viewer")
@PageTitle("clip viewer")
public class PublicViewerPage extends VerticalLayout implements HasUrlParameter<String> {

    HorizontalLayout headerLandingBar;

    Button returnToLandingPage;
    Button libraryButton;
    Clip clip;

    /**
     * Constructor for the Landing Page
     */
    public PublicViewerPage() {
        addClassName("viewer-page");

        createButtons();
        createHeaderBar();

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    /**
     * Creates the buttons for the landing page
     */
    void createButtons() {
        returnToLandingPage = new Button("go home", VaadinIcon.ARROW_LEFT.create());
        returnToLandingPage.addClassName("landing-page-button-small-transparent");

        returnToLandingPage.addClickListener(event -> {
            UI.getCurrent().navigate(LandingPage.class);
        });

        libraryButton = new Button("library", VaadinIcon.BOOK.create());
        libraryButton.addClassName("landing-page-button-small-transparent");
    }

    /**
     * Creates the header bar for the landing page
     */
    void createHeaderBar() {
        headerLandingBar = new HorizontalLayout();

        FlexLayout left_layout = new FlexLayout();
        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(JustifyContentMode.START);
        left_layout.setAlignItems(Alignment.CENTER);
        left_layout.setWidth("80%");
        left_layout.add();
        left_layout.add(new H4(ClipApplication.database.getServerName()));

        FlexLayout center_layout = new FlexLayout();
        center_layout.setSizeFull();
        center_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        center_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        center_layout.add(new H6("clip viewer."));

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(JustifyContentMode.END);
        right_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        right_layout.setWidth("80%");

        right_layout.add(returnToLandingPage, libraryButton);

        returnToLandingPage.getStyle().set("margin-right", "10px");
        libraryButton.getStyle().set("margin-left", "10px");

        headerLandingBar.add(left_layout, center_layout, right_layout);

        headerLandingBar.setWidthFull();
        headerLandingBar.addClassName("header-landing-bar");
    }

    /**
     * Sets the parameter for the viewer page
     */
    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        DatabaseClip databaseClip = new DatabaseClip();

        VerticalLayout viewer_page_content = new VerticalLayout();
        viewer_page_content.setSizeFull();
        viewer_page_content.setAlignItems(Alignment.CENTER);
        viewer_page_content.setJustifyContentMode(JustifyContentMode.CENTER);

        add(headerLandingBar);
        if (parameter != null) {
            this.clip = databaseClip.getClipByUrl(parameter);
            if (this.clip != null) {
                viewer_page_content.add(new PublicClipViewer(this.clip));
            } else {
                viewer_page_content.add(new H4("Clip not found"));
            }
        } else {
            viewer_page_content.add(new H4("No url provided"));
        }

        add(viewer_page_content);
    }
}
