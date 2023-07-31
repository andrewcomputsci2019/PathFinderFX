package com.andrewcomputsci.pathfinderfx;

import com.andrewcomputsci.pathfinderfx.Model.CellState;
import com.andrewcomputsci.pathfinderfx.view.CellRectange;
import com.andrewcomputsci.pathfinderfx.view.PathFinderVisualizer;
import com.andrewcomputsci.pathfinderfx.view.SideBar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class PathFinderApplication extends Application {
    private static int DEFAULT_WIDTH = 1005;
    private static int DEFAULT_HEIGHT = 1005;
    @Override
    public void start(Stage primaryStage) throws Exception {
        SplitPane splitPane = new SplitPane();
        PathFinderVisualizer visualizer = new PathFinderVisualizer();
        SideBar bar = new SideBar();
        GridPane grid = visualizer.getGridNode();
        ScrollPane scrollPane = bar.getRootContent();
        scrollPane.setMaxWidth(225.0);
        scrollPane.setMinWidth(225.0);
        scrollPane.setMinHeight(400.0);
        splitPane.getItems().addAll(scrollPane,grid);
        Scene scene = new Scene(splitPane,DEFAULT_WIDTH,DEFAULT_HEIGHT);
        primaryStage.setTitle("Path FinderFX");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(600.0);
        primaryStage.setMinHeight(600.0);
        primaryStage.show();
    }
    private void alt(){
        AnchorPane pane = new AnchorPane();
        PathFinderVisualizer visualizer = new PathFinderVisualizer();
        SideBar bar = new SideBar();
        ScrollPane scrollPane = bar.getRootContent();
        GridPane grid = visualizer.getGridNode();
        AnchorPane.setBottomAnchor(scrollPane,0.0);
        AnchorPane.setTopAnchor(scrollPane,0.0);
        AnchorPane.setLeftAnchor(scrollPane,0.0);
        AnchorPane.setRightAnchor(scrollPane,DEFAULT_WIDTH-225.0);
        //sets the width of the scroll pane
        AnchorPane.setLeftAnchor(grid,225.0);
        //attaches grid to top, bottom and right hand side of the screen
        AnchorPane.setRightAnchor(grid,0.0);
        AnchorPane.setTopAnchor(grid,0.0);
        AnchorPane.setBottomAnchor(grid,0.0);
        pane.getChildren().add(scrollPane);
        pane.getChildren().add(grid);
        pane.widthProperty().addListener((observable, oldValue, newValue) -> {
            AnchorPane.setRightAnchor(scrollPane,newValue.doubleValue()-225.0);
        });
        Scene scene = new Scene(pane,DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
