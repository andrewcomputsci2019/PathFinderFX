package com.andrewcomputsci.pathfinderfx;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import com.andrewcomputsci.pathfinderfx.Controllers.GridController;
import com.andrewcomputsci.pathfinderfx.view.PathFinderVisualizer;
import com.andrewcomputsci.pathfinderfx.view.SideBar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.concurrent.Executors;


public class PathFinderApplication extends Application {
    private static int DEFAULT_WIDTH = 1000;
    private static int DEFAULT_HEIGHT = 800;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        SplitPane splitPane = new SplitPane();
        PathFinderVisualizer visualizer = new PathFinderVisualizer();
        SideBar bar = new SideBar();
        GridPane grid = visualizer.getGridNode();
        ScrollPane scrollPane = bar.getRootContent();
        scrollPane.setMinWidth(255);
        scrollPane.setMinHeight(400.0);
        splitPane.getItems().addAll(scrollPane,grid);
        splitPane.setDividerPosition(0,.30); //initial view split
        SplitPane.setResizableWithParent(scrollPane,false); //make sure grid grows over sidebar during resize
        Scene scene = new Scene(splitPane,DEFAULT_WIDTH,DEFAULT_HEIGHT);
        primaryStage.setTitle("Path FinderFX");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(600.0);
        primaryStage.setMinHeight(600.0);
        new GridController(visualizer,bar);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> GridController.algoExecutor.shutdown());
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
        Runtime.getRuntime().addShutdownHook(new Thread(GridController.algoExecutor::shutdown));
        launch(args);
    }
}
