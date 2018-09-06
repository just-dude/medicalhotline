package domain.common.exception;

/**
 * Created by SuslovAI on 15.10.2017.
 */
public class DataIntegrityViolationBusinessException extends DataAccessFailedBuisnessException {

    private static final String MESSAGE = "Data integrity violation has occured. Can't remove entity, because other entities have references to this entity.";

    public DataIntegrityViolationBusinessException() {
        super(MESSAGE);
    }

    public DataIntegrityViolationBusinessException(String messsage) {
        super(messsage);
    }

    public DataIntegrityViolationBusinessException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
