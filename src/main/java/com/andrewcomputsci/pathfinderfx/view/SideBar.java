package com.andrewcomputsci.pathfinderfx.view;

import atlantafx.base.layout.InputGroup;
import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import com.andrewcomputsci.pathfinderfx.Model.Algorithm;
import com.andrewcomputsci.pathfinderfx.Model.CellType;
import com.andrewcomputsci.pathfinderfx.Model.Heuristic;
import com.andrewcomputsci.pathfinderfx.Model.MazeType;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.kordamp.ikonli.javafx.FontIcon;

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
    private HBox gridCustomizationHorizontalLayout;
    private Button addRandomWeights;
    private Button addRandomWalls;
    private HBox gridMazeGenerationSection;
    private InputGroup mazeGroup;
    private Button mazeGenButton;
    private ComboBox<MazeType> mazeTypeComboBox;

    private HBox controlSectionContainer;

    private SplitMenuButton controlBar;
    private MenuItem startButton;
    private MenuItem clearButton;
    private MenuItem exitButton;


    private VBox statContainer;
    private Label iterations;
    private Label pathFound;
    private Label pathCost;
    private Label deltaTime;


    public SideBar() {
        rootContainer = new VBox();
        rootContainer.setAlignment(Pos.TOP_CENTER);
        rootContainer.setSpacing(12.5);
        scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(rootContainer);
        initGridSizeUi();
        initTilePicker();
        initAlgorithmPicker();
        initGridCustomizationSection();
        initMazeGenerationSection();
        initControlUi();
        initStaticsSection();
    }

    private void initGridSizeUi() {
        gridSizeGroupWrapper = new VBox();
        gridSizeGroupWrapper.setAlignment(Pos.CENTER);
        gridSizeGroupWrapper.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px; -fx-border-color: gray;");
        gridSizeLabelWrapper = new HBox();
        gridSizeLabelWrapper.setAlignment(Pos.CENTER);
        gridSizeLabelWrapper.setSpacing(2.5);
        Label gridSizeLabel = new Label();
        gridSizeLabel.setText("Grid Size:");
        gridSizeLabel.getStyleClass().addAll(Styles.TEXT, Styles.ACCENT);
        gridSizeTextField = new TextField();
        gridSizeTextField.setPromptText(String.format("%s,%s", PathFinderVisualizer.getDefaultWidth(), PathFinderVisualizer.getDefaultHeight()));
        gridSizeTextField.setPrefWidth(110.0);
        gridSizeLabelWrapper.getChildren().addAll(gridSizeLabel, gridSizeTextField);
        changeGridSizeButton = new Button("Change Current Grid");
        changeGridSizeButton.getStyleClass().addAll(Styles.ACCENT);
        changeGridSizeButton.setMnemonicParsing(true);
        changeGridSizeButton.setFont(new Font(13));
        gridSizeGroupWrapper.getChildren().addAll(gridSizeLabelWrapper, changeGridSizeButton);
        gridSizeGroupWrapper.setSpacing(4.0);
        VBox.setMargin(gridSizeLabelWrapper, new Insets(2.5, 0, 0, 0));
        VBox.setMargin(gridSizeGroupWrapper, new Insets(2.0, 2.5, 0, 2.5));
        gridSizeGroupWrapper.setPadding(new Insets(0, 0, 2.5, 0));
        rootContainer.getChildren().add(gridSizeGroupWrapper);
    }

    private void initTilePicker() {
        tileTypeWrapper = new HBox();
        tileTypeWrapper.setPrefHeight(65.0);
        tileTypeWrapper.setSpacing(5.0);
        tileTypeWrapper.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px; -fx-border-color: gray;");
        tileTypeWrapper.setAlignment(Pos.CENTER);
        Label tileTypeLabel = new Label("TileType:");
        tileTypeLabel.getStyleClass().addAll(Styles.TEXT, Styles.ACCENT);
        tileTypeComboBox = new ComboBox<>();
        tileTypeComboBox.getStyleClass().add(Tweaks.ALT_ICON);
        tileTypeComboBox.setCellFactory(param -> new ListCell<>() {

            @Override
            protected void updateItem(CellType item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);

                } else {
                    setText(item.name());
                }
            }
        });
        tileTypeComboBox.setPrefWidth(110);
        tileTypeComboBox.setItems(FXCollections.observableArrayList(CellType.Source, CellType.Target, CellType.Wall, CellType.Traversable));
        tileTypeComboBox.getSelectionModel().selectFirst();
        tileTypeWrapper.getChildren().addAll(tileTypeLabel, tileTypeComboBox);
        VBox.setMargin(tileTypeWrapper, new Insets(0, 2.5, 0, 2.5));
        rootContainer.getChildren().add(tileTypeWrapper);
    }

    private void initAlgorithmPicker() {
        algorithmSectionWrapper = new VBox(5.0);
        algorithmSelectionWrapper = new HBox(2.5);
        Label algorithmSelectionLabel = new Label("Algorithm:");
        algorithmSelectionLabel.getStyleClass().addAll(Styles.TEXT, Styles.ACCENT);
        algorithmSelectionLabel.setAlignment(Pos.CENTER_RIGHT);
        algorithmSelectionBox = new ComboBox<>();
        algorithmSelectionBox.getStyleClass().add(Tweaks.ALT_ICON);
        algorithmSelectionBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Algorithm item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.name());
                }
            }
        });
        algorithmSelectionBox.setItems(FXCollections.observableArrayList(Algorithm.AStar, Algorithm.BFS, Algorithm.BFSGreedy, Algorithm.DFS, Algorithm.WaveFront));
        algorithmSelectionBox.getSelectionModel().selectFirst();
        algorithmSelectionWrapper.getChildren().addAll(algorithmSelectionLabel, algorithmSelectionBox);
        heuristicSelectionWrapper = new HBox(5);
        Label heuristicSelectionLabel = new Label("Heuristic: ");
        heuristicSelectionLabel.getStyleClass().addAll(Styles.TEXT, Styles.ACCENT);
        heuristicSelectionLabel.setAlignment(Pos.CENTER_RIGHT);
        heuristicSelectionBox = new ComboBox<>();
        heuristicSelectionBox.getStyleClass().add(Tweaks.ALT_ICON);
        heuristicSelectionBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Heuristic item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.name());
                }
            }
        });
        heuristicSelectionBox.setItems(FXCollections.observableArrayList(Heuristic.Chebyshev, Heuristic.Euclidean, Heuristic.Manhattan));
        heuristicSelectionBox.getSelectionModel().selectFirst();
        algorithmSelectionBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            heuristicSelectionBox.setDisable(!newValue.equals(Algorithm.AStar));
        });
        heuristicSelectionBox.setPrefWidth(125.0);
        algorithmSelectionBox.setPrefWidth(125.0);
        heuristicSelectionWrapper.setAlignment(Pos.CENTER);
        algorithmSelectionWrapper.setAlignment(Pos.CENTER);
        heuristicSelectionWrapper.getChildren().addAll(heuristicSelectionLabel, heuristicSelectionBox);
        algorithmSectionWrapper.getChildren().addAll(algorithmSelectionWrapper, heuristicSelectionWrapper);
        VBox.setMargin(algorithmSelectionWrapper, new Insets(5.0, 0, 0, 0));
        VBox.setMargin(heuristicSelectionWrapper, new Insets(0, 0, 2.5, 0));
        VBox.setMargin(algorithmSectionWrapper, new Insets(0, 2.5, 0, 2.5));
        algorithmSectionWrapper.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px; -fx-border-color: gray;");
        rootContainer.getChildren().add(algorithmSectionWrapper);
    }

    private void initGridCustomizationSection() {
        gridCustomizationHorizontalLayout = new HBox(2.5);
        gridCustomizationHorizontalLayout.setPrefHeight(65);
        gridCustomizationHorizontalLayout.setAlignment(Pos.CENTER);
        gridCustomizationHorizontalLayout.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px; -fx-border-color: gray;");
        Label gridCusLabel = new Label("Random Grid Gen:");
        gridCusLabel.getStyleClass().addAll(Styles.TEXT, Styles.ACCENT);
        addRandomWeights = new Button("Weight");
        addRandomWalls = new Button("Walls");
        addRandomWalls.getStyleClass().addAll(Styles.ACCENT, Styles.SMALL);
        addRandomWeights.getStyleClass().addAll(Styles.ACCENT, Styles.SMALL);
        gridCustomizationHorizontalLayout.getChildren().addAll(gridCusLabel, addRandomWalls, addRandomWeights);
        VBox.setMargin(gridCustomizationHorizontalLayout, new Insets(0, 2.5, 0, 2.5));
        rootContainer.getChildren().add(gridCustomizationHorizontalLayout);
    }

    private void initMazeGenerationSection() {
        gridMazeGenerationSection = new HBox(2.5);
        gridMazeGenerationSection.setAlignment(Pos.CENTER);
        gridMazeGenerationSection.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px; -fx-border-color: gray;");
        gridMazeGenerationSection.setPrefHeight(65.0);
        Label mazeLabel = new Label("Create Maze:");
        mazeLabel.getStyleClass().addAll(Styles.TEXT, Styles.ACCENT);
        mazeGenButton = new Button("Go");
        mazeGenButton.getStyleClass().addAll(Styles.ACCENT);
        mazeTypeComboBox = new ComboBox<>();
        mazeTypeComboBox.getStyleClass().addAll(Tweaks.ALT_ICON);
        mazeTypeComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(MazeType item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.name());
                }
            }
        });
        mazeTypeComboBox.setPrefWidth(130);
        mazeTypeComboBox.setItems(FXCollections.observableArrayList(MazeType.BinaryTree, MazeType.DFSRandom, MazeType.Kruskal, MazeType.Prims, MazeType.Recursive));
        mazeTypeComboBox.getSelectionModel().selectFirst();
        mazeGroup = new InputGroup(mazeTypeComboBox, mazeGenButton);
        gridMazeGenerationSection.getChildren().addAll(mazeLabel, mazeGroup);
        VBox.setMargin(gridMazeGenerationSection, new Insets(0, 2.5, 0, 2.5));
        rootContainer.getChildren().add(gridMazeGenerationSection);
    }

    private void initControlUi() {
        controlSectionContainer = new HBox(2.5);
        controlSectionContainer.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px; -fx-border-color: gray;");
        controlSectionContainer.setAlignment(Pos.CENTER);
        FontIcon start = new FontIcon("bi-play-fill");
        FontIcon clear = new FontIcon("bi-eraser-fill");
        FontIcon stop = new FontIcon("bi-skip-end-fill");
        startButton = new MenuItem("Start");
        startButton.setGraphic(start);
        clearButton = new MenuItem("Clear Grid");
        clearButton.setGraphic(clear);
        exitButton = new MenuItem("Skip");
        exitButton.setGraphic(stop);
        controlBar = new SplitMenuButton(startButton, clearButton, exitButton);
        controlBar.setPrefWidth(165);
        controlBar.setText("Program Control");
        HBox.setMargin(controlBar, new Insets(5, 0, 5, 0));
        controlSectionContainer.getChildren().add(controlBar);
        VBox.setMargin(controlSectionContainer, new Insets(0, 2.5, 0, 2.5));
        rootContainer.getChildren().add(controlSectionContainer);
    }

    private void initStaticsSection() {
        statContainer = new VBox();
        statContainer.setAlignment(Pos.CENTER);
        statContainer.setPrefHeight(80);
        VBox.setMargin(statContainer, new Insets(0, 2.5, 0, 2.5));

        pathFound = new Label("N/A");
        iterations = new Label("N/A");
        pathCost = new Label("N/A");
        deltaTime = new Label("N/A");

        pathFound.getStyleClass().addAll(Styles.TEXT, Styles.TEXT_MUTED);
        pathCost.getStyleClass().addAll(Styles.TEXT, Styles.TEXT_MUTED);
        deltaTime.getStyleClass().addAll(Styles.TEXT, Styles.TEXT_MUTED);
        iterations.getStyleClass().addAll(Styles.TEXT,Styles.TEXT_MUTED);

        pathFound.setAlignment(Pos.CENTER_LEFT);
        pathCost.setAlignment(Pos.CENTER_LEFT);
        deltaTime.setAlignment(Pos.CENTER_LEFT);

        HBox pathContainer = new HBox(1);
        VBox.setMargin(pathContainer, new Insets(2.5, 2.5, 2.5, 2.5));
        pathContainer.setAlignment(Pos.CENTER);
        Label pathLabel = new Label("Path Found?: ");
        pathLabel.getStyleClass().addAll(Styles.TEXT, Styles.ACCENT);
        pathLabel.setAlignment(Pos.CENTER_RIGHT);
        pathContainer.getChildren().addAll(pathLabel, pathFound);


        HBox iterationContainer = new HBox(1);
        iterationContainer.setAlignment(Pos.CENTER);
        VBox.setMargin(iterationContainer,new Insets(2.5));
        Label iterationsLabel = new Label("Total Iterations: ");
        iterationsLabel.setAlignment(Pos.CENTER_RIGHT);
        iterationsLabel.getStyleClass().addAll(Styles.TEXT,Styles.ACCENT);
        iterationContainer.getChildren().addAll(iterationsLabel,iterations);

        HBox pathCostHBox = new HBox(1);
        pathCostHBox.setAlignment(Pos.CENTER);
        VBox.setMargin(pathCostHBox, new Insets(2.5));
        Label pathCostLabel = new Label("Path Total Cost: ");
        pathCostLabel.setAlignment(Pos.CENTER_RIGHT);
        pathCostLabel.getStyleClass().addAll(Styles.TEXT, Styles.ACCENT);
        pathCostHBox.getChildren().addAll(pathCostLabel, pathCost);

        HBox elapsedTimeBox = new HBox(1);
        elapsedTimeBox.setAlignment(Pos.CENTER);
        VBox.setMargin(elapsedTimeBox, new Insets(2.5));
        Label elaspedTimeLabel = new Label("Total Time: ");
        elaspedTimeLabel.getStyleClass().addAll(Styles.TEXT, Styles.ACCENT);
        elaspedTimeLabel.setAlignment(Pos.CENTER_RIGHT);
        elapsedTimeBox.getChildren().addAll(elaspedTimeLabel, deltaTime);

        statContainer.getChildren().addAll(pathContainer,iterationContainer, pathCostHBox, elapsedTimeBox);
        statContainer.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px; -fx-border-color: gray;");
        rootContainer.getChildren().add(statContainer);

    }

    public ScrollPane getRootContent() {
        return scrollPane;
    }

    public Button getChangeGridSizeButton() {
        return changeGridSizeButton;
    }

    public TextField getGridSizeTextField() {
        return gridSizeTextField;
    }

    public ComboBox<CellType> getTileTypeComboBox() {
        return tileTypeComboBox;
    }

    public ComboBox<Algorithm> getAlgorithmSelectionBox() {
        return algorithmSelectionBox;
    }

    public ComboBox<Heuristic> getHeuristicSelectionBox() {
        return heuristicSelectionBox;
    }

    public Button getAddRandomWeights() {
        return addRandomWeights;
    }

    public Button getAddRandomWalls() {
        return addRandomWalls;
    }

    public Button getMazeGenButton() {
        return mazeGenButton;
    }

    public ComboBox<MazeType> getMazeTypeComboBox() {
        return mazeTypeComboBox;
    }

    public SplitMenuButton getControlBar() {
        return controlBar;
    }

    public MenuItem getStartButton() {
        return startButton;
    }

    public MenuItem getClearButton() {
        return clearButton;
    }

    public MenuItem getExitButton() {
        return exitButton;
    }

    public Label getPathFound() {
        return pathFound;
    }

    public Label getPathCost() {
        return pathCost;
    }

    public Label getDeltaTime() {
        return deltaTime;
    }

    public Label getIterations() {
        return iterations;
    }
}
