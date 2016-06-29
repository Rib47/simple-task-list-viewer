package org.rib.events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import org.rib.Main;
import org.rib.domain.TaskCompareDomain;
import org.rib.domain.TaskDomain;
import org.rib.domain.TaskList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rib on 24.06.2016.
 */
public class CompareButtonHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Resource File");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );

        List<TaskDomain> currTasks = Main.getView().getItems();
        List<TaskDomain> loadedTasks = null;
        try {
            File file = fileChooser.showOpenDialog(Main.getStage());

            JAXBContext jaxbContext = JAXBContext.newInstance(TaskList.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            TaskList taskList = (TaskList) jaxbUnmarshaller.unmarshal(file);

            loadedTasks  = taskList.getTaskList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<TaskCompareDomain> compareTaskList = new ArrayList<>();

        Map<String, Integer> currTaskMap = getNameToMemoryMap(currTasks);
        Map<String, Integer> loadedTaskMap = getNameToMemoryMap(loadedTasks);
        Set<String> namesSet = getAllKeysSet(currTaskMap, loadedTaskMap);

        for (String name : namesSet) {
            Integer memUsage = currTaskMap.get(name);
            Integer memToCompare = loadedTaskMap.get(name);
            Integer memDiff = (memUsage != null && memToCompare != null)
                    ? (memUsage - memToCompare)
                    : null;

            TaskCompareDomain compareTask = new TaskCompareDomain();
            compareTask.setName(name);
            compareTask.setMemoryUsage(currTaskMap.get(name));
            compareTask.setMemoryToCompare(loadedTaskMap.get(name));
            compareTask.setMemoryDiff(memDiff);

            compareTaskList.add(compareTask);
        }

        Main.setCompareTableView(compareTaskList);

    }

    private Map<String, Integer> getNameToMemoryMap(List<TaskDomain> taskList) {
        Map<String, Integer> resultMap =
                taskList.stream().collect(Collectors.toMap(TaskDomain::getName, TaskDomain::getMemoryUsage));
        return resultMap;
    }

    private Set<String> getAllKeysSet(Map<String, Integer> ... taskMaps) {
        Set<String> resultSet = new HashSet<>();
        for (Map<String, Integer> taskMap : taskMaps) {
            resultSet.addAll(taskMap.keySet());
        }
        return resultSet;
    }

}
