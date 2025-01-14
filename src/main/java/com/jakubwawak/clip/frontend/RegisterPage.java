/*
by Jakub Wawak
kubawawak@gmail.com
all rights reserved
*/

package com.jakubwawak.clip.frontend;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;


/**
 * Main application web view
 */
@PageTitle("clip account creation")
@Route("register")
public class RegisterPage extends VerticalLayout {


    TextField emailField;
    TextField passwordField;
    TextField passwordConfirmationField;

    Button registerButton;

    Button goToLandingPageButton;
    


    /**
     * Constructor
     */
    public RegisterPage(){
        addClassName("viewer-page");
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        emailField = new TextField("email");
        emailField.setPlaceholder("enter your email");
        emailField.addClassName("clip-editor-title");
        emailField.setMaxLength(150);
        emailField.setWidth("100%");

        passwordField = new TextField("password");
        passwordField.setPlaceholder("enter your password");
        passwordField.addClassName("clip-editor-title");
        passwordField.setMaxLength(150);
        passwordField.setWidth("100%");
        
        passwordConfirmationField = new TextField("password confirmation");
        passwordConfirmationField.setPlaceholder("confirm your password");
        passwordConfirmationField.addClassName("clip-editor-title");
        passwordConfirmationField.setMaxLength(150);
        passwordConfirmationField.setWidth("100%");

        registerButton = new Button("register",VaadinIcon.ROCKET.create());
        registerButton.addClassName("landing-page-button-small");
        registerButton.setWidth("100%");

        goToLandingPageButton = new Button("go to landing page",VaadinIcon.HOME.create());
        goToLandingPageButton.addClassName("landing-page-button-small-transparent");
        goToLandingPageButton.setWidth("100%");

        goToLandingPageButton.addClickListener(event -> {
            UI.getCurrent().navigate(LandingPage.class);
        });
    }

    /**
     * Function for preparing layout data
     */
    void prepareLayout(){
        prepareComponents();

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();mainLayout.setMaxWidth("1000px");mainLayout.setMaxHeight("1000px");
        mainLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        mainLayout.getStyle().set("text-align", "center");

        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.setSizeFull();
        leftLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        leftLayout.getStyle().set("text-align", "center");

        // TODO: add logo to left layout

        StreamResource res = new StreamResource("logo.png", () -> {
            return RegisterPage.class.getClassLoader().getResourceAsStream("images/clip_icon.png");
        });

        Image logo = new Image(res,"logo");
        logo.setHeight("30rem");
        logo.setWidth("30rem");

        leftLayout.add(logo);
        leftLayout.add(goToLandingPageButton);

        VerticalLayout rightLayout = new VerticalLayout();
        rightLayout.setSizeFull();
        rightLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        rightLayout.getStyle().set("text-align", "center");
        rightLayout.getStyle().set("border", "1px solid orange");

        rightLayout.add(emailField,passwordField,passwordConfirmationField,registerButton);

        mainLayout.add(leftLayout,rightLayout);

        add(mainLayout);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}