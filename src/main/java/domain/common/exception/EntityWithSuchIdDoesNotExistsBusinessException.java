package domain.common.exception;


/**
 * Created by suslovai on 15.09.2017.
 */
public class EntityWithSuchIdDoesNotExistsBusinessException extends DataAccessFailedBuisnessException {

    private static final String MESSAGE_FORMAT = "Entity with such id - %s not exists";

    private Object id;

    public EntityWithSuchIdDoesNotExistsBusinessException(Object id) {
        super(String.format(MESSAGE_FORMAT, id));
    }

    public EntityWithSuchIdDoesNotExistsBusinessException(Object id, Throwable cause) {
        super(String.format(MESSAGE_FORMAT, id), cause);
    }

    public Object getId() {
        return id;
    }
}
