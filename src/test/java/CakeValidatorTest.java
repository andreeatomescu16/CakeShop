import domain.Cake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validator.CakeValidator;
import validator.ValidatorException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CakeValidatorTest {

    private CakeValidator cakeValidator;

    @BeforeEach
    void setUp() {
        cakeValidator = new CakeValidator();
    }

    @Test
    void testValidCake() {
        // This cake meets all the validation criteria
        Cake validCake = new Cake(1, 500, 15, "Chocolate");
        assertDoesNotThrow(() -> cakeValidator.validate(validCake), "This cake should be valid");
    }

    @Test
    void testInvalidId() {
        // Test negative ID
        Cake invalidIdCake = new Cake(-1, 500, 15, "Chocolate");
        assertThrows(ValidatorException.class, () -> cakeValidator.validate(invalidIdCake), "Id must be a positive integer");
    }

    @Test
    void testInvalidWeight() {
        // Test zero weight
        Cake zeroWeightCake = new Cake(1, 0, 15, "Chocolate");
        assertThrows(ValidatorException.class, () -> cakeValidator.validate(zeroWeightCake), "Weight must be a positive integer");

        // Test negative weight
        Cake negativeWeightCake = new Cake(1, -100, 15, "Chocolate");
        assertThrows(ValidatorException.class, () -> cakeValidator.validate(negativeWeightCake), "Weight must be a positive integer");
    }

    @Test
    void testInvalidPrice() {
        // Test negative price
        Cake negativePriceCake = new Cake(1, 500, -10, "Chocolate");
        assertThrows(ValidatorException.class, () -> cakeValidator.validate(negativePriceCake), "Price must be a positive integer");

        // Test too high price
        Cake tooExpensiveCake = new Cake(1, 500, 20000, "Chocolate");
        assertThrows(ValidatorException.class, () -> cakeValidator.validate(tooExpensiveCake), "Too expensive");
    }

    @Test
    void testEmptyType() {
        // Test empty type
        Cake emptyTypeCake = new Cake(1, 500, 15, "");
        assertThrows(ValidatorException.class, () -> cakeValidator.validate(emptyTypeCake), "Type must not be empty");
    }

    @Test
    void testInvalidType() {
        // Test invalid type (not chocolate, vanilla, or caramel)
        Cake invalidTypeCake = new Cake(1, 500, 15, "Strawberry");
        assertThrows(ValidatorException.class, () -> cakeValidator.validate(invalidTypeCake), "Invalid type");
    }
}
