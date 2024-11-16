import domain.Cake;
import org.junit.jupiter.api.*;
import repository.CakeRepositoryDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CakeRepositoryDBTest {

    private static final String DB_URL = "jdbc:sqlite:identifier.sqlite"; // Use a test database URL
    private CakeRepositoryDB repository;

    @BeforeEach
    void setUp() {
        repository = new CakeRepositoryDB(DB_URL);
        clearDatabase(); // Clears the database before each test to avoid data carry-over
    }

    @AfterEach
    void tearDown() {
        clearDatabase(); // Ensure the database is clean after each test
    }

    @Test
    void testAddCake() {
        Cake cake = new Cake(1, 500, 20, "Chocolate");
        assertTrue(repository.add(1, cake), "Cake should be added successfully.");

        Cake retrievedCake = repository.findById(1);
        assertNotNull(retrievedCake, "Retrieved cake should not be null.");
        assertEquals(cake, retrievedCake, "Retrieved cake should match the added cake.");
    }

    @Test
    void testFindById() {
        Cake cake = new Cake(2, 300, 15, "Vanilla");
        repository.add(2, cake);

        Cake retrievedCake = repository.findById(2);
        assertNotNull(retrievedCake, "Retrieved cake should not be null.");
        assertEquals(2, retrievedCake.getId());
        assertEquals(300, retrievedCake.getWeight());
        assertEquals(15, retrievedCake.getPrice());
        assertEquals("Vanilla", retrievedCake.getType());
    }

    @Test
    void testGetAllCakes() {
        Cake cake1 = new Cake(1, 500, 20, "Chocolate");
        Cake cake2 = new Cake(2, 300, 15, "Vanilla");
        repository.add(1, cake1);
        repository.add(2, cake2);

        Iterator<Cake> cakeIterator = repository.get_all();
        List<Cake> cakes = new ArrayList<>();
        cakeIterator.forEachRemaining(cakes::add);

        assertEquals(2, cakes.size(), "There should be 2 cakes in the repository.");
        assertTrue(cakes.contains(cake1), "Repository should contain the first cake.");
        assertTrue(cakes.contains(cake2), "Repository should contain the second cake.");
    }

    private void clearDatabase() {
        // Helper method to clear the Cake table in the test database
        String sql = "DELETE FROM Cake";
        try (Connection conn = repository.connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
