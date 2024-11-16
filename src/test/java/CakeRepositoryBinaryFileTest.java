import domain.Cake;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CakeRepositoryBinaryFile;
import repository.FileNotValidException;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CakeRepositoryBinaryFileTest {

    private static final String TEST_FILE_PATH = "test_cakes.bin";
    private CakeRepositoryBinaryFile repository;

    @BeforeEach
    void setUp() throws FileNotValidException, IOException {
        // Ensure the test binary file exists
        File file = new File(TEST_FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }
        repository = new CakeRepositoryBinaryFile(TEST_FILE_PATH);
    }

    @AfterEach
    void tearDown() {
        // Delete the test binary file after each test
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testAddCake() throws FileNotValidException {
        Cake cake = new Cake(1, 500, 20, "Chocolate");
        assertTrue(repository.add(1, cake), "The cake should be added successfully.");

        // Reload repository to verify persistence
        repository = new CakeRepositoryBinaryFile(TEST_FILE_PATH);
        Cake retrievedCake = repository.findById(1);
        assertNotNull(retrievedCake, "The cake should be retrievable after saving.");
        assertEquals(cake, retrievedCake, "The retrieved cake should match the saved cake.");
    }

    @Test
    void testModifyCake() throws FileNotValidException {
        Cake cake = new Cake(2, 750, 30, "Vanilla");
        repository.add(2, cake);

        // Modify the cake's properties
        cake.setWeight(800);
        cake.setType("Deluxe Vanilla");
        assertTrue(repository.modify(2, cake), "The cake should be modified successfully.");

        // Reload repository to verify modification persistence
        repository = new CakeRepositoryBinaryFile(TEST_FILE_PATH);
        Cake modifiedCake = repository.findById(2);
        assertNotNull(modifiedCake, "The modified cake should be retrievable after modification.");
        assertEquals(800, modifiedCake.getWeight(), "The weight should match the modified value.");
        assertEquals("Deluxe Vanilla", modifiedCake.getType(), "The type should match the modified value.");
    }

    @Test
    void testDeleteCake() throws FileNotValidException {
        Cake cake = new Cake(3, 600, 25, "Strawberry");
        repository.add(3, cake);

        assertTrue(repository.delete(3), "The cake should be deleted successfully.");

        // Reload repository to verify deletion persistence
        repository = new CakeRepositoryBinaryFile(TEST_FILE_PATH);
        assertNull(repository.findById(3), "The cake should no longer be retrievable after deletion.");
    }
}
