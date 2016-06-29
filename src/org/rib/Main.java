package org.rib;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import org.rib.domain.TaskCompareDomain;
import org.rib.domain.TaskDomain;
import org.rib.domain.TaskList;
import org.rib.events.*;

import java.util.List;

public class Main extends Application {

    private static TableView<TaskDomain> view;
    private static TableView<TaskCompareDomain> compareView;
    private static Button loadBtn;
    private static Button aggregateBtn;
    private static Button saveBtn;
    private static Button exportBtn;
    private static Button compareBtn;
    private static Button clearBtn;

    private static TaskList taskList;
    private static Stage stage;



    @Override
    public void start(Stage primaryStage) throws Exception{

        loadBtn = new Button("Load tasks");
        loadBtn.setOnAction(new LoadButtonHandler());

        aggregateBtn = new Button("Aggregate");
        aggregateBtn.setOnAction(new AggregateButtonHandler());

        saveBtn = new Button("Save list");
        saveBtn.setOnAction(new SaveButtonHandler());

        compareBtn = new Button("Compare");
        compareBtn.setOnAction(new CompareButtonHandler());

        exportBtn = new Button("Export to Excel");
        exportBtn.setOnAction(new ExportButtonHandler());

        clearBtn = new Button("Clear list");
        clearBtn.setOnAction(new ClearButtonHandler());

        setActiveButtons(true, false, false, false, false, false);

        GridPane root = new GridPane();
        root.setHgap(8);
        root.setVgap(8);
        root.setPadding(new Insets(5));

        setupPaneConstraints(root);

        view = new TableView<>();
        view.setVisible(true);
        compareView = new TableView<>();
        view.setVisible(false);

        root.add(view, 0, 0, 6, 10);
        root.add(compareView, 0, 0, 6, 10);
        root.add(loadBtn,       0, 11);
        root.add(aggregateBtn,  1, 11);
        root.add(saveBtn,       2, 11);
        root.add(compareBtn,    3, 11);
        root.add(exportBtn,    4, 11);
        root.add(clearBtn,      5, 11);
        Scene scene = new Scene(root, 550, 700);

        primaryStage.setTitle("Simple Task List Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void setupPaneConstraints(GridPane pane) {
        ColumnConstraints cons1 = new ColumnConstraints();
        cons1.setHgrow(Priority.ALWAYS);
        pane.getColumnConstraints().addAll(cons1);

        RowConstraints rcons1 = new RowConstraints();
        rcons1.setVgrow(Priority.ALWAYS);
        pane.getRowConstraints().addAll(rcons1);
    }

    public static void setFullTableView(List<TaskDomain> taskList) {
        TableColumn pidCol = new TableColumn("PID");
        pidCol.setCellValueFactory(new PropertyValueFactory("pid"));

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        nameCol.setSortType(TableColumn.SortType.ASCENDING);

        TableColumn memCol = new TableColumn("Memory usage");
        memCol.setCellValueFactory(new PropertyValueFactory("memoryUsage"));

        ObservableList<TaskDomain> items = FXCollections.observableList(taskList);
        view.getColumns().setAll(pidCol, nameCol, memCol);
        view.setItems(items);
        view.getSortOrder().setAll(nameCol);

        Main.setActiveButtons(true, true, false, false, true, true);
        showView();
    }

    public static void setAggregateTableView(List<TaskDomain> taskList) {
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        nameCol.setSortType(TableColumn.SortType.ASCENDING);

        TableColumn memCol = new TableColumn("Memory usage");
        memCol.setCellValueFactory(new PropertyValueFactory("memoryUsage"));

        ObservableList<TaskDomain> items = FXCollections.observableList(taskList);
        view.getColumns().setAll(nameCol, memCol);
        view.setItems(items);
        view.getSortOrder().setAll(nameCol);

        Main.setActiveButtons(true, false, true, true, true, true);
        showView();
    }

    public static void setCompareTableView(List<TaskCompareDomain> taskList) {
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<TaskCompareDomain, String>("name"));
        nameCol.setSortType(TableColumn.SortType.ASCENDING);

        TableColumn memCol = new TableColumn("Memory (current)");
        memCol.setCellValueFactory(new PropertyValueFactory<TaskCompareDomain, Integer>("memoryUsage"));
        TableColumn compareMemCol = new TableColumn("Memory usage (compared)");
        compareMemCol.setCellValueFactory(new PropertyValueFactory<TaskCompareDomain, Integer>("memoryToCompare"));
        TableColumn diffCol = new TableColumn("Difference");
        diffCol.setCellValueFactory(new PropertyValueFactory<TaskCompareDomain, Integer>("memoryDiff"));

        ObservableList<TaskCompareDomain> items = FXCollections.observableList(taskList);
        compareView.getColumns().setAll(nameCol, memCol, compareMemCol, diffCol);
        compareView.setItems(items);
        compareView.getSortOrder().setAll(nameCol);

        Main.setActiveButtons(true, false, false, true, true, false);
        showCompareView();
    }

    public static void clearTableView() {
        view.getColumns().clear();
        view.getItems().clear();
        setActiveButtons(true, false, false, false, false, false);
        showView();
        compareView.getColumns().clear();
        compareView.getItems().clear();
    }

    private static void setActiveButtons(boolean loadFlag, boolean aggregateFlag, boolean saveFlag,
                                        boolean compareFlag, boolean clearFlag, boolean exportFlag) {
        loadBtn.setDisable(!loadFlag);
        aggregateBtn.setDisable(!aggregateFlag);
        saveBtn.setDisable(!saveFlag);
        compareBtn.setDisable(!compareFlag);
        clearBtn.setDisable(!clearFlag);
        exportBtn.setDisable(!exportFlag);
    }

    private static void showView() {
        view.setVisible(true);
        compareView.setVisible(false);
    }

    private static void showCompareView() {
        view.setVisible(false);
        compareView.setVisible(true);
    }

    public static void main(String[] args) {
        launch(args);
    }


    public static TableView<TaskDomain> getView() {
        return view;
    }

    public static Stage getStage() {
        return stage;
    }
}
