package model.impl;

import model.Folder;
import model.Size;

public class DefaultFolder implements Folder {

    private String name;
    private Size size;

    public DefaultFolder(String name, Size size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSize() {
        return size.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "name=" + name + ", size=" + size;
    }
}
