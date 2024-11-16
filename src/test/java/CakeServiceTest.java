import domain.Cake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.RepoMemory;
import service.CakeService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CakeServiceTest {

    private CakeService cakeService;
    private RepoMemory<Integer, Cake> repo;

    @BeforeEach
    void setUp() {
        repo = new RepoMemory<>();
        cakeService = new CakeService(repo);
    }

    // Helper method to count items in the repository
    private int getRepoSize() {
        int count = 0;
        Iterator<Cake> iterator = repo.get_all();
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    // Helper method to find a Cake by ID
    private Cake getCakeById(int id) {
        Iterator<Cake> iterator = repo.get_all();
        while (iterator.hasNext()) {
            Cake cake = iterator.next();
            if (cake.getId() == id) {
                return cake;
            }
        }
        return null;
    }

    @Test
    void testNewOrderValidCake() {
        Cake cake = new Cake(1, 500, 20, "Chocolate");
        assertTrue(cakeService.new_order(cake), "The cake should be added successfully");
        assertEquals(1, getRepoSize(), "The repository should contain one cake");
    }

    @Test
    void testNewOrderInvalidCake() {
        Cake invalidCake = new Cake(2, -100, 15, "Vanilla"); // assuming negative weight is invalid
        assertFalse(cakeService.new_order(invalidCake), "The cake should not be added due to invalid data");
        assertEquals(0, getRepoSize(), "The repository should remain empty");
    }

    @Test
    void testCancelOrderExistingCake() {
        Cake cake = new Cake(1, 500, 15, "Chocolate");
        cakeService.new_order(cake);

        // Attempt to cancel the existing order
        assertTrue(cakeService.cancel_order(1), "The cake order should be canceled successfully");
    }

    @Test
    void testCancelOrderNonExistingCake() {
        assertFalse(cakeService.cancel_order(10), "Canceling a non-existing order should return false");
    }

    @Test
    void testUpdateOrderInvalidCake() {
        Cake originalCake = new Cake(1, 500, 15, "Vanilla");
        cakeService.new_order(originalCake);

        // Attempt an invalid update
        Cake invalidCake = new Cake(1, -100, 0, "");
        assertFalse(cakeService.update_order(1, invalidCake), "Invalid cake update should fail");

        // Ensure the original cake is still intact
        Cake retrievedCake = cakeService.get_repo().findById(1);  // Adjust if necessary
        assertNotNull(retrievedCake, "The original cake should still be in the repository");
        assertEquals(originalCake, retrievedCake, "The original cake should be unchanged after a failed update");
    }

    @Test
    void testGetAll() {
        Cake cake1 = new Cake(1, 500, 15, "Chocolate");
        Cake cake2 = new Cake(2, 700, 20, "Vanilla"); // Different ID and valid properties

        cakeService.new_order(cake1);
        cakeService.new_order(cake2);

        Iterator<Cake> cakesIterator = cakeService.get_all();
        List<Cake> cakes = new ArrayList<>();
        cakesIterator.forEachRemaining(cakes::add);

        // Verify that two cakes are present
        assertEquals(2, cakes.size(), "There should be two cakes in the repository");
    }
}
