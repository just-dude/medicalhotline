package dataAccess.common.exception;

/**
 * Created by suslovai on 15.09.2017.
 */
public class EntityWithSuchIdDoesNotExistsDataAccessException extends DataAccessException {

    private static final String MESSAGE_FORMAT = "Entity with such id - %s not exists";

    private Object id;

    public EntityWithSuchIdDoesNotExistsDataAccessException(Object id) {
        super(String.format(MESSAGE_FORMAT, id));
    }

    public EntityWithSuchIdDoesNotExistsDataAccessException(Object id, Throwable cause) {
        super(String.format(MESSAGE_FORMAT, id), cause);
    }

    public Object getId() {
        return id;
    }
}
