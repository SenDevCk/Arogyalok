package com.rkvit.arogyalok.Filter;

import java.util.List;

public class DocFilter {
    public static Integer INDEX_Fees = 0;
    public static Integer INDEX_Exper = 1;
    public static Integer INDEX_City = 2;
    public static Integer INDEX_Spec = 3;
////    public static Integer INDEX_BuildingType = 2;
//    public static Integer INDEX_STATUS = 2;
//    public static Integer INDEX_CLIENTTYPE = 3;

    private String name;
    private List<String> values;
    private List<String> selected;

    public DocFilter(String name, List<String> values, List<String> selected) {
        this.name = name;
        this.values = values;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<String> getSelected() {
        return selected;
    }

    public void setSelected(List<String> selected) {
        this.selected = selected;
    }
}