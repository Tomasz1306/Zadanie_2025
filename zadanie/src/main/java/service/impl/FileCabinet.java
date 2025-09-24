package service.impl;

import model.Folder;
import model.Size;
import model.impl.DefaultFolder;
import model.impl.DefaultMultiFolder;
import service.Cabinet;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class FileCabinet implements Cabinet {

    private int counter;
    private final List<Folder> folders = new ArrayList<>();

    {
        counter = 0;
    }

    public FileCabinet() {

    this.generateData(15000,3000,200);
//    for (Folder folder : folders) {
//        System.out.println(folder);
//    }

        Instant start1 = Instant.now();
        Optional<Folder> folderA = findFolderByName("Random 10000 59");
        Instant end1 = Instant.now();
        Instant start3 = Instant.now();
        Optional<Folder> folderC = findFolderByName3("Random 10000 59");
        Instant end3 = Instant.now();
        System.out.println("CZAS REKURENCJI: " + Duration.between(start1, end1).toString());
        System.out.println("BFS: " + Duration.between(start3, end3).toString());
        System.out.println(folderA.map(folder -> "Znaleziono: " + folder.getName()).orElse("Nie znaleziono folderu A"));
        System.out.println(folderC.map(folder -> "ZNALEZIONO: " + folder.getName()).orElse("Nie znaleziono folderu C"));
//        findFoldersBySize("LARGE");
//        System.out.println("Number of folders: " + count());
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        for (Folder folder: folders) {
            Folder findedFolder = searchFolderWithName(folder, name);
            if (findedFolder != null) {
                return Optional.of(findedFolder);
            }
        }
        return Optional.empty();
    }

    public Optional<Folder> findFolderByName3(String name) {
        Folder folder = BFS(name);
        if (folder != null) {
            return Optional.of(folder);
        }
        return Optional.empty();
    }

    private Folder BFS(String name) {
        Iterator<Folder> mainIterator = this.folders.iterator();
        Folder source = mainIterator.next();

        Set<String> visited = new HashSet<>();

        ArrayDeque<Folder> queue = new ArrayDeque<>();

        visited.add(source.getName());
        queue.add(source);

        while(!queue.isEmpty()) {
            source = queue.poll();
            if (source.getClass() == DefaultFolder.class) {
                if (source.getName().equals(name)) {
                    return source;
                }
            }

            if (source.getClass() == DefaultMultiFolder.class) {
                if (source.getName().equals(name)) {
                    return source;
                }

                for (Folder n : ((DefaultMultiFolder) source).getFolders()) {
                    if (!visited.contains(n.getName())) {
                        visited.add(n.getName());
                        queue.add(n);
                    }
                }
            }
            if (mainIterator.hasNext()) {
                Folder n = mainIterator.next();
                if (!visited.contains(n.getName())) {
                    visited.add(n.getName());
                    queue.add(n);
                }
            }

        }
        return null;
    }


    @Override
    public List<Folder> findFoldersBySize(String size) {
        List<Folder> foldersWithSize = new ArrayList<>();
        for (Folder folder: folders) {
            searchFolderWithSize(folder, size, foldersWithSize);
        }
        System.out.println(foldersWithSize);
        return foldersWithSize;
    }

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

    private Folder searchFolderWithName(Folder folder, String name) {
        if (folder == null) {
            return null;
        }

        if (folder instanceof DefaultMultiFolder) {

            DefaultMultiFolder searchFolder = (DefaultMultiFolder) folder;

            if (searchFolder.getName().equals(name)) {
                return searchFolder;
            }

            for (Folder subFolder : searchFolder.getFolders()) {
                Folder returnedFromSubFolder = searchFolderWithName(subFolder, name);
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

    private void searchFolderWithSize(Folder folder, String size, List<Folder> folders) {
        if (folder instanceof DefaultMultiFolder) {

            DefaultMultiFolder searchFolder = (DefaultMultiFolder) folder;

            if (searchFolder.getSize().equals(size)) {
                folders.add(folder);
            }

            for (Folder subFolder : searchFolder.getFolders()) {
                System.out.println("Szukam w subFolder: " + subFolder);
                searchFolderWithSize(subFolder, size, folders);
            }
        }

        if (folder instanceof DefaultFolder) {
            if (folder.getSize().equals(size)) {
                System.out.println("Dodaje folder: " + folder.getSize());
                folders.add(folder);
            }
        }
    }

    private void countFolders(Folder folder) {

        System.out.println("CLASS TYPE: " + folder.getClass() + " || " +  folder.getName() + " || ");
        if (folder instanceof DefaultMultiFolder) {

            DefaultMultiFolder searchFolder = (DefaultMultiFolder) folder;
            for (Folder subFolder : searchFolder.getFolders()) {
                this.counter++;
                countFolders(subFolder) ;
            }
        }
    }

    private void generateData(int number, int maxNumberOfNestedFolder, int maxOfNest) {
        for (int i = 0; i < number; i++) {
            if (getRandomBoolean()) {
                Size size = getRandomBoolean() ? Size.LARGE : getRandomBoolean() ? Size.MEDIUM : Size.SMALL;
                this.folders.add(new DefaultFolder("Random " + i, size));
            } else {
                Size size = getRandomBoolean() ? Size.LARGE : getRandomBoolean() ? Size.MEDIUM : Size.SMALL;
                DefaultMultiFolder multiFolder = new DefaultMultiFolder("Random " + i, size);

                for (int j = 0; j < getRandomOfNestedFolders(maxNumberOfNestedFolder); j++) {
                    if (getRandomBoolean()) {
                        Size size1 = getRandomBoolean() ? Size.LARGE : getRandomBoolean() ? Size.MEDIUM : Size.SMALL;
                        multiFolder.getFolders().add(new DefaultFolder("Random " + i + " " + j, size));
                    } else {
                        Size size1 = getRandomBoolean() ? Size.LARGE : getRandomBoolean() ? Size.MEDIUM : Size.SMALL;
                        DefaultMultiFolder multiFolder1 = new DefaultMultiFolder("Random " + i + " " + j, size1);

                        for (int k = 0; k < getRandomOfNest(maxOfNest); k++) {
                            Size size2 = getRandomBoolean() ? Size.LARGE : getRandomBoolean() ? Size.MEDIUM : Size.SMALL;
                            multiFolder1.getFolders().add(new DefaultFolder("Random " + i + " " + j + " " + k, size));
                        }

                        multiFolder.getFolders().add(multiFolder1);
                    }

                }
                this.folders.add(multiFolder);
            }
        }
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
