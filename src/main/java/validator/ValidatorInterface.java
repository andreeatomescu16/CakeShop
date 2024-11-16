package validator;
import java.lang.Exception;

public interface ValidatorInterface<T> {
    void validate (T entity) throws ValidatorException;
}
