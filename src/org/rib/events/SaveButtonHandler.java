package org.rib.events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import org.rib.Main;
import org.rib.domain.TaskDomain;
import org.rib.domain.TaskList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rib on 24.06.2016.
 */
public class SaveButtonHandler implements EventHandler<ActionEvent> {

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

        List<TaskDomain> tasks = Main.getView().getItems();
        TaskList taskList = new TaskList(tasks);

        try {
            File file = fileChooser.showSaveDialog(Main.getStage());

            JAXBContext jaxbContext = JAXBContext.newInstance(TaskList.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //Marshal the employees list in console
            jaxbMarshaller.marshal(taskList, System.out);

            //Marshal the employees list in file
            jaxbMarshaller.marshal(taskList, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
