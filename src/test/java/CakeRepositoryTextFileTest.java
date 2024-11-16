import domain.Cake;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CakeRepositoryTextFile;
import repository.FileNotValidException;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CakeRepositoryTextFileTest {

    private static final String TEST_FILE_PATH = "data/cakes2.txt";
    private CakeRepositoryTextFile repository;

    @BeforeEach
    void setUp() throws IOException, FileNotValidException {
        File file = new File(TEST_FILE_PATH);
        // Ensure the directory exists
        file.getParentFile().mkdirs();
        // Ensure the file exists, or create a new empty file for testing
        if (!file.exists()) {
            file.createNewFile();
        }
        repository = new CakeRepositoryTextFile(TEST_FILE_PATH);
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testAddCake() throws FileNotValidException {
        Cake cake = new Cake(1, 500, 20, "Chocolate");
        assertTrue(repository.add(1, cake), "The cake should be added successfully.");

        // Reload the repository to verify persistence
        repository = new CakeRepositoryTextFile(TEST_FILE_PATH);
        Cake retrievedCake = repository.findById(1);
        assertEquals(cake, retrievedCake, "The retrieved cake should match the saved cake.");
    }

    @Test
    void testDeleteCake() throws FileNotValidException {
        Cake cake = new Cake(2, 750, 30, "Vanilla");
        repository.add(2, cake);

        assertTrue(repository.delete(2), "The cake should be deleted successfully.");

        // Reload the repository to verify deletion persistence
        repository = new CakeRepositoryTextFile(TEST_FILE_PATH);
        assertNull(repository.findById(2), "The cake should no longer be retrievable after deletion.");
    }

    @Test
    void testModifyCake() throws FileNotValidException {
        Cake cake = new Cake(3, 600, 25, "Strawberry");
        repository.add(3, cake);

        // Modify the cake's weight and type
        cake.setWeight(650);
        cake.setType("Strawberry Deluxe");
        assertTrue(repository.modify(3, cake), "The cake should be modified successfully.");

        // Reload repository to verify modification persistence
        repository = new CakeRepositoryTextFile(TEST_FILE_PATH);
        Cake modifiedCake = repository.findById(3);
        assertEquals(650, modifiedCake.getWeight(), "The weight should match the modified value.");
        assertEquals("Strawberry Deluxe", modifiedCake.getType(), "The type should match the modified value.");
    }

    @Test
    void testInvalidFileHandling() {
        assertThrows(RuntimeException.class, () -> new CakeRepositoryTextFile("non_existent_file.txt"),
                "Initializing with a non-existent file should throw RuntimeException.");
    }
}
