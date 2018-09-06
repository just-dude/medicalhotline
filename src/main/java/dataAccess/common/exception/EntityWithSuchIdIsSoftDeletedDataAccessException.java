package dataAccess.common.exception;

/**
 * Created by suslovai on 15.09.2017.
 */
public class EntityWithSuchIdIsSoftDeletedDataAccessException extends DataAccessException {

    private static final String MESSAGE_FORMAT = "Entity with such id - %s is soft deleted";

    private Object id;

    public EntityWithSuchIdIsSoftDeletedDataAccessException(Object id) {
        super(String.format(MESSAGE_FORMAT, id));
    }

    public EntityWithSuchIdIsSoftDeletedDataAccessException(Object id, Throwable cause) {
        super(String.format(MESSAGE_FORMAT, id), cause);
    }

    public Object getId() {
        return id;
    }
}
