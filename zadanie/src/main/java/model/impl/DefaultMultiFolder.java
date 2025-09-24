package model.impl;

import model.Folder;
import model.MultiFolder;
import model.Size;

import java.util.ArrayList;
import java.util.List;

public class DefaultMultiFolder implements MultiFolder {

    private String name;
    private Size size;
    private final List<Folder> subFolders = new ArrayList<>();

    public DefaultMultiFolder(String name, Size size, List<Folder> subFolders) {
        this.name = name;
        this.size = size;
        this.subFolders.addAll(subFolders);
    }

    public DefaultMultiFolder(String name, Size size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public List<Folder> getFolders() {
        return subFolders;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSize() {
        return size.name();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void addFolder(Folder folder) {
        subFolders.add(folder);
    }

    @Override
    public String toString() {
        return "name " + this.name + ", size " + this.size + ", folders " + this.subFolders;
    }
}
