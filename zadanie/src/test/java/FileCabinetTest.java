import model.Folder;
import model.Size;
import model.impl.DefaultFolder;
import model.impl.DefaultMultiFolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.impl.FileCabinet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileCabinetTest {

    @Test
    public void shouldFindFolderByName() {
        FileCabinet fileCabinet = new FileCabinet(createTestFolders());

        Optional<Folder> folder = fileCabinet.findFolderByName("Folder E4_A");

        Assertions.assertTrue(folder.isPresent());
        Assertions.assertEquals("Folder E4_A", folder.get().getName());

    }

    @Test
    public void shouldNotFindFolderByName() {
        FileCabinet fileCabinet = new FileCabinet(createTestFolders());

        Optional<Folder> folder = fileCabinet.findFolderByName("Folder");

        Assertions.assertTrue(folder.isEmpty());
    }

    @Test
    public void shouldFindFoldersBySmallSize() {
        FileCabinet fileCabinet = new FileCabinet(createTestFolders());

        List<Folder> folders = fileCabinet.findFoldersBySize(Size.SMALL.name());

        Assertions.assertEquals(13, folders.size());
        Assertions.assertEquals("Folder A", folders.getFirst().getName());
    }

    @Test
    public void shouldNotFindFoldersBySmallSize() {
        FileCabinet fileCabinet = new FileCabinet(createTestFoldersWithoutSmallSize());

        List<Folder> folders = fileCabinet.findFoldersBySize(Size.SMALL.name());

        Assertions.assertEquals(0, folders.size());
    }

    @Test
    public void shouldFindFoldersByMediumSize() {
        FileCabinet fileCabinet = new FileCabinet(createTestFolders());

        List<Folder> folders = fileCabinet.findFoldersBySize(Size.MEDIUM.name());

        Assertions.assertEquals(7, folders.size());
        Assertions.assertTrue(
                folders.stream().map(Folder::getName).toList().contains("Folder D3_B")
        );
    }

    @Test
    public void shouldNotFindFoldersByMediumSize() {
        FileCabinet fileCabinet = new FileCabinet(createTestFoldersWithoutMediumSize());

        List<Folder> folders = fileCabinet.findFoldersBySize(Size.MEDIUM.name());

        Assertions.assertEquals(0, folders.size());
    }

    @Test
    public void shouldFindFoldersByLargeSize() {
        FileCabinet fileCabinet = new FileCabinet(createTestFolders());

        List<Folder> folders = fileCabinet.findFoldersBySize(Size.LARGE.name());

        Assertions.assertEquals(7, folders.size());
        Assertions.assertTrue(
                folders.stream().map(Folder::getName).toList().contains("Folder F2_D")
        );
    }

    @Test
    public void shouldNotFindFoldersByLargeSize() {
        FileCabinet fileCabinet = new FileCabinet(createTestFoldersWithoutLargeSize());

        List<Folder> folders = fileCabinet.findFoldersBySize(Size.LARGE.name());

        Assertions.assertEquals(0, folders.size());
    }

    @Test
    public void shouldReturnNumberOfFolders() {
        FileCabinet fileCabinet = new FileCabinet(createTestFolders());

        int numberOfFolders = fileCabinet.count();
        Assertions.assertEquals(27, numberOfFolders);
    }

    private List<Folder> createTestFolders() {
        List<Folder> folders = new ArrayList<>();

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
        return folders;
    }

    private List<Folder> createTestFoldersWithoutSmallSize() {
        List<Folder> folders = new ArrayList<>();
        folders.add(new DefaultFolder("Folder A", Size.MEDIUM));
        folders.add(new DefaultFolder("Folder B", Size.MEDIUM));
        folders.add(new DefaultFolder("Folder C", Size.LARGE));
        return folders;
    }

    private List<Folder> createTestFoldersWithoutMediumSize() {
        List<Folder> folders = new ArrayList<>();
        folders.add(new DefaultFolder("Folder A", Size.LARGE));
        folders.add(new DefaultFolder("Folder B", Size.LARGE));
        folders.add(new DefaultFolder("Folder C", Size.LARGE));
        return folders;
    }

    private List<Folder> createTestFoldersWithoutLargeSize() {
        List<Folder> folders = new ArrayList<>();
        folders.add(new DefaultFolder("Folder A", Size.SMALL));
        folders.add(new DefaultFolder("Folder B", Size.MEDIUM));
        folders.add(new DefaultFolder("Folder C", Size.SMALL));
        return folders;
    }
}
