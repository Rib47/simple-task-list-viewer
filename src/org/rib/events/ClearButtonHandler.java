package org.rib.events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.rib.Main;

/**
 * Created by rib on 24.06.2016.
 */
public class ClearButtonHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        Main.clearTableView();
    }
}
