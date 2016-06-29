package org.rib.events;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.rib.Main;
import org.rib.domain.TaskDomain;

import java.util.*;

/**
 * Created by rib on 24.06.2016.
 */
public class AggregateButtonHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {

        TableView<TaskDomain> view = Main.getView();
        List<TaskDomain> procList = Main.getView().getItems();
        Map<String, TaskDomain> taskMap = new HashMap<>();
        TaskDomain mapTask;
        for (TaskDomain task : procList) {
            mapTask = taskMap.get(task.getName());
            if ((mapTask == null) || (mapTask.getMemoryUsage() == null)) {
                task.setPid(null);
                taskMap.put(task.getName(), task);
            } else {
                if (mapTask.getMemoryUsage() == null){
                    taskMap.put(task.getName(), task);
                } else {
                    mapTask.setMemoryUsage(mapTask.getMemoryUsage() + task.getMemoryUsage());
                }
            }
        }

        Main.setAggregateTableView(new ArrayList<>(taskMap.values()));
    }
}
