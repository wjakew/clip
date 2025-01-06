/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.clip.frontend.components;

import com.jakubwawak.clip.ClipApplication;
import com.jakubwawak.clip.entity.Clip;
import com.jakubwawak.clip.frontend.windows.EditorPasswordWindow;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.ast.Node;

/**
 * Component for viewing a clip
 */
public class PublicClipViewer extends VerticalLayout {

    Clip clip;

    HorizontalLayout header;

    Button editButton;

    /**
     * Constructor for ClipViewer
     * 
     * @param clip
     */
    public PublicClipViewer(Clip clip) {
        this.clip = clip;

        addClassName("clip-viewer");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        prepareLayout();
    }

    /**
     * Creates the header of the clip viewer
     */
    private void createHeader() {
        header = new HorizontalLayout();

        FlexLayout left_layout = new FlexLayout();
        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(JustifyContentMode.START);
        left_layout.setAlignItems(Alignment.CENTER);
        left_layout.setWidth("80%");
        left_layout.add();

        left_layout.add(new H4(clip.getClipTitle()));

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(JustifyContentMode.END);
        right_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        right_layout.setWidth("80%");

        editButton = new Button("Edit", VaadinIcon.PENCIL.create(), this::goToEdit);
        editButton.addClassName("landing-page-button-small");

        right_layout.add(new H6(clip.getClipCreatedAt().toString()), editButton);

        header.add(left_layout, right_layout);
        header.setWidth("100%");
        header.setAlignItems(Alignment.CENTER);
    }

    /**
     * Creates a renderer for the clip
     * 
     * @return Html
     */
    Html createRenderer() {
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse(clip.getClipRaw());
        String value = "<body><div>" + renderer.render(document) + "</div></body>";
        Html htmlPreview = new Html(value);
        htmlPreview.getStyle().set("width", "100%");
        htmlPreview.getStyle().set("height", "100%");
        htmlPreview.getStyle().set("text-align", "left");
        htmlPreview.getStyle().set("overflow", "auto");
        return htmlPreview;
    }

    /**
     * Prepares the layout for the clip viewer
     */
    void prepareLayout() {
        createHeader();
        add(header);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setAlignItems(Alignment.CENTER);
        content.setJustifyContentMode(JustifyContentMode.CENTER);
        content.add(createRenderer());

        add(content);
    }

    /**
     * Function for going to the edit page
     */
    private void goToEdit(ClickEvent<Button> event) {
        EditorPasswordWindow editorPasswordWindow = new EditorPasswordWindow(clip);
        add(editorPasswordWindow.main_dialog);
        editorPasswordWindow.main_dialog.open();
    }
}
