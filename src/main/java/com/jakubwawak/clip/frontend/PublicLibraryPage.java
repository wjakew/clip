/**
 * @author Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.clip.frontend;

import com.jakubwawak.clip.ClipApplication;
import com.jakubwawak.clip.frontend.components.ClipEditor;
import com.jakubwawak.clip.frontend.components.LibraryComponent;
import com.jakubwawak.clip.frontend.windows.EditorWindow;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Landing Page for the application
 */
@Route(value = "/library")
@PageTitle("clip library")
public class PublicLibraryPage extends VerticalLayout {

    Button createClipButton;
    Button goBackButton;

    HorizontalLayout headerLandingBar;

    LibraryComponent libraryComponent;

    /**
     * Constructor for the Landing Page
     */
    public PublicLibraryPage() {
        addClassName("landing-page");

        createButtons();
        createHeaderBar();

        VerticalLayout landing_page_content = new VerticalLayout();
        landing_page_content.setSizeFull();
        landing_page_content.setAlignItems(Alignment.CENTER);
        landing_page_content.setJustifyContentMode(JustifyContentMode.CENTER);

        libraryComponent = new LibraryComponent();

        landing_page_content.add(libraryComponent);

        add(headerLandingBar);
        add(landing_page_content);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    /**
     * Creates the buttons for the landing page
     */
    void createButtons() {
        createClipButton = new Button("create clip", VaadinIcon.PLUS.create(), this::createClipAction);
        createClipButton.addClassName("landing-page-button-small");
        goBackButton = new Button("go back", VaadinIcon.ARROW_LEFT.create(), this::goBackAction);
        goBackButton.addClassName("landing-page-button-small");
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
        center_layout.add(new H6("clip library"));

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(JustifyContentMode.END);
        right_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        right_layout.setWidth("80%");

        right_layout.add(goBackButton,createClipButton);

        headerLandingBar.add(left_layout, center_layout, right_layout);

        headerLandingBar.setWidthFull();
        headerLandingBar.addClassName("header-landing-bar");
    }

    /**
     * Action for creating a clip
     */
    private void createClipAction(ClickEvent<Button> event) {
        EditorWindow editorWindow = new EditorWindow(null,null,false);
        add(editorWindow.main_dialog);
        editorWindow.main_dialog.open();
    }

    private void goBackAction(ClickEvent<Button> event) {
        getUI().get().navigate(LandingPage.class);
    }
}
