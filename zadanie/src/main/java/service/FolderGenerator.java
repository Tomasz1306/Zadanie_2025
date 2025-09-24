package service;

import model.Folder;

import java.util.List;

public interface FolderGenerator {
    List<Folder> generateFolders(int numberOfFolders, int randomNumber);
    boolean getRandomBoolean();
    int getRandomInt(int max);
    void displayMetrics();
}
