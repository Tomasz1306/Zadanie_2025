package service.impl;

import model.Folder;
import model.Size;
import model.impl.DefaultFolder;
import model.impl.DefaultMultiFolder;
import service.Cabinet;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FileCabinet implements Cabinet {

    private int counter;
    private final List<Folder> folders = new ArrayList<>();

    {
        counter = 0;
    }

    public FileCabinet() {

    this.generateData(15000,300,200);
//    for (Folder folder : folders) {
//        System.out.println(folder);
//    }
        String name = "Random 10000";

        Instant start = Instant.now();
        Optional<Folder> findedFolder = findFolderByName(name);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println(findedFolder.isPresent() ? "Znaleziono: " + findedFolder.get().getName() : "Nie znaleziono: " + name);
        System.out.println("CZAS: " + duration.toSeconds() + "s " + duration.toMillis() + "ms");

    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        Predicate<Folder> predicate = s -> s.getName().equals(name);
        return findOneWithBFS(predicate);
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        Predicate<Folder> predicate = s -> s.getSize().equals(size);
        return findAllWithBFS(predicate);
    }

    private Optional<Folder> findOneWithBFS(Predicate<Folder> predicate) {
        return getFoldersWithBFS().filter(predicate).findFirst();
    }

    private List<Folder> findAllWithBFS(Predicate<Folder> predicate) {
        return getFoldersWithBFS().filter(predicate).toList();
    }

    private Stream<Folder> getFoldersWithBFS() {
        Deque<Folder> deque = new ArrayDeque<>(this.folders);
        List<Folder> collectedFolders = new LinkedList<>();
        while(!deque.isEmpty()) {
            Folder folder = deque.removeFirst();
            collectedFolders.add(folder);
            if (folder instanceof DefaultMultiFolder) {
                ((DefaultMultiFolder) folder).getFolders().forEach(deque::push);
            }
        }
        return collectedFolders.stream();
    }

//    @Override
//    public List<Folder> findFoldersBySize(String size) {
//        List<Folder> foldersWithSize = new ArrayList<>();
//        for (Folder folder: folders) {
//            searchFolderWithSize(folder, size, foldersWithSize);
//        }
//        return foldersWithSize;
//    }

    @Override
    public int count() {
        int localCounter = 0;
        for (Folder folder: folders) {
            this.counter++;
            countFolders(folder);
        };
        localCounter = this.counter;
        this.counter = 0;
        return localCounter;
    }

    private void countFolders(Folder folder) {
        if (folder instanceof DefaultMultiFolder) {
            DefaultMultiFolder searchFolder = (DefaultMultiFolder) folder;
            for (Folder subFolder : searchFolder.getFolders()) {
                this.counter++;
                countFolders(subFolder) ;
            }
        }
    }

    private Folder searchFolderByNameWithRecursion(Folder folder, String name) {
        if (folder == null) {
            return null;
        }

        if (folder instanceof DefaultMultiFolder) {

            DefaultMultiFolder searchFolder = (DefaultMultiFolder) folder;

            if (searchFolder.getName().equals(name)) {
                return searchFolder;
            }

            for (Folder subFolder : searchFolder.getFolders()) {
                Folder returnedFromSubFolder = searchFolderByNameWithRecursion(subFolder, name);
                if (returnedFromSubFolder != null) {
                    return returnedFromSubFolder;
                }
            }
        }

        if (folder instanceof DefaultFolder) {
            if (folder.getName().equals(name)) {
                return folder;
            }
        }
        return null;
    }

    private void searchFolderBySizeWithRecursion(Folder folder, String size, List<Folder> folders) {
        if (folder instanceof DefaultMultiFolder) {

            DefaultMultiFolder searchFolder = (DefaultMultiFolder) folder;

            if (searchFolder.getSize().equals(size)) {
                folders.add(folder);
            }

            for (Folder subFolder : searchFolder.getFolders()) {
                searchFolderBySizeWithRecursion(subFolder, size, folders);
            }
        }

        if (folder instanceof DefaultFolder) {
            if (folder.getSize().equals(size)) {
                folders.add(folder);
            }
        }
    }

    private void generateData(int number, int maxNumberOfNestedFolder, int maxOfNest) {
        int numberOfFolders = 0;
        for (int i = 0; i < number; i++) {
            if (getRandomBoolean()) {
                Size size = getRandomBoolean() ? Size.LARGE : getRandomBoolean() ? Size.MEDIUM : Size.SMALL;
                this.folders.add(new DefaultFolder("Random " + i, size));
                numberOfFolders++;
            } else {
                Size size = getRandomBoolean() ? Size.LARGE : getRandomBoolean() ? Size.MEDIUM : Size.SMALL;
                DefaultMultiFolder multiFolder = new DefaultMultiFolder("Random " + i, size);
                numberOfFolders++;
                for (int j = 0; j < getRandomOfNestedFolders(maxNumberOfNestedFolder); j++) {
                    if (getRandomBoolean()) {
                        Size size1 = getRandomBoolean() ? Size.LARGE : getRandomBoolean() ? Size.MEDIUM : Size.SMALL;
                        multiFolder.getFolders().add(new DefaultFolder("Random " + i + " " + j, size));
                        numberOfFolders++;
                    } else {
                        Size size1 = getRandomBoolean() ? Size.LARGE : getRandomBoolean() ? Size.MEDIUM : Size.SMALL;
                        DefaultMultiFolder multiFolder1 = new DefaultMultiFolder("Random " + i + " " + j, size1);
                        numberOfFolders++;

                        for (int k = 0; k < getRandomOfNest(maxOfNest); k++) {
                            Size size2 = getRandomBoolean() ? Size.LARGE : getRandomBoolean() ? Size.MEDIUM : Size.SMALL;
                            multiFolder1.getFolders().add(new DefaultFolder("Random " + i + " " + j + " " + k, size));
                            numberOfFolders++;
                        }

                        multiFolder.getFolders().add(multiFolder1);
                    }

                }
                this.folders.add(multiFolder);
            }
        }
        System.out.println("Number of Folders: " + numberOfFolders);
    }

    private boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    private int getRandomOfNestedFolders(int maxNumberOfNestedFolder) {
        Random random = new Random();
        return random.nextInt(maxNumberOfNestedFolder) + 1;
    }

    private int getRandomOfNest(int randomOfNest) {
        Random random = new Random();
        return random.nextInt(randomOfNest) + 1;
    }
}
