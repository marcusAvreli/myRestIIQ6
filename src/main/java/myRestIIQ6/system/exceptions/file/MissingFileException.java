package myRestIIQ6.system.exceptions.file;

import java.util.function.Supplier;

import myRestIIQ6.system.exceptions.EntityBusinessException;




public class MissingFileException extends EntityBusinessException {

    private static final String EXCEPTION_MESSAGE = "Menu category can not be updated";

    public MissingFileException() {
        super(EXCEPTION_MESSAGE);
    }

    public static Supplier<MissingFileException> newMenuCategoryCreateException() {
        return () -> new MissingFileException();
    }
}