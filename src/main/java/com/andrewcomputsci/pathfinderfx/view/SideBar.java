package com.andrewcomputsci.pathfinderfx.view;

import com.andrewcomputsci.pathfinderfx.Model.CellType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SideBar {
    private VBox rootContainer;
    private ScrollPane scrollPane;
    private Button changeGridSizeButton;
    private TextField gridSizeTextField;
    private VBox gridSizeWrapper;
    private ComboBox<CellType> tileTypeComboBox;

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
        initGridSizeUi();
    }

    private void initGridSizeUi() {
        gridSizeWrapper = new VBox();
        gridSizeWrapper.setAlignment(Pos.CENTER);
        gridSizeWrapper.setPrefHeight(65.0);
        gridSizeWrapper.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px; -fx-border-color: gray;");
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        VBox.setMargin(gridSizeWrapper,new Insets(2.5));
        box.setSpacing(5.0);
        Label label =  new Label();
        label.setFont(new Font(13));
        label.setText("Grid Size: ");
        gridSizeTextField = new TextField();
        gridSizeTextField.setPromptText("25,25");
        box.getChildren().addAll(label,gridSizeTextField);
        changeGridSizeButton = new Button("Change Grid Size");
        gridSizeWrapper.getChildren().addAll(box, changeGridSizeButton);
        gridSizeWrapper.setSpacing(5.0);
        rootContainer.getChildren().add(gridSizeWrapper);
    }
    public void initTilePicker(){

    }

    public ScrollPane getRootContent(){
        return scrollPane;
    }
}
