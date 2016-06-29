package org.rib.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Created by rib on 24.06.2016.
 */
@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskDomain {

    private Integer pid;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "memory")
    private Integer memoryUsage;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(Integer memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDomain that = (TaskDomain) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(pid, that.pid) &&
                Objects.equals(memoryUsage, that.memoryUsage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pid, memoryUsage);
    }

    @Override
    public String toString() {
        return "TaskDomain{" +
                "name='" + name + '\'' +
                ", pid=" + pid +
                ", memoryUsage=" + memoryUsage +
                '}';
    }
}


