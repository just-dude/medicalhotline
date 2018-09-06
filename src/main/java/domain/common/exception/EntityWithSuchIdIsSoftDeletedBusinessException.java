package domain.common.exception;


/**
 * Created by suslovai on 15.09.2017.
 */
public class EntityWithSuchIdIsSoftDeletedBusinessException extends DataAccessFailedBuisnessException {

    private static final String MESSAGE_FORMAT = "Entity with such id - %s is soft deleted";

    private Object id;

    public EntityWithSuchIdIsSoftDeletedBusinessException(Object id) {
        super(String.format(MESSAGE_FORMAT, id));
    }

    public EntityWithSuchIdIsSoftDeletedBusinessException(Object id, Throwable cause) {
        super(String.format(MESSAGE_FORMAT, id), cause);
    }

    public Object getId() {
        return id;
    }
}
