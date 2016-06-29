package org.rib.domain;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by rib on 28.06.2016.
 */
public class TaskCompareDomain extends TaskDomain {
    private Integer memoryToCompare;
    private Integer memoryDiff;

    public Integer getMemoryToCompare() {
        return memoryToCompare;
    }

    public void setMemoryToCompare(Integer memoryToCompare) {
        this.memoryToCompare = memoryToCompare;
    }

    public Integer getMemoryDiff() {
        return memoryDiff;
    }

    public void setMemoryDiff(Integer memoryDiff) {
        this.memoryDiff = memoryDiff;
    }
}
