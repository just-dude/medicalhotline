package common.exception;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by SuslovAI on 14.08.2017.
 */
public class ApplicationException extends RuntimeException {

    private static String DEFAULT_CONTEXT_NAME = "dafaultContext";

    private Queue<ContextData> contextsDataQueue = Collections.asLifoQueue(new ArrayDeque<>());

    public ApplicationException() {
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, String contextName) {
        super(message);
        contextsDataQueue.add(new ContextData(contextName));
    }

    public ApplicationException(String message, String contextName, Object... contextDataList) {
        super(message);
        contextsDataQueue.add(new ContextData(contextName, new ArrayList<Object>(Arrays.asList(contextDataList))));
    }

    public ApplicationException(String message, String contextName, Map contextDataMap) {
        super(message);
        contextsDataQueue.add(new ContextData(contextName, contextDataMap));
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(String message, Throwable cause, String contextName, Object... contextDataList) {
        super(message, cause);
        contextsDataQueue.add(new ContextData(contextName, new ArrayList<Object>(Arrays.asList(contextDataList))));
    }

    public ApplicationException(String message, Throwable cause, String contextName, Map contextDataMap) {
        super(message, cause);
        contextsDataQueue.add(new ContextData(contextName, contextDataMap));
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(Throwable cause, String contextName, Object... contextDataList) {
        super(cause);
        contextsDataQueue.add(new ContextData(contextName, new ArrayList<Object>(Arrays.asList(contextDataList))));
    }

    public ApplicationException(Throwable cause, String contextName, Map contextDataMap) {
        super(cause);
        contextsDataQueue.add(new ContextData(contextName, contextDataMap));
    }

    public void addContextData(Object data) {
        addDefaultContextDataIfNotExists();
        this.contextsDataQueue.element().addData(data);
    }

    public void addContextData(String name, Object data) {
        addDefaultContextDataIfNotExists();
        this.contextsDataQueue.element().addData(name, data);
    }

    public void addContext(String contextName) {
        this.contextsDataQueue.add(new ContextData(contextName));
    }

    @Override
    public String getMessage() {
        if (contextsDataQueue.isEmpty()) {
            return super.getMessage();
        } else {
            String format = (super.getMessage().endsWith(".")) ? "%s %s" : "%s. %s";
            return String.format(format, super.getMessage(), getContextsDataMessage());
        }
    }

    private void addDefaultContextDataIfNotExists() {
        if (this.contextsDataQueue.isEmpty()) {
            this.contextsDataQueue.add(new ContextData(DEFAULT_CONTEXT_NAME));
        }
    }

    private String getContextsDataMessage() {
        String contexts = this.contextsDataQueue.stream().map(cd -> cd.toString()).filter(cdStr -> cdStr != "").collect(Collectors.joining(", "));
        return String.format("Contexts { %s }.", contexts);
    }
}
