package service.impl;

import model.Folder;
import model.Size;
import model.impl.DefaultFolder;
import model.impl.DefaultMultiFolder;
import service.Cabinet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileCabinet implements Cabinet {

    private int counter;
    private final List<Folder> folders = new ArrayList<>();

    {
        counter = 0;
    }

    public FileCabinet() {
        // 3
        folders.add(new DefaultFolder("Folder A", Size.SMALL));
        folders.add(new DefaultFolder("Folder B", Size.SMALL));
        folders.add(new DefaultFolder("Folder C", Size.LARGE));
        // 7
        folders.add(new DefaultMultiFolder(
                "Folder D",
                Size.MEDIUM,
                List.of(
                        new DefaultFolder("Folder D1", Size.SMALL),
                        new DefaultFolder("Folder D2", Size.SMALL),
                        new DefaultMultiFolder(
                                "Folder D3",
                                Size.LARGE,
                                List.of(
                                        new DefaultFolder("Folder D3_A", Size.SMALL),
                                        new DefaultFolder("Folder D3_B", Size.MEDIUM),
                                        new DefaultFolder("Folder D3_C", Size.LARGE)
                                )
                        )
                )
        ));
        // 8
        folders.add(new DefaultMultiFolder(
                "Folder E",
                Size.LARGE,
                List.of(
                        new DefaultFolder("Folder E1", Size.SMALL),
                        new DefaultFolder("Folder E2", Size.SMALL),
                        new DefaultFolder("Folder E3", Size.MEDIUM),
                        new DefaultMultiFolder(
                                "Folder E4",
                                Size.SMALL,
                                List.of(
                                        new DefaultFolder("Folder E4_A", Size.SMALL),
                                        new DefaultFolder("Folder E4_B", Size.MEDIUM),
                                        new DefaultFolder("Folder E4_C", Size.LARGE)
                                )
                        )
                )
        ));
        // 9
        folders.add(new DefaultMultiFolder(
                "Folder F",
                Size.MEDIUM,
                List.of(
                        new DefaultFolder("Folder F1", Size.SMALL),
                        new DefaultMultiFolder(
                                "Folder F2",
                                Size.LARGE,
                                List.of(
                                        new DefaultFolder("Folder F2_A", Size.SMALL),
                                        new DefaultFolder("Folder F2_B", Size.SMALL),
                                        new DefaultFolder("Folder F2_C", Size.MEDIUM),
                                        new DefaultFolder("Folder F2_D", Size.LARGE)
                                )
                        ),
                        new DefaultFolder("Folder F3", Size.MEDIUM),
                        new DefaultFolder("Folder F4", Size.SMALL)
                )
        ));
        // 13
        folders.add(new DefaultMultiFolder(
                "Folder G",
                Size.LARGE,
                List.of(
                        new DefaultFolder("Folder G1", Size.SMALL),
                        new DefaultFolder("Folder G2", Size.MEDIUM),
                        new DefaultMultiFolder(
                                "Folder G3",
                                Size.MEDIUM,
                                List.of(
                                        new DefaultFolder("Folder G3_A", Size.SMALL),
                                        new DefaultFolder("Folder G3_B", Size.SMALL),
                                        new DefaultMultiFolder(
                                                "Folder G3_C",
                                                Size.LARGE,
                                                List.of(
                                                        new DefaultFolder("Folder G3_C1", Size.SMALL),
                                                        new DefaultFolder("Folder G3_C2", Size.MEDIUM),
                                                        new DefaultFolder("Folder G3_C3", Size.SMALL),
                                                        new DefaultFolder("Folder G3_C4", Size.LARGE)
                                                )
                                        )
                                )
                        ),
                        new DefaultFolder("Folder G4", Size.SMALL),
                        new DefaultFolder("Folder G5", Size.MEDIUM)
                )
        ));
        // 9
        folders.add(new DefaultMultiFolder(
                "Folder H",
                Size.SMALL,
                List.of(
                        new DefaultFolder("Folder H1", Size.SMALL),
                        new DefaultFolder("Folder H2", Size.SMALL),
                        new DefaultFolder("Folder H3", Size.MEDIUM),
                        new DefaultFolder("Folder H4", Size.LARGE),
                        new DefaultMultiFolder(
                                "Folder H5",
                                Size.MEDIUM,
                                List.of(
                                        new DefaultFolder("Folder H5_A", Size.SMALL),
                                        new DefaultFolder("Folder H5_B", Size.MEDIUM),
                                        new DefaultFolder("Folder H5_C", Size.LARGE)
                                )
                        )
                )
        ));
        // 14
        folders.add(new DefaultMultiFolder(
                "Folder I",
                Size.LARGE,
                List.of(
                        new DefaultFolder("Folder I1", Size.SMALL),
                        new DefaultFolder("Folder I2", Size.SMALL),
                        new DefaultMultiFolder(
                                "Folder I3",
                                Size.MEDIUM,
                                List.of(
                                        new DefaultFolder("Folder I3_A", Size.SMALL),
                                        new DefaultFolder("Folder I3_B", Size.SMALL),
                                        new DefaultFolder("Folder I3_C", Size.MEDIUM),
                                        new DefaultMultiFolder(
                                                "Folder I3_D",
                                                Size.LARGE,
                                                List.of(
                                                        new DefaultFolder("Folder I3_D1", Size.SMALL),
                                                        new DefaultFolder("Folder I3_D2", Size.MEDIUM),
                                                        new DefaultFolder("Folder I3_D3", Size.LARGE)
                                                )
                                        )
                                )
                        ),
                        new DefaultFolder("Folder I4", Size.SMALL),
                        new DefaultFolder("Folder I5", Size.MEDIUM),
                        new DefaultFolder("Folder I6", Size.LARGE)
                )
        ));
        // 11
        folders.add(new DefaultMultiFolder(
                "Folder J",
                Size.SMALL,
                List.of(
                        new DefaultFolder("Folder J1", Size.SMALL),
                        new DefaultFolder("Folder J2", Size.SMALL),
                        new DefaultFolder("Folder J3", Size.MEDIUM),
                        new DefaultMultiFolder(
                                "Folder J4",
                                Size.LARGE,
                                List.of(
                                        new DefaultFolder("Folder J4_A", Size.SMALL),
                                        new DefaultFolder("Folder J4_B", Size.MEDIUM),
                                        new DefaultFolder("Folder J4_C", Size.LARGE),
                                        new DefaultFolder("Folder J4_D", Size.SMALL)
                                )
                        ),
                        new DefaultFolder("Folder J5", Size.SMALL),
                        new DefaultFolder("Folder J6", Size.MEDIUM)
                )
        ));


//        Optional<Folder> folderA = findFolderByName("Folder D");
//        System.out.println(folderA.map(folder -> "Znaleziono: " + folder.getName()).orElse("Nie znaleziono folderu A"));
//        findFoldersBySize("LARGE");
        System.out.println("Number of folders: " + count());
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

        if (folder.getClass() == DefaultMultiFolder.class) {

            DefaultMultiFolder searchFolder = (DefaultMultiFolder) folder;

            if (searchFolder.getName().equals(name)) {
                return searchFolder;
            }

            for (Folder subFolder : searchFolder.getFolders()) {
                System.out.println("Szukam w subFolder: " + subFolder.getName());
                Folder returnedFromSubFolder = searchFolderWithName(subFolder, name);
                if (returnedFromSubFolder != null) {
                    return returnedFromSubFolder;
                }
            }
        }

        if (folder.getClass() == DefaultFolder.class) {
            System.out.println("SPRAWDZAM FOLDER: " + folder.getName() + " -- " + name);
            if (folder.getName().equals(name)) {
                System.out.println("Zwracam folder: " + folder.getName());
                return folder;
            }
        }
        return null;
    }

    private void searchFolderWithSize(Folder folder, String size, List<Folder> folders) {
        if (folder.getClass() == DefaultMultiFolder.class) {

            DefaultMultiFolder searchFolder = (DefaultMultiFolder) folder;

            if (searchFolder.getSize().equals(size)) {
                folders.add(folder);
            }

            for (Folder subFolder : searchFolder.getFolders()) {
                System.out.println("Szukam w subFolder: " + subFolder);
                searchFolderWithSize(subFolder, size, folders);
            }
        }

        if (folder.getClass() == DefaultFolder.class) {
            if (folder.getSize().equals(size)) {
                System.out.println("Dodaje folder: " + folder.getSize());
                folders.add(folder);
            }
        }
    }

    private void countFolders(Folder folder) {

        System.out.println("CLASS TYPE: " + folder.getClass() + " || " +  folder.getName() + " || ");
        if (folder.getClass() == DefaultMultiFolder.class) {

            DefaultMultiFolder searchFolder = (DefaultMultiFolder) folder;
            for (Folder subFolder : searchFolder.getFolders()) {
                this.counter++;
                countFolders(subFolder) ;
            }
        }
    }
}
