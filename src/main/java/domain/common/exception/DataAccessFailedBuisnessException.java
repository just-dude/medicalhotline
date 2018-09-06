package domain.common.exception;

import java.util.Map;

/**
 * Created by SuslovAI on 17.08.2017.
 */
public class DataAccessFailedBuisnessException extends BusinessException {

    public DataAccessFailedBuisnessException() {
    }

    public DataAccessFailedBuisnessException(String message) {
        super(message);
    }

    public DataAccessFailedBuisnessException(String message, String contextName) {
        super(message, contextName);
    }

    public DataAccessFailedBuisnessException(String message, String contextName, Object... contextDataList) {
        super(message, contextName, contextDataList);
    }

    public DataAccessFailedBuisnessException(String message, String contextName, Map contextDataMap) {
        super(message, contextName, contextDataMap);
    }

    public DataAccessFailedBuisnessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessFailedBuisnessException(String message, Throwable cause, String contextName, Object... contextDataList) {
        super(message, cause, contextName, contextDataList);
    }

    public DataAccessFailedBuisnessException(String message, Throwable cause, String contextName, Map contextDataMap) {
        super(message, cause, contextName, contextDataMap);
    }
}
