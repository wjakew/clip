/**
 * @author Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.clip.frontend.components;

import com.flowingcode.vaadin.addons.markdown.MarkdownEditor;

import java.sql.Timestamp;

import com.flowingcode.vaadin.addons.markdown.BaseMarkdownComponent.DataColorMode;
import com.jakubwawak.clip.ClipApplication;
import com.jakubwawak.clip.entity.Clip;
import com.jakubwawak.clip.frontend.windows.ClipStatisticsWindow;
import com.jakubwawak.clip.frontend.windows.PublishWindow;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

/**
 * ClipEditor class for the Clip application
 */
public class ClipEditor extends VerticalLayout {

    private Clip clip;

    private boolean newClip;

    private String userSessionToken;
    private boolean proMode;

    private TextField cliptTitle;
    private MenuBar menuBar;
    private Button publishButton;
    private ComboBox<String> clipType;

    private MarkdownEditor markdownEditor;

    private H6 wordCount, signsCount, createdAs;

    /**
     * Constructor for the ClipEditor class
     */
    public ClipEditor(Clip clip, String userSessionToken, boolean proMode) {
        this.clip = clip;
        this.userSessionToken = userSessionToken;
        this.proMode = proMode;
        if (clip == null) {
            this.clip = new Clip();
            this.newClip = true;
        } else {
            this.newClip = false;
        }

        if (userSessionToken == null) {
            this.userSessionToken = "";
        }

        addClassName("clip-editor-component");
        this.setSizeFull();
        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);

        prepareComponents();
        prepareLayout();
    }

    /**
     * Function for preparing the components
     */
    private void prepareComponents() {
        this.cliptTitle = new TextField("");
        this.cliptTitle.setPlaceholder("Title");
        this.cliptTitle.setPrefixComponent(VaadinIcon.FLAG.create());
        this.cliptTitle.setWidthFull();
        cliptTitle.setValue(clip.getClipTitle());
        cliptTitle.addClassName("clip-editor-title");
        cliptTitle.setMaxLength(300);

        menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        menuBar.addClassName("menubar");

        MenuItem settings = menuBar.addItem("Settings");

        SubMenu settingsSubMenu = settings.getSubMenu();
        settingsSubMenu.addItem("Show Statistics", e -> {
            ClipStatisticsWindow clipStatisticsWindow = new ClipStatisticsWindow(clip);
            clipStatisticsWindow.main_dialog.open();
        });

        settingsSubMenu.addItem("Clear Clip", e -> {
            cliptTitle.setValue("");
            markdownEditor.setContent("");
            clipType.setValue("");
            clip = new Clip();
            newClip = true;
            ClipApplication.showNotification("Clip cleared");
        });

        publishButton = new Button("Publish", VaadinIcon.UPLOAD.create(), this::saveOrUpdateClip);
        publishButton.addClassName("landing-page-button-small");

        if (!newClip) {
            publishButton.setText("Update");
        }

        wordCount = new H6("words: " + clip.getClipWordCount());

        if (clip.getUserId() == 0) {
            createdAs = new H6("created as: guest");
        } else {
            createdAs = new H6("created as: " + clip.getUserId());
        }

        signsCount = new H6("signs: " + clip.getClipRaw().length());

        clipType = new ComboBox<String>();
        clipType.addClassName("clip-editor-clip-type");
        clipType.setItems("text", "code");
        clipType.setLabel("");
        clipType.setPlaceholder("type");
        clipType.setValue(clip.getClipExtension());

        this.markdownEditor = new MarkdownEditor();
        this.markdownEditor.setSizeFull();
        this.markdownEditor.setContent(clip.getClipRaw());
        this.markdownEditor.setPlaceholder("clip content...");
        this.markdownEditor.setDataColorMode(DataColorMode.DARK);

        this.markdownEditor.addContentChangeListener(e -> {
            String contentRaw = this.markdownEditor.getContent();
            int wordCountValue = contentRaw.split("\\s+").length;
            wordCount.setText("words: " + wordCountValue);
            signsCount.setText("signs: " + contentRaw.length());
        });

        if (proMode) {
            this.markdownEditor.setMaxLength(1000);
        } else {
            this.markdownEditor.setMaxLength(1000000);
        }

    }

    /**
     * Function for preparing the upper bar
     */
    private HorizontalLayout prepareUpperBar() {
        HorizontalLayout upperBar = new HorizontalLayout();
        upperBar.setWidthFull();
        upperBar.setJustifyContentMode(JustifyContentMode.CENTER);
        upperBar.setAlignItems(Alignment.CENTER);

        FlexLayout left_layout = new FlexLayout();
        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(JustifyContentMode.START);
        left_layout.setAlignItems(Alignment.CENTER);
        left_layout.setWidth("80%");
        left_layout.add();

        FlexLayout center_layout = new FlexLayout();
        center_layout.setSizeFull();
        center_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        center_layout.setAlignItems(FlexComponent.Alignment.CENTER);

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(JustifyContentMode.END);
        right_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        right_layout.setWidth("80%");

        left_layout.add(cliptTitle);
        right_layout.add(menuBar, publishButton);

        upperBar.add(left_layout, center_layout, right_layout);

        return upperBar;
    }

    /**
     * Function for preparing the lower bar
     */
    private HorizontalLayout prepareLowerBar() {
        HorizontalLayout lowerBar = new HorizontalLayout();
        lowerBar.setWidthFull();
        lowerBar.setJustifyContentMode(JustifyContentMode.CENTER);
        lowerBar.setAlignItems(Alignment.CENTER);

        FlexLayout left_layout = new FlexLayout();
        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(JustifyContentMode.START);
        left_layout.setAlignItems(Alignment.CENTER);
        left_layout.setWidth("80%");
        left_layout.add();

        FlexLayout center_layout = new FlexLayout();
        center_layout.setSizeFull();
        center_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        center_layout.setAlignItems(FlexComponent.Alignment.CENTER);

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(JustifyContentMode.END);
        right_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        right_layout.setWidth("80%");

        left_layout.add(clipType, createdAs);
        right_layout.add(wordCount, new H6("/"), signsCount);
        lowerBar.add(left_layout, center_layout, right_layout);

        return lowerBar;
    }

    /**
     * Function for saving or updating the clip
     */
    private void saveOrUpdateClip(ClickEvent<Button> event) {
        if (cliptTitle.getValue().isEmpty() && markdownEditor.getContent().isEmpty() && clipType.getValue().isEmpty()) {
            ClipApplication.showNotification("Please fill all the fields");
            return;
        }

        clip.setClipTitle(cliptTitle.getValue());
        clip.setClipRaw(markdownEditor.getContent());
        clip.setClipExtension(clipType.getValue());
        clip.setClipWordCount(clip.getClipRaw().split("\\s+").length);

        if (newClip) {
            clip.setClipCreatedAt(new Timestamp(System.currentTimeMillis()));
        }
        clip.setClipUpdatedAt(new Timestamp(System.currentTimeMillis()));

        PublishWindow publishWindow = new PublishWindow(clip, userSessionToken, proMode, newClip, this);
        add(publishWindow.main_dialog);
        publishWindow.main_dialog.open();
    }

    /**
     * Function for preparing the layout
     */
    private void prepareLayout() {
        this.add(prepareUpperBar(), markdownEditor, prepareLowerBar());
    }

    /**
     * Function for clearing the clip editor
     */
    public void clear() {
        this.markdownEditor.setContent("");
        this.cliptTitle.setValue("");
        this.clipType.setValue("");
        this.clip = new Clip();
        this.newClip = true;
    }
}
