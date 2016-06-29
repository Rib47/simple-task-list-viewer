package org.rib.events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import org.rib.Main;
import org.rib.domain.TaskDomain;
import org.rib.domain.TaskList;
import org.rib.services.ExcelService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

/**
 * Created by rib on 24.06.2016.
 */
public class ExportButtonHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export to Excel File");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx")
        );
        File file = fileChooser.showSaveDialog(Main.getStage());

        List<TaskDomain> tasks = Main.getView().getItems();
        ExcelService service = new ExcelService();
        service.writeToFile(file, tasks);
    }
}
