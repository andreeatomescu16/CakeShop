package validator;

import domain.Cake;

public class CakeValidator implements ValidatorInterface<Cake> {

    public CakeValidator() {}

    @Override
    public void validate(Cake cake) throws ValidatorException {
        if (cake.getId() < 0) {
            throw new ValidatorException("Id must be a positive integer");
        }

        if (cake.getWeight() <= 0) {
            throw new ValidatorException("Weight must be a positive integer");
        }

        if (cake.getPrice() < 0) {
            throw new ValidatorException("Price must be a positive integer");
        }

        if (cake.getType().isEmpty()) {
            throw new ValidatorException("Type must not be empty");
        }

        if (cake.getPrice() > 10000)
            throw new ValidatorException("Too expensive");

        if (!cake.getType().equalsIgnoreCase("chocolate") && !cake.getType().equalsIgnoreCase("vanilla") && !cake.getType().equalsIgnoreCase("caramel"))
            throw new ValidatorException("Invalid type");
    }
}