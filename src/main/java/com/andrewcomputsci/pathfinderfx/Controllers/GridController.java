package com.andrewcomputsci.pathfinderfx.Controllers;

import atlantafx.base.controls.ToggleSwitch;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;
import com.andrewcomputsci.pathfinderfx.view.PathFinderVisualizer;
import com.andrewcomputsci.pathfinderfx.view.SideBar;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;

import java.util.Timer;

public class GridController {
    private PathFinderVisualizer grid;
    private SideBar sideBar;
    private SimpleBooleanProperty exactMode;
    private ContextMenu settingMenu;
    private ToggleSwitch toggleSwitch;

    //should use timeline to added ability to animate at a fixed interval
    public GridController(PathFinderVisualizer visualizer, SideBar sideBar){
            if(visualizer == null || sideBar == null){
                throw new IllegalArgumentException("Null parameter given");
            }
            this.grid = visualizer;
            this.sideBar = sideBar;
            exactMode = new SimpleBooleanProperty(false);
            toggleSwitch = new ToggleSwitch("Exact Mode");
            toggleSwitch.setTooltip(new Tooltip("Option that forces some algorithms to expand search past first solution namely DFS"));
            exactMode.bind(toggleSwitch.selectedProperty());
            settingMenu = new ContextMenu(new CustomMenuItem(toggleSwitch));
            addContextMenu();
    }


    private void addContextMenu(){
        grid.getGridNode().setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.SECONDARY)){
                System.out.println("Exact Value: "  + exactMode.get());
                settingMenu.show(grid.getGridNode(),event.getScreenX(),event.getScreenY());
            }
        });
    }

    private void initCellControls(){

    }



}
