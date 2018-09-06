package domain.common.exception;

/**
 * Created by SuslovAI on 01.12.2016.
 */
public class RuleViolationBusinessException extends BusinessException {

    protected String userMessage;

    public RuleViolationBusinessException(String userMessage) {
    }

    public RuleViolationBusinessException(String message, String userMessage) {
        super(message);
        this.userMessage=userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
}
