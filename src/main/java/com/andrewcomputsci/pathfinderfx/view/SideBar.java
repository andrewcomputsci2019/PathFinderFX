package com.andrewcomputsci.pathfinderfx.view;

import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import com.andrewcomputsci.pathfinderfx.Model.CellType;
import javafx.collections.FXCollections;
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
    private HBox gridSizeLabelWrapper;
    private VBox gridSizeGroupWrapper;
    private HBox tileTypeWrapper = new HBox();
    private ComboBox<CellType> tileTypeComboBox;

    public SideBar() {
        rootContainer = new VBox();
        rootContainer.setAlignment(Pos.TOP_CENTER);
        rootContainer.setStyle("-fx-border-width: 1px; -fx-border-color: red;");
        rootContainer.setSpacing(12.5);
        scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(rootContainer);
        scrollPane.setStyle("-fx-border-width: 1px; -fx-border-color: red;");
        initGridSizeUi();
        initTilePicker();
    }

    private void initGridSizeUi() {
        gridSizeGroupWrapper = new VBox();
        gridSizeGroupWrapper.setAlignment(Pos.CENTER);
        gridSizeGroupWrapper.setPrefHeight(65.0);
        gridSizeGroupWrapper.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px; -fx-border-color: gray;");
        gridSizeLabelWrapper = new HBox();
        gridSizeLabelWrapper.setAlignment(Pos.CENTER);
        VBox.setMargin(gridSizeGroupWrapper,new Insets(2.5));
        gridSizeLabelWrapper.setSpacing(2.5);
        Label gridSizeLabel =  new Label();
        gridSizeLabel.setText("Grid Size:");
        gridSizeLabel.getStyleClass().addAll(Styles.TEXT,Styles.ACCENT);
        gridSizeTextField = new TextField();
        gridSizeTextField.setPromptText("25,25");
        gridSizeLabelWrapper.getChildren().addAll(gridSizeLabel,gridSizeTextField);
        changeGridSizeButton = new Button("Change Current Grid");
        changeGridSizeButton.getStyleClass().add(Styles.ACCENT);
        changeGridSizeButton.setMnemonicParsing(true);
        changeGridSizeButton.setFont(new Font(13));
        gridSizeGroupWrapper.getChildren().addAll(gridSizeLabelWrapper, changeGridSizeButton);
        gridSizeGroupWrapper.setSpacing(4.0);
        rootContainer.getChildren().add(gridSizeGroupWrapper);
    }
    private void initTilePicker(){
        tileTypeWrapper = new HBox();
        tileTypeWrapper.setPrefHeight(65.0);
        tileTypeWrapper.setSpacing(5.0);
        tileTypeWrapper.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px; -fx-border-color: gray;");
        tileTypeWrapper.setAlignment(Pos.CENTER);
        VBox.setMargin(tileTypeWrapper,new Insets(2.5));
        Label tileTypeLabel = new Label("TileType:");
        tileTypeLabel.getStyleClass().addAll(Styles.TEXT,Styles.ACCENT);
        tileTypeComboBox = new ComboBox<>();
        tileTypeComboBox.getStyleClass().add(Tweaks.ALT_ICON);
        tileTypeComboBox.setCellFactory(param -> new ListCell<>(){

            @Override
            protected void updateItem(CellType item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);

                }else {
                    setText(item.name());
                }
            }
        });
        tileTypeComboBox.setPrefWidth(140);
        tileTypeComboBox.setItems(FXCollections.observableArrayList(CellType.Source,CellType.Target,CellType.Wall,CellType.Traversable));
        tileTypeComboBox.getSelectionModel().selectFirst();
        tileTypeWrapper.getChildren().addAll(tileTypeLabel,tileTypeComboBox);
        rootContainer.getChildren().add(tileTypeWrapper);
    }
    private void initAlgorithmPicker(){

    }

    public ScrollPane getRootContent(){
        return scrollPane;
    }
}
