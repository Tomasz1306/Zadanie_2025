package service.impl;

import model.Folder;
import model.MultiFolder;
import model.Size;
import model.impl.DefaultMultiFolder;
import service.Cabinet;
import service.FolderGenerator;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FileCabinet implements Cabinet {

    private final List<Folder> folders = new ArrayList<>();

    public FileCabinet() {

        FolderGenerator folderGenerator = new FolderGeneratorImpl();
        this.folders.addAll(folderGenerator.generateFolders(500, 10));
        folderGenerator.displayMetrics();
        String name = "Random 420 2 1";

        runExampleFindByName(name);
        runExampleFindBySize();
        System.out.println("Number of folders: " + count());
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
            if (folder instanceof MultiFolder) {
                ((MultiFolder) folder).getFolders().forEach(deque::addFirst);
            }
        }
        return collectedFolders.stream();
    }

    @Override
    public int count() {
        return (int) getFoldersWithBFS().count();
    }

    private Optional<Folder> findByNameRecursion(Folder folder, String name) {
        if (folder == null) return Optional.empty();
        if (folder.getName().equals(name)) {
            return Optional.of(folder);
        }
        if (folder instanceof MultiFolder) {
            for (Folder subFolder : ((DefaultMultiFolder) folder).getFolders()) {
                Optional<Folder> returnedFromSubFolder = findByNameRecursion(subFolder, name);
                if (returnedFromSubFolder.isPresent()) {
                    return returnedFromSubFolder;
                }
            }
        }

        return Optional.empty();
    }

    private void findBySizeRecursion(Folder folder, String size, List<Folder> folders) {
        if (folder == null) return;
        if (folder instanceof MultiFolder) {
            for (Folder subFolder : ((DefaultMultiFolder) folder).getFolders()) {
                findBySizeRecursion(subFolder, size, folders);
            }
        }
        if (folder.getSize().equals(size)) {
            folders.add(folder);
        }
    }

    private void runExampleFindByName(String name) {
        System.out.println("=========== FIND BY NAME ===========");
        Instant start = Instant.now();
        Optional<Folder> findedFolder = findFolderByName(name);
        Instant end = Instant.now();
        double milis = Duration.between(start, end).toNanos() / 1_000_000;
        System.out.println(findedFolder.isPresent() ? "Find: " + findedFolder.get().getName() : "not find: " + name);
        System.out.println("TIME: " + milis + " ms");

        Optional<Folder> folderByName = Optional.empty();
        start = Instant.now();
        for (Folder f : folders) {
            folderByName = findByNameRecursion(f, name);
            if (folderByName.isPresent()) {
                break;
            }
        }
        end = Instant.now();
        milis = Duration.between(start, end).toNanos() / 1_000_000;
        System.out.println(folderByName.isPresent() ? "(RECURSION) Find: " + folderByName.get().getName() : "not find: " + name);
        System.out.println("TIME: " + milis + "ms");
    }

    private void runExampleFindBySize() {
        for (var size : Size.values()) {
            System.out.println("=========== FIND BY SIZE " + size + "===========");
            Instant start = Instant.now();
            List<Folder> foldersBFS = findFoldersBySize(size.name());
            Instant end = Instant.now();
            double milis = (double) Duration.between(start, end).toNanos() / 1_000_000;
            System.out.println("NUMBER OF " + size + " FOLDERS: "  + foldersBFS.size());
            System.out.println("TIME: " + milis + " ms");

            List<Folder> foldersByRecursion = new ArrayList<>();
            start = Instant.now();
            for (Folder f : this.folders) {
                findBySizeRecursion(f, size.name(), foldersByRecursion);
            }
            end = Instant.now();
            milis = (double) Duration.between(start, end).toNanos() / 1_000_000;
            System.out.println("(RECURSION) NUMBER OF " + size + " FOLDERS: "  + foldersByRecursion.size());
            System.out.println("(RECURSION) TIME: " + milis + " ms");
        }
        System.out.println("=========== ========== ===========");
    }
}
