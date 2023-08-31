package com.andrewcomputsci.pathfinderfx.Controllers;

import atlantafx.base.controls.ProgressSliderSkin;
import atlantafx.base.controls.ToggleSwitch;
import atlantafx.base.theme.Styles;
import com.andrewcomputsci.pathfinderfx.Generators.MazeGenerator;
import com.andrewcomputsci.pathfinderfx.Model.CellState;
import com.andrewcomputsci.pathfinderfx.Model.CellType;
import com.andrewcomputsci.pathfinderfx.Model.Message;
import com.andrewcomputsci.pathfinderfx.Model.Statistics;
import com.andrewcomputsci.pathfinderfx.Solver.PathFinderSolver;
import com.andrewcomputsci.pathfinderfx.Utils.AlgorithmFactory;
import com.andrewcomputsci.pathfinderfx.Utils.GridUtils;
import com.andrewcomputsci.pathfinderfx.view.CellRectangle;
import com.andrewcomputsci.pathfinderfx.view.PathFinderVisualizer;
import com.andrewcomputsci.pathfinderfx.view.SideBar;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GridController {
    private PathFinderVisualizer grid;
    private SideBar sideBar;
    private SimpleBooleanProperty exactMode;
    private ContextMenu settingMenu;
    private ToggleSwitch toggleSwitch;

    private Slider animationRateSlider;

    private boolean editableState;

    private boolean targetPlaced;

    private boolean sourcePlaced;


    private Timeline algorithmAnimator;
    private Statistics lastSet;

    private SimpleBooleanProperty algorithmRunning;

    private ConcurrentLinkedQueue<Message> threadPipe;
    public static final ExecutorService algoExecutor = Executors.newSingleThreadExecutor(); //needs to be closed/stopped when application ends


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
            Button gcButton = new Button("GC");
            gcButton.setOnAction(event -> {
                System.gc();
            });
            gcButton.getStyleClass().add(Styles.ACCENT);
            CustomMenuItem animationItem = new CustomMenuItem(animationRateSlider);
            CustomMenuItem item = new CustomMenuItem(toggleSwitch);
            CustomMenuItem gcMenu = new CustomMenuItem(gcButton);
            gcMenu.setHideOnClick(false);
            item.setHideOnClick(false);
            animationItem.setHideOnClick(false);
            settingMenu = new ContextMenu(item,animationItem,gcMenu);
            addContextMenu();
            addCellTypeListeners();
            initCellControls();
            initTimeLine();
            setUpControlMenuButtons();
            initGridGenButtons();
            initMazeButtons();
            initChangeGridButton();
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
        algorithmRunning = new SimpleBooleanProperty(false);
    }

    private void addCellTypeListeners(){

        for(CellRectangle rect : grid.getCellGrid()){
            rect.getInnerCell().typeProperty().addListener((observable, oldValue, newValue) -> {
                if( newValue.equals(CellType.Traversable) && rect.getInnerCell().weightProperty().get() != 0.0){
                    rect.setFill(colorInterpolate(rect.getInnerCell().weightProperty().get()));
                }else {
                    rect.setFill(newValue.getColor());
                }
            });
            rect.getInnerCell().stateProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue.equals(CellState.Unvisited)){
                    if(rect.getInnerCell().weightProperty().get() == 0.0)
                        rect.setFill(rect.getInnerCell().getType().getColor());
                    else
                        rect.setFill(colorInterpolate(rect.getInnerCell().weightProperty().get()));
                }else {
                    rect.setFill(newValue.getColor());
                }
            });
            rect.getInnerCell().weightProperty().addListener((observable, oldValue, newValue) -> {
                //the weight will be between 0 and 1, so we just create a linear gradient between two colors
                // using the weight as the present cut-off
                //100% = 16 luminance, 0% = 100% luminance
                // x = 1, y = 16 // x = 0  y = 100
                // y = mx+b
                // y = 100 (0)(m)
                // y = 100 + (m)(1) = 16  -> m = -84
                if(newValue.doubleValue() != 0.0 || !rect.getInnerCell().typeProperty().get().equals(CellType.Traversable)) rect.setFill(colorInterpolate(newValue.doubleValue()));
                else{
                    rect.setFill(rect.getInnerCell().getType().getColor());
                }
            });
        }
    }
    private static Color colorInterpolate(double weight){
        double value = (100 + weight*-75);
        return Color.hsb(195,51/100.0,value/100.0);
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
                if(!editableState){
                    return;
                }
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
        if(rect.getInnerCell().typeProperty().get().equals(CellType.Source) && !type.equals(CellType.Source)){
            sourcePlaced = false;
        }
        else if(rect.getInnerCell().typeProperty().get().equals(CellType.Target) && !type.equals(CellType.Target)){
            targetPlaced = false;
        }
        sourcePlaced = type.equals(CellType.Source) || sourcePlaced;
        targetPlaced = type.equals(CellType.Target) || targetPlaced;
        rect.getInnerCell().typeProperty().set(type);
    }

    private void initTimeLine(){
        algorithmAnimator = new Timeline();
        algorithmAnimator.rateProperty().bind(animationRateSlider.valueProperty());
        algorithmAnimator.statusProperty().addListener((observable, oldValue, newValue) -> {
            //add code here for when the animator stops or pauses
            //two conditions either user skipped the animation or the animation finished
            //if passed it was skipped
            //if stopped animation ran its course
            if(newValue.equals(Animation.Status.PAUSED)){
                while (algorithmRunning.get() || !threadPipe.isEmpty()){
                    Message message = threadPipe.poll();
                    if(message==null){
                        continue;
                    }
                    message.getCellToBeChanged().getInnerCell().stateProperty().set(message.getNewType());
                }
                drawPath(lastSet.path());
            }
            else if(newValue.equals(Animation.Status.STOPPED) && !algorithmRunning.get()){
                drawPath(lastSet.path());
            }

        });
        algorithmAnimator.getKeyFrames().setAll(new KeyFrame(Duration.millis(50),(action)->{
            System.out.println("Playing keyframe");
        }));
        algorithmAnimator.setCycleCount(Animation.INDEFINITE);
    }

    private void setUpControlMenuButtons(){
            sideBar.getStartButton().setOnAction(event -> {
                if(!algorithmRunning.get() && !algorithmAnimator.getStatus().equals(Animation.Status.RUNNING) && targetPlaced && sourcePlaced){
                    GridUtils.initPathFind(grid.getCellGrid());
                    editableState = false;
                    System.out.println("[DEBUG] -- grabbing path finder");
                    PathFinderSolver solver = AlgorithmFactory.getPathFinder(sideBar.getAlgorithmSelectionBox().getValue(),sideBar.getHeuristicSelectionBox().getValue(),exactMode.get());
                    if(solver == null){
                        editableState = true;
                        System.out.println("[DEBUG] -- Method not supported yet");
                        return;
                    }
                    threadPipe = new ConcurrentLinkedQueue<>();
                    Task<Statistics> task = new Task<>() {
                        @Override
                        protected Statistics call() throws Exception {
                            return solver.solve(grid.getCellGrid(), grid.getWidth(), grid.getHeight(), threadPipe);
                        }
                    };
                    task.setOnScheduled(workerEvent -> {
                        algorithmRunning.set(true);
                    });
                    task.setOnRunning(workerEvent ->{
                        algorithmAnimator.play();
                    });
                    task.setOnSucceeded(workerEvent ->{
                        System.out.println("[DEBUG] -- Algorithm Finished");
                        algorithmRunning.set(false);
                        lastSet = task.getValue();
                        fillStatPage();
                    });
                    algorithmAnimator.getKeyFrames().setAll(new KeyFrame(Duration.millis(1000/60.0), actionEvent ->{
                        if(algorithmRunning.get() || !threadPipe.isEmpty()){
                            if(threadPipe.isEmpty()){
                                return;
                            }
                            Message message = threadPipe.poll();
                            message.getCellToBeChanged().getInnerCell().stateProperty().set(message.getNewType());
                        }else{
                            algorithmAnimator.stop();
                        }
                    }));
                    algoExecutor.execute(task);
                }
            });

            sideBar.getClearButton().setOnAction(event -> {
                System.out.println("[DEBUG] -- Clear Button Pressed");

                if(editableState){
                    System.out.println("[DEBUG] -- Clearing Grid");
                    for(CellRectangle rect: grid.getCellGrid()){
                        rect.getInnerCell().typeProperty().set(CellType.Traversable);
                        rect.getInnerCell().stateProperty().set(CellState.Unvisited);
                        rect.getInnerCell().weightProperty().set(0.0);
                    }
                    targetPlaced = false;
                    sourcePlaced = false;
                }
            });

            sideBar.getExitButton().setOnAction(event -> {
                if(!editableState && algorithmAnimator.getStatus().equals(Animation.Status.RUNNING)){
                    algorithmAnimator.pause();
                }
            });
    }

    private void drawPath(List<CellRectangle> path){
        if(path == null){
            editableState = true;
            return;
        }
        System.out.println("[DEBUG] -- Drawling solution Path");
        Timeline pathLine = new Timeline(new KeyFrame(Duration.millis(50),(action)->{
            CellRectangle rect = path.remove(0);
            rect.getInnerCell().stateProperty().set(CellState.Path);
        }));
        pathLine.rateProperty().bind(animationRateSlider.valueProperty());
        pathLine.setCycleCount(path.size());
        pathLine.setOnFinished((action)->{
            editableState = true;
            System.out.println("[DEBUG] -- Finished Drawling Path");
            pathLine.rateProperty().unbind();
        });
        pathLine.setAutoReverse(false);
        pathLine.play();
    }

    private void fillStatPage(){
        System.out.println("[DEBUG] -- LAST SET: " + lastSet);
        sideBar.getDeltaTime().setText(String.format("%3.6fms",lastSet.deltaTime()/(1E6)));
        sideBar.getPathFound().setText(lastSet.path()!=null?"True":"False");
        sideBar.getPathCost().setText(String.format("%.2f",lastSet.pathCost()));
        sideBar.getIterations().setText(String.valueOf(lastSet.passes()));
    }

    private void initGridGenButtons(){
        sideBar.getAddRandomWeights().setOnAction(event -> {
            Random random = new Random();
            if(editableState){
                for(CellRectangle rect: grid.getCellGrid()){
                    rect.getInnerCell().weightProperty().set(random.nextDouble(0.05,1.00));
                    System.out.println("[DEBUG] -- rect weight: " + rect.getInnerCell().getWeight());
                }
            }
        });
        sideBar.getAddRandomWalls().setOnAction(event -> {
            Random random = new Random();
            for (CellRectangle rectangle: grid.getCellGrid()){
                if(random.nextInt(0,Integer.MAX_VALUE)%8 == 0){
                    rectangle.getInnerCell().typeProperty().set(CellType.Wall);
                }
            }
        });
    }

    private void initMazeButtons(){
        sideBar.getMazeGenButton().setOnAction(event -> {
            System.out.println("Button Pressed");
            if(editableState) {
                editableState = false;
                MazeGenerator gen = AlgorithmFactory.getMazeGenerator(sideBar.getMazeTypeComboBox().getValue());
                GridUtils.initMaze(grid.getCellGrid());
                ConcurrentLinkedQueue<Message> queue = new ConcurrentLinkedQueue<>();
                Task<Object> task = new Task<>() {
                    @Override
                    protected Object call() throws Exception {
                        gen.generateMaze(grid.getCellGrid(),grid.getWidth(),grid.getHeight(),queue);
                        return null;
                    }
                };
                Timeline line = new Timeline();
                 line.getKeyFrames().setAll(new KeyFrame(Duration.millis(10),action -> {
                     if (!queue.isEmpty()){
                         Message message = queue.poll();
                         CellRectangle changed = message.getCellToBeChanged();
                         if(message.getCellType() != null){
                             changed.getInnerCell().typeProperty().set(message.getCellType());
                         }else{
                             changed.getInnerCell().stateProperty().set(message.getNewType());
                         }
                     }
                     else{
                        line.stop();
                     }
                 }));
                line.setAutoReverse(false);
                line.setCycleCount(Animation.INDEFINITE);
                task.setOnRunning(event1 -> {
                    line.playFromStart();
                });
                task.setOnSucceeded(event1 -> {
                    editableState = true;
                });
                task.setOnFailed(event1 -> {
                    editableState = true;
                });
                algoExecutor.execute(task);
            }
        });
    }

    private void initChangeGridButton(){
        ValidationSupport validationSupport = new ValidationSupport();
        Validator<String> validator = (control, s) -> {
            boolean condition = false;
            if(s == null) return ValidationResult.fromError(control,"Null value");
            condition = s.matches("^[0-9]+,[0-9]+$");
            return ValidationResult.fromErrorIf(control,"Bad string provided",!condition);
        };
        validationSupport.registerValidator(sideBar.getGridSizeTextField(),validator);
        sideBar.getChangeGridSizeButton().setOnAction(event -> {
            if(validationSupport.isInvalid()){
                System.out.println("[DEBUG] -- invalid string provided");
                return;
            }
            String[] nums = sideBar.getGridSizeTextField().getText().split(",");
            grid.changeGridDimension(Integer.parseInt(nums[0]),Integer.parseInt(nums[1]));
            addCellTypeListeners();
            initCellControls();
            sideBar.getClearButton().fire();
        });
    }
}

