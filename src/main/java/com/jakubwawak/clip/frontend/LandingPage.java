/**
 * @author Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.clip.frontend;

import com.jakubwawak.clip.ClipApplication;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

/**
 * Landing Page for the application
 */
@Route(value = "/welcome")
@RouteAlias(value = "/")
@PageTitle("Welcome to Clip")
public class LandingPage extends VerticalLayout{

    Button loginButton;
    Button createClipButton;
    Button goToClipLibrary;

    HorizontalLayout headerLandingBar;

    /**
     * Constructor for the Landing Page
     */
    public LandingPage(){
        addClassName("landing-page");

        createButtons();
        createHeaderBar();

        VerticalLayout landing_page_content = new VerticalLayout();
        landing_page_content.setSizeFull();
        landing_page_content.setAlignItems(Alignment.CENTER);
        landing_page_content.setJustifyContentMode(JustifyContentMode.CENTER);

        landing_page_content.add(new H1("clip."));
        landing_page_content.add(new HorizontalLayout(createClipButton));


        add(headerLandingBar);
        add(landing_page_content);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    /**
     * Creates the buttons for the landing page
     */
    void createButtons(){
        loginButton = new Button("login",VaadinIcon.USER.create());
        loginButton.addClassName("landing-page-button-small");    
        createClipButton = new Button("",VaadinIcon.PLUS.create());
        createClipButton.addClassName("landing-page-button-big");
        goToClipLibrary = new Button("library",VaadinIcon.BOOK.create());
        goToClipLibrary.addClassName("landing-page-button-small-transparent");
    }

    /**
     * Creates the header bar for the landing page
     */
    void createHeaderBar(){
        headerLandingBar = new HorizontalLayout();

        FlexLayout left_layout = new FlexLayout();
        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(JustifyContentMode.START);
        left_layout.setAlignItems(Alignment.CENTER);
        left_layout.setWidth("80%");
        left_layout.add();

        left_layout.add(new H4(ClipApplication.database.getServerName()));

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(JustifyContentMode.END);
        right_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        right_layout.setWidth("80%");
        
        // Apply the spacing class to the buttons
        goToClipLibrary.addClassName("spacing");
        loginButton.addClassName("spacing");

        right_layout.add(goToClipLibrary, loginButton);
        
        headerLandingBar.add(left_layout, right_layout);

        headerLandingBar.setWidthFull();
        headerLandingBar.addClassName("header-landing-bar");
    }
}
