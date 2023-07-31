package com.andrewcomputsci.pathfinderfx.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SideBar {
    private VBox rootContainer;
    private ScrollPane scrollPane;

    public SideBar() {
        rootContainer = new VBox();
        rootContainer.setAlignment(Pos.TOP_CENTER);
        rootContainer.setStyle("-fx-border-width: 1px; -fx-border-color: red;");
        rootContainer.setSpacing(25.0);
        scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(rootContainer);
        scrollPane.setStyle("-fx-border-width: 1px; -fx-border-color: red;");
        initUi();
    }

    private void initUi() {
        VBox wrapper = new VBox();
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setPrefHeight(50.0);
        wrapper.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px; -fx-border-color: gray;");
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        VBox.setMargin(wrapper,new Insets(2.5));
        box.setSpacing(5.0);
        Label label =  new Label();
        label.setFont(new Font(12));
        label.setText("Grid Size: ");
        TextField field = new TextField();
        field.setPromptText("25,25");
        box.getChildren().addAll(label,field);
        wrapper.getChildren().addAll(box,new Button("Click Me"));
        rootContainer.getChildren().add(wrapper);
    }

    public ScrollPane getRootContent(){
        return scrollPane;
    }
}
