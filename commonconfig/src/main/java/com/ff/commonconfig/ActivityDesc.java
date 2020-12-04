package com.ff.commonconfig;

public class ActivityDesc {
    public int id;
    public String name;
    public String path;
    public String desc;
    public String title;

    public ActivityDesc(int id, String name, String path, String title,String desc) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.title = title;
        this.desc = desc;
    }
}
