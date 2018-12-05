package com.qiyue.tagfilter.model;

/**
 * Description:
 * Data：2018\11\28 0028-9:20
 * Author: zgl
 */
public class ComTagFilterData implements ITagFilterViewData {
    private String name;//描述
    private String key;//值

    public ComTagFilterData() {
    }

    public ComTagFilterData(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTagFilterLabelViewText() {
        return name;
    }

    @Override
    public String getTagFilterLabelViewValue() {
        return key;
    }
}
