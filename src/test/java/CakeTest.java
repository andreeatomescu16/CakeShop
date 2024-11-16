
import domain.Cake;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CakeTest {

    @Test
    void testDefaultConstructor() {
        Cake cake = new Cake();
        assertNull(cake.getId(), "Default constructor should initialize id as null.");
        assertEquals(0, cake.getWeight(), "Default constructor should initialize weight as 0.");
        assertEquals(0, cake.getPrice(), "Default constructor should initialize price as 0.");
        assertNull(cake.getType(), "Default constructor should initialize type as null.");
    }

    @Test
    void testParameterizedConstructor() {
        Cake cake = new Cake(1, 500, 20, "Chocolate");

        assertEquals(1, cake.getId(), "Id should be initialized correctly by the parameterized constructor.");
        assertEquals(500, cake.getWeight(), "Weight should be initialized correctly by the parameterized constructor.");
        assertEquals(20, cake.getPrice(), "Price should be initialized correctly by the parameterized constructor.");
        assertEquals("Chocolate", cake.getType(), "Type should be initialized correctly by the parameterized constructor.");
    }

    @Test
    void testSetAndGetId() {
        Cake cake = new Cake();
        cake.setId(2);
        assertEquals(2, cake.getId(), "getId should return the id set by setId.");
    }

    @Test
    void testSetAndGetWeight() {
        Cake cake = new Cake();
        cake.setWeight(750);
        assertEquals(750, cake.getWeight(), "getWeight should return the weight set by setWeight.");
    }

    @Test
    void testSetAndGetPrice() {
        Cake cake = new Cake();
        cake.setPrice(30);
        assertEquals(30, cake.getPrice(), "getPrice should return the price set by setPrice.");
    }

    @Test
    void testSetAndGetType() {
        Cake cake = new Cake();
        cake.setType("Vanilla");
        assertEquals("Vanilla", cake.getType(), "getType should return the type set by setType.");
    }

    @Test
    void testToString() {
        Cake cake = new Cake(3, 800, 40, "Red Velvet");
        String expectedString = "Cake{id=3, weight=800, price=40, type='Red Velvet'}";
        assertEquals(expectedString, cake.toString(), "toString should return the correct string representation of the Cake object.");
    }
}
