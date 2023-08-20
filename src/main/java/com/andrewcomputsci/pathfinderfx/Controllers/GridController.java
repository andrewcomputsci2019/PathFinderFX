package com.andrewcomputsci.pathfinderfx.Controllers;

import atlantafx.base.controls.ProgressSliderSkin;
import atlantafx.base.controls.ToggleSwitch;
import atlantafx.base.theme.Styles;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;
import com.andrewcomputsci.pathfinderfx.view.PathFinderVisualizer;
import com.andrewcomputsci.pathfinderfx.view.SideBar;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;

import java.util.Timer;

public class GridController {
    private PathFinderVisualizer grid;
    private SideBar sideBar;
    private SimpleBooleanProperty exactMode;
    private ContextMenu settingMenu;
    private ToggleSwitch toggleSwitch;

    private Slider animationRateSlider;

    private Timeline timeline;

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

            toggleSwitch.selectedProperty().addListener((observable, oldValue, newValue) -> {
                toggleSwitch.pseudoClassStateChanged(Styles.STATE_SUCCESS,newValue);
            });
            animationRateSlider = new Slider(0,2,1);
            animationRateSlider.setSkin(new ProgressSliderSkin(animationRateSlider));
            animationRateSlider.getStyleClass().add(Styles.SMALL);
            animationRateSlider.setShowTickMarks(true);
            animationRateSlider.setShowTickLabels(true);
            animationRateSlider.setMajorTickUnit(1);
            animationRateSlider.setTooltip(new Tooltip("Controls the rate of the Animation by default 1 - normal, 0 - paused, and 2 - double the normal"));
            exactMode.bind(toggleSwitch.selectedProperty());
            CustomMenuItem animationItem = new CustomMenuItem(animationRateSlider);
            CustomMenuItem item = new CustomMenuItem(toggleSwitch);
            item.setHideOnClick(false);
            animationItem.setHideOnClick(false);
            settingMenu = new ContextMenu(item,animationItem);
            addContextMenu();

    }


    private void addContextMenu(){
        grid.getGridNode().setOnMouseClicked(event -> {
            if (event.isControlDown() && event.getButton().equals(MouseButton.SECONDARY)){
                System.out.println("Exact Value: "  + exactMode.get());
                settingMenu.show(grid.getGridNode(),event.getScreenX(),event.getScreenY());
            }else if(settingMenu.isShowing()){
                settingMenu.hide();
            }
        });
    }

    private void initCellControls(){
        for(CellRectangle rect: grid.getCellGrid()){

        }
    }



}
