
import domain.Cake;
import filter.FilterCakesByWeight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilterCakesByWeightTest {

    @Test
    void testAcceptWithWeightLessThanThreshold() {
        FilterCakesByWeight filter = new FilterCakesByWeight(500);
        Cake lightCake = new Cake(1, 300, 20, "Chocolate");

        assertTrue(filter.accept(lightCake), "The filter should accept cakes with weight less than the threshold.");
    }

    @Test
    void testAcceptWithWeightEqualToThreshold() {
        FilterCakesByWeight filter = new FilterCakesByWeight(500);
        Cake equalWeightCake = new Cake(2, 500, 25, "Vanilla");

        assertFalse(filter.accept(equalWeightCake), "The filter should reject cakes with weight equal to the threshold.");
    }

    @Test
    void testAcceptWithWeightGreaterThanThreshold() {
        FilterCakesByWeight filter = new FilterCakesByWeight(500);
        Cake heavyCake = new Cake(3, 600, 30, "Strawberry");

        assertFalse(filter.accept(heavyCake), "The filter should reject cakes with weight greater than the threshold.");
    }
}
