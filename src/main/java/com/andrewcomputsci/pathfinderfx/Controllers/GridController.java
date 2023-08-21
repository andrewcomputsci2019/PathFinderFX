package com.andrewcomputsci.pathfinderfx.Controllers;

import atlantafx.base.controls.ProgressSliderSkin;
import atlantafx.base.controls.ToggleSwitch;
import atlantafx.base.theme.Styles;
import com.andrewcomputsci.pathfinderfx.Model.Cell;
import com.andrewcomputsci.pathfinderfx.Model.CellType;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Timer;

public class GridController {
    private PathFinderVisualizer grid;
    private SideBar sideBar;
    private SimpleBooleanProperty exactMode;
    private ContextMenu settingMenu;
    private ToggleSwitch toggleSwitch;

    private Slider animationRateSlider;

    private Timeline timeline;

    private boolean editableState;

    private boolean targetPlaced;

    private boolean sourcePlaced;

    //should use timeline to added ability to animate at a fixed interval
    public GridController(PathFinderVisualizer visualizer, SideBar sideBar){
            if(visualizer == null || sideBar == null){
                throw new IllegalArgumentException("Null parameter given");
            }
            this.grid = visualizer;
            this.sideBar = sideBar;
            initStateVars();
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
            addCellTypeListeners();
            initCellControls();

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


    private void initStateVars(){
        editableState = true;
        sourcePlaced = false;
        targetPlaced = false;
    }

    private void addCellTypeListeners(){

        for(CellRectangle rect : grid.getCellGrid()){
            rect.getInnerCell().typeProperty().addListener((observable, oldValue, newValue) -> {
                rect.setFill(newValue.getColor());
            });
        }
    }

    private void initCellControls(){
        grid.getGridNode().setOnMouseDragged(event -> {
            if(!editableState){
                return;
            }

            //get cell from grid
            int col;
            int row;
            double percentWidth = ((100.0)/(grid.getWidth())) * grid.getGridNode().getWidth();
            double percentHeight = ((100.0)/(grid.getHeight())) * grid.getGridNode().getHeight();
            percentHeight /=100.0;
            percentWidth /=100.0;
            col = (int) (event.getX()/percentWidth);
            row = (int) (event.getY()/percentHeight);
            System.out.println(col + " , " + row);
            CellRectangle item = (CellRectangle) ((Pane)grid.getGridNode().getChildren().get(row*grid.getWidth()+col)).getChildren().get(0);

            if(event.isPrimaryButtonDown()){
                changeCellType(item);
            }else if(event.isSecondaryButtonDown()){
                CellType type = item.getInnerCell().getType();
                targetPlaced = targetPlaced&&!type.equals(CellType.Target);
                sourcePlaced = sourcePlaced&&!type.equals(CellType.Source);
                item.getInnerCell().typeProperty().set(CellType.Traversable);
            }

        });
        for(CellRectangle rect: grid.getCellGrid()){
            rect.setOnMouseClicked(event -> {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    changeCellType(rect);
                }
                else if (event.getButton().equals(MouseButton.SECONDARY)){
                    CellType type = rect.getInnerCell().getType();
                    targetPlaced = targetPlaced&&!type.equals(CellType.Target);
                    sourcePlaced = sourcePlaced&&!type.equals(CellType.Source);
                    rect.getInnerCell().typeProperty().set(CellType.Traversable);
                }
            });
        }
    }

    private void changeCellType(CellRectangle rect) {
        CellType type = sideBar.getTileTypeComboBox().getValue();
        if(type.equals(CellType.Target) && targetPlaced){
            return;
        }
        if(type.equals(CellType.Source) && sourcePlaced){
            return;
        }
        sourcePlaced = type.equals(CellType.Source);
        targetPlaced = type.equals(CellType.Target);
        rect.getInnerCell().typeProperty().set(type);
    }
}

