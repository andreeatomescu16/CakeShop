import domain.Cake;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CakeRepositoryJsonFile;
import repository.FileNotValidException;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CakeRepositoryJsonFileTest {

    private static final String TEST_FILE = "test_cakes.json";
    private CakeRepositoryJsonFile repository;

    @BeforeEach
    void setUp() throws FileNotValidException {
        repository = new CakeRepositoryJsonFile(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testAddCake() throws FileNotValidException {
        // Add a cake and ensure it's saved in the file
        Cake cake = new Cake(1, 500, 20, "Chocolate");
        assertTrue(repository.add(1, cake), "Cake should be added successfully.");

        // Reload the repository to verify persistence
        repository = new CakeRepositoryJsonFile(TEST_FILE);
        Optional<Cake> retrievedCake = Optional.ofNullable(repository.findById(1));
        assertTrue(retrievedCake.isPresent(), "The cake should be retrievable after saving.");
        assertEquals(cake, retrievedCake.get(), "The retrieved cake should match the saved cake.");
    }

    @Test
    void testModifyCake() throws FileNotValidException {
        // Add a cake, modify it, and ensure the modification is saved
        Cake cake = new Cake(2, 300, 15, "Vanilla");
        repository.add(2, cake);

        // Modify the cake
        cake.setWeight(350);
        cake.setType("Vanilla Deluxe");
        assertTrue(repository.modify(2, cake), "The cake should be modified successfully.");

        // Reload the repository to verify modification persistence
        repository = new CakeRepositoryJsonFile(TEST_FILE);
        Optional<Cake> modifiedCake = Optional.ofNullable(repository.findById(2));
        assertTrue(modifiedCake.isPresent(), "The modified cake should be retrievable after modification.");
        assertEquals(350, modifiedCake.get().getWeight(), "The weight should match the modified value.");
        assertEquals("Vanilla Deluxe", modifiedCake.get().getType(), "The type should match the modified value.");
    }

    @Test
    void testDeleteCake() throws FileNotValidException {
        // Add a cake and delete it, then ensure it's removed from the file
        Cake cake = new Cake(3, 400, 25, "Strawberry");
        repository.add(3, cake);

        assertTrue(repository.delete(3), "The cake should be deleted successfully.");

        // Reload the repository to verify deletion persistence
        repository = new CakeRepositoryJsonFile(TEST_FILE);
        assertNull(repository.findById(3), "The cake should no longer be retrievable after deletion.");
    }
}
