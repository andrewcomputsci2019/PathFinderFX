package com.andrewcomputsci.pathfinderfx.view;

import atlantafx.base.layout.InputGroup;
import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import com.andrewcomputsci.pathfinderfx.Model.Algorithm;
import com.andrewcomputsci.pathfinderfx.Model.CellType;
import com.andrewcomputsci.pathfinderfx.Model.Heuristic;
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
    private VBox algorithmSectionWrapper;
    private HBox algorithmSelectionWrapper;
    private HBox heuristicSelectionWrapper;
    private ComboBox<Algorithm> algorithmSelectionBox;
    private ComboBox<Heuristic> heuristicSelectionBox;


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
        initAlgorithmPicker();
    }

    private void initGridSizeUi() {
        gridSizeGroupWrapper = new VBox();
        gridSizeGroupWrapper.setAlignment(Pos.CENTER);
        gridSizeGroupWrapper.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px; -fx-border-color: gray;");
        gridSizeLabelWrapper = new HBox();
        gridSizeLabelWrapper.setAlignment(Pos.CENTER);
        gridSizeLabelWrapper.setSpacing(2.5);
        Label gridSizeLabel =  new Label();
        gridSizeLabel.setText("Grid Size:");
        gridSizeLabel.getStyleClass().addAll(Styles.TEXT,Styles.ACCENT);
        gridSizeTextField = new TextField();
        gridSizeTextField.setPromptText(String.format("%s,%s",PathFinderVisualizer.getDefaultWidth(),PathFinderVisualizer.getDefaultHeight()));
        gridSizeTextField.setPrefWidth(110.0);
        gridSizeLabelWrapper.getChildren().addAll(gridSizeLabel,gridSizeTextField);
        changeGridSizeButton = new Button("Change Current Grid");
        changeGridSizeButton.getStyleClass().addAll(Styles.ACCENT);
        changeGridSizeButton.setMnemonicParsing(true);
        changeGridSizeButton.setFont(new Font(13));
        gridSizeGroupWrapper.getChildren().addAll(gridSizeLabelWrapper, changeGridSizeButton);
        gridSizeGroupWrapper.setSpacing(4.0);
        VBox.setMargin(gridSizeLabelWrapper,new Insets(2.5,0,0,0));
        VBox.setMargin(gridSizeGroupWrapper,new Insets(2.0,2.5,0,2.5));
        gridSizeGroupWrapper.setPadding(new Insets(0,0,2.5,0));
        rootContainer.getChildren().add(gridSizeGroupWrapper);
    }
    private void initTilePicker(){
        tileTypeWrapper = new HBox();
        tileTypeWrapper.setPrefHeight(65.0);
        tileTypeWrapper.setSpacing(5.0);
        tileTypeWrapper.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px; -fx-border-color: gray;");
        tileTypeWrapper.setAlignment(Pos.CENTER);
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
        tileTypeComboBox.setPrefWidth(110);
        tileTypeComboBox.setItems(FXCollections.observableArrayList(CellType.Source,CellType.Target,CellType.Wall,CellType.Traversable));
        tileTypeComboBox.getSelectionModel().selectFirst();
        tileTypeWrapper.getChildren().addAll(tileTypeLabel,tileTypeComboBox);
        VBox.setMargin(tileTypeWrapper,new Insets(0,2.5,0,2.5));
        rootContainer.getChildren().add(tileTypeWrapper);
    }
    private void initAlgorithmPicker(){
        algorithmSectionWrapper = new VBox(5.0);
        algorithmSelectionWrapper = new HBox(2.5);
        Label algorithmSelectionLabel = new Label("Algorithm:");
        algorithmSelectionLabel.getStyleClass().addAll(Styles.TEXT,Styles.ACCENT);
        algorithmSelectionBox = new ComboBox<>();
        algorithmSelectionBox.getStyleClass().add(Tweaks.ALT_ICON);
        algorithmSelectionBox.setCellFactory(param -> new ListCell<>(){
            @Override
            protected void updateItem(Algorithm item, boolean empty){
                super.updateItem(item,empty);
                if(empty){
                    setText(null);
                }else {
                    setText(item.name());
                }
            }
        });
        algorithmSelectionBox.setItems(FXCollections.observableArrayList(Algorithm.AStar,Algorithm.BFS,Algorithm.BFSGreedy,Algorithm.DFS,Algorithm.WaveFront));
        algorithmSelectionBox.getSelectionModel().selectFirst();
        algorithmSelectionWrapper.getChildren().addAll(algorithmSelectionLabel,algorithmSelectionBox);
        heuristicSelectionWrapper = new HBox(2.5);
        Label heuristicSelectionLabel = new Label("Heuristic:");
        heuristicSelectionLabel.getStyleClass().addAll(Styles.TEXT,Styles.ACCENT);
        heuristicSelectionBox = new ComboBox<>();
        heuristicSelectionBox.getStyleClass().add(Tweaks.ALT_ICON);
        heuristicSelectionBox.setCellFactory(param -> new ListCell<>(){
            @Override
            protected void updateItem(Heuristic item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                }else {
                    setText(item.name());
                }
            }
        });
        heuristicSelectionBox.setItems(FXCollections.observableArrayList(Heuristic.Chebyshev,Heuristic.Euclidean,Heuristic.Manhattan));
        heuristicSelectionBox.getSelectionModel().selectFirst();
        algorithmSelectionBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            heuristicSelectionBox.setDisable(!newValue.equals(Algorithm.AStar));
        });
        heuristicSelectionBox.setPrefWidth(125.0);
        algorithmSelectionBox.setPrefWidth(125.0);
        heuristicSelectionWrapper.setAlignment(Pos.CENTER);
        algorithmSelectionWrapper.setAlignment(Pos.CENTER);
        heuristicSelectionWrapper.getChildren().addAll(heuristicSelectionLabel,heuristicSelectionBox);
        algorithmSectionWrapper.getChildren().addAll(algorithmSelectionWrapper,heuristicSelectionWrapper);
        VBox.setMargin(algorithmSelectionWrapper,new Insets(5.0,0,0,0));
        VBox.setMargin(heuristicSelectionWrapper,new Insets(0,0,2.5,0));
        VBox.setMargin(algorithmSectionWrapper,new Insets(0,2.5,0,2.5));
        algorithmSectionWrapper.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px; -fx-border-color: gray;");
        rootContainer.getChildren().add(algorithmSectionWrapper);
    }

    public ScrollPane getRootContent(){
        return scrollPane;
    }
}
