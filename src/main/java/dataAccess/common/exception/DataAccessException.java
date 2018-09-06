
package dataAccess.common.exception;


import common.exception.ApplicationException;

import java.util.Map;

public class DataAccessException extends ApplicationException {


    public DataAccessException() {
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, String contextName) {
        super(message, contextName);
    }

    public DataAccessException(String message, String contextName, Object... contextDataList) {
        super(message, contextName, contextDataList);
    }

    public DataAccessException(String message, String contextName, Map contextDataMap) {
        super(message, contextName, contextDataMap);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(String message, Throwable cause, String contextName, Object... contextDataList) {
        super(message, cause, contextName, contextDataList);
    }

    public DataAccessException(String message, Throwable cause, String contextName, Map contextDataMap) {
        super(message, cause, contextName, contextDataMap);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }

    public DataAccessException(Throwable cause, String contextName, Object... contextDataList) {
        super(cause, contextName, contextDataList);
    }

    public DataAccessException(Throwable cause, String contextName, Map contextDataMap) {
        super(cause, contextName, contextDataMap);
    }
}
