package validator;

import domain.Order;

public class OrderValidator implements ValidatorInterface<Order>{

    public OrderValidator() {}

    @Override
    public void validate(Order order) throws ValidatorException {
        if (order.getId() < 0) {
            throw new ValidatorException("Id must be a positive integer");
        }

        if (order.getCake() == null) {
            throw new ValidatorException("Cake must not be null");
        }

        if (order.getName().isEmpty()) {
            throw new ValidatorException("Name must not be empty");
        }
    }
}
