package myRestIIQ6.system.exceptions.application;

import java.util.function.Supplier;

import myRestIIQ6.system.exceptions.EntityBusinessException;



public class ApplicationDoesNotExistException extends EntityBusinessException {

    private static final String EXCEPTION_MESSAGE = "Application does not exist";

    public ApplicationDoesNotExistException() {
        super(EXCEPTION_MESSAGE);
    }

    public static Supplier<ApplicationDoesNotExistException> newMenuCategoryCreateException2() {
        return () -> new ApplicationDoesNotExistException();
    }
}
