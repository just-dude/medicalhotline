package dataAccess.common.exception;

/**
 * Created by SuslovAI on 24.09.2017.
 */
public class UnsupportedOperationDataAccessException extends DataAccessException {

    private static final String MESSAGE_FORMAT = "Operation '%s' unsupprted by this class";

    public UnsupportedOperationDataAccessException(String operationName) {
        super(String.format(MESSAGE_FORMAT, operationName));
    }
}
