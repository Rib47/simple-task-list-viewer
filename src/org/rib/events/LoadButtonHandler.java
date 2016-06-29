package org.rib.events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.rib.Main;
import org.rib.domain.TaskDomain;
import org.rib.services.ParserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rib on 24.06.2016.
 */
public class LoadButtonHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {

        List<String> lines = loadRawTaskLines();

        List<TaskDomain> taskList = (new ParserService()).parseTaskList(lines);

        Main.setFullTableView(taskList);
    }


    private List<String> loadRawTaskLines() {
        List<String> lines = new ArrayList<>();
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "chcp.com 65001 && tasklist.exe");
            Process process = builder.start();

            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);

            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
