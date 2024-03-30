package com.rkvit.arogyalok.Filter;

import java.util.List;

public class BloodFilter {
    public static Integer INDEX_City = 0;

    private String name;
    private List<String> values;
    private List<String> selected;

    public BloodFilter(String name, List<String> values, List<String> selected) {
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