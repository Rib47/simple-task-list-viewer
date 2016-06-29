package org.rib.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rib on 28.06.2016.
 */

@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskList {

    @XmlElement(name = "task")
    private List<TaskDomain> taskList;

    public TaskList() {
    }

    public TaskList(List<TaskDomain> taskList) {
        List<TaskDomain> listCopy = new ArrayList<>();
        for (TaskDomain task : taskList) {
            listCopy.add(task);
        }
        this.taskList = listCopy;
    }

    public List<TaskDomain> getTaskList() {
        return taskList;
    }
}
