package org.rib.services;

import org.rib.domain.TaskDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rib on 23.06.2016.
 */

public class ParserService {

    public List<TaskDomain> parseTaskList(List<String> lines) {
        List<List<String>> taskStringsList =  splitTaskLines(lines);
        List<TaskDomain> result = new ArrayList<>();
        for (List<String> row: taskStringsList) {
            TaskDomain process = new TaskDomain();
            process.setName(row.get(0));
            process.setPid(new Integer(row.get(1)));
            String memStr = row.get(4).replaceAll("\\D+", "");
            process.setMemoryUsage(Integer.parseInt(memStr));
            result.add(process);
        }
        return result;
    }

    private List<List<String>> splitTaskLines(List<String> lines) {

        List<List<String>> result = new ArrayList<>();
        List<Integer> sizesList = null;
        for (String s: lines) {
            if (sizesList == null) {
                if (s.contains("===")) {
                    sizesList = detectSizes(s);
                }
            } else {
                List<String> lineList = parseLine(s, sizesList);
                if (lineList != null)
                    result.add(lineList);
            }
        }
        return result;
    }

    private List<Integer> detectSizes(String s) {
        List<Integer> sizes = new ArrayList<>();
        String[] columnMarksArr = s.split(" ");
        for(String str: columnMarksArr) {
            sizes.add(str.length());
        }
        return sizes;
    }

    private List<String> parseLine(String s, List<Integer> sizes) {
        List<String> result = new ArrayList<>();
        int index = 0;

        try {
            for(Integer size: sizes) {
                String value = s.substring(index, index + size).trim();
                result.add(value);
                index += size + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }
}
