package service.impl;

import model.Folder;
import model.Size;
import model.impl.DefaultFolder;
import model.impl.DefaultMultiFolder;
import service.FolderGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FolderGeneratorImpl implements FolderGenerator {

    private int numberOfDefaultFolders;
    private int numberOfDefaultMultiFolders;
    private int numberOfSmallFolders;
    private int numberOfMediumFolders;
    private int numberOfLargeFolders;

    @Override
    public List<Folder> generateFolders(int numberOfFolders, int randomNumber) {
        List<Folder> folders = new ArrayList<>();
        for (int i = 0; i < numberOfFolders; i++) {
            if (getRandomBoolean()) {
                folders.add(new DefaultFolder("Random " + i, getRandomSize()));
                numberOfDefaultFolders++;
            } else {
                DefaultMultiFolder multiFolder = new DefaultMultiFolder("Random " + i, getRandomSize());
                numberOfDefaultMultiFolders++;
                for (int j = 0; j < getRandomInt(randomNumber); j++) {
                    if (getRandomBoolean()) {
                        multiFolder.getFolders().add(new DefaultFolder("Random " + i + " " + j, getRandomSize()));
                        numberOfDefaultFolders++;
                    } else {
                        DefaultMultiFolder multiFolder1 = new DefaultMultiFolder("Random " + i + " " + j, getRandomSize());
                        numberOfDefaultMultiFolders++;

                        for (int k = 0; k < getRandomInt(randomNumber); k++) {
                            multiFolder1.getFolders().add(new DefaultFolder("Random " + i + " " + j + " " + k, getRandomSize()));
                            numberOfDefaultFolders++;
                        }

                        multiFolder.getFolders().add(multiFolder1);
                    }
                }
                folders.add(multiFolder);
            }
        }
        return folders;
    }

    @Override
    public boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    @Override
    public int getRandomInt(int max) {
        Random random = new Random();
        return random.nextInt(max) + 1;
    }

    private Size getRandomSize() {
        Size size = getRandomBoolean() ? Size.LARGE : getRandomBoolean() ? Size.MEDIUM : Size.SMALL;
        if (size == Size.SMALL) numberOfSmallFolders++;
        if (size ==  Size.MEDIUM) numberOfMediumFolders++;
        if (size == Size.LARGE) numberOfLargeFolders++;
        return size;
    }

    public int getNumberOfDefaultFolders() {
        return numberOfDefaultFolders;
    }
    public int getNumberOfDefaultMultiFolders() {
        return numberOfDefaultMultiFolders;
    }
    public int getNumberOfSmallFolders() {
        return numberOfSmallFolders;
    }
    public int getNumberOfMediumFolders() {
        return numberOfMediumFolders;
    }
    public int getNumberOfLargeFolders() {
        return numberOfLargeFolders;
    }

    @Override
    public void displayMetrics() {
        System.out.println("Number of default folders: " + numberOfDefaultFolders);
        System.out.println("Number of default multi folders: " + numberOfDefaultMultiFolders);
        System.out.println("Number of ALL folders: " + (numberOfDefaultFolders + numberOfDefaultMultiFolders));
        System.out.println("Number of small folders: " + numberOfSmallFolders);
        System.out.println("Number of medium folders: " + numberOfMediumFolders);
        System.out.println("Number of large folders: " + numberOfLargeFolders);
    }
}
