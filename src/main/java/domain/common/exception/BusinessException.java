
package domain.common.exception;


import common.exception.ApplicationException;

import java.util.Map;

public class BusinessException extends ApplicationException {

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, String contextName) {
        super(message, contextName);
    }

    public BusinessException(String message, String contextName, Object... contextDataList) {
        super(message, contextName, contextDataList);
    }

    public BusinessException(String message, String contextName, Map contextDataMap) {
        super(message, contextName, contextDataMap);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message, Throwable cause, String contextName) {
        super(message, cause, contextName);
    }

    public BusinessException(String message, Throwable cause, String contextName, Object... contextDataList) {
        super(message, cause, contextName, contextDataList);
    }

    public BusinessException(String message, Throwable cause, String contextName, Map contextDataMap) {
        super(message, cause, contextName, contextDataMap);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(Throwable cause, String contextName, Object... contextDataList) {
        super(cause, contextName, contextDataList);
    }

    public BusinessException(Throwable cause, String contextName, Map contextDataMap) {
        super(cause, contextName, contextDataMap);
    }
}
