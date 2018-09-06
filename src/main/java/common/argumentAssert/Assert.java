package common.argumentAssert;


import org.apache.commons.lang3.ArrayUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by suslovai on 13.09.2017.
 */
public class Assert {

    private static final String DEFAULT_NOT_NULL_EX_MESSAGE_FORMAT_MESSAGE = "Argument %s must be not null";
    private static final String DEFAULT_NOT_NULL_EX_MESSAGE = "Argument must be not null";

    private static final String DEFAULT_NOT_CONTAINS_NULL_SIMPLE_FORMAT_MESSAGE = "Argument must not contains null at %s index";

    private static final String DEFAULT_NOT_EMPTY_COLLECTION_SIMPLE_EX_MESSAGE = "Argument must has size more than 0";
    private static final String DEFAULT_NOT_EMPTY_COLLECTION_SIMPLE_EX_MESSAGE_FORMAT_MESSAGE = "Argument %s must has size more than 0";

    public static <T> void notNull(T argument) {
        notNull(argument, DEFAULT_NOT_NULL_EX_MESSAGE, new Object[]{});
    }

    public static <T> void notNull(T argument, String argumentName) {
        notNull(argument, DEFAULT_NOT_NULL_EX_MESSAGE_FORMAT_MESSAGE, argumentName);
    }

    public static <T> void notEmpty(Collection<T> argument) {
        notEmpty(argument, DEFAULT_NOT_EMPTY_COLLECTION_SIMPLE_EX_MESSAGE, new Object[]{});
    }

    public static <T> void notEmpty(Collection<T> argument, String argumentName) {
        notEmpty(argument, DEFAULT_NOT_EMPTY_COLLECTION_SIMPLE_EX_MESSAGE_FORMAT_MESSAGE, argumentName);
    }

    public static <T> void notEmpty(Collection<T> argument, String messageFormat, Object... messageArgs) {
        notNull(argument);
        if (argument.size()==0) {
            throw new IllegalArgumentException(String.format(messageFormat, messageArgs));
        }
    }

    public static <K,T> void notEmpty(Map<K,T> argument) {
        notEmpty(argument, DEFAULT_NOT_EMPTY_COLLECTION_SIMPLE_EX_MESSAGE, new Object[]{});
    }

    public static <K,T> void notEmpty(Map<K,T> argument, String argumentName) {
        notEmpty(argument, DEFAULT_NOT_EMPTY_COLLECTION_SIMPLE_EX_MESSAGE_FORMAT_MESSAGE, argumentName);
    }

    public static <K,T> void notEmpty(Map<K,T> argument, String messageFormat, Object... messageArgs) {
        notNull(argument);
        if (argument.size()==0) {
            throw new IllegalArgumentException(String.format(messageFormat, messageArgs));
        }
    }

    public static <T> void notEmpty(T[] argument){
        notEmpty(argument, DEFAULT_NOT_EMPTY_COLLECTION_SIMPLE_EX_MESSAGE);
    }

    public static <T> void notEmpty(T[] argument, String messageFormat, Object... messageArgs) {
        notNull(argument);
        if (argument.length==0) {
            throw new IllegalArgumentException(String.format(messageFormat, messageArgs));
        }
    }

    public static <T> void notNull(T argument, String messageFormat, Object... messageArgs) {
        if (argument == null) {
            throw new IllegalArgumentException(String.format(messageFormat, messageArgs));
        }
    }

    public static <T> void notContainsNullElements(Collection<T> collection) {
        notContainsNullElements(collection, DEFAULT_NOT_CONTAINS_NULL_SIMPLE_FORMAT_MESSAGE, new Object[]{});
    }

    public static <T> void notContainsNullElements(Collection<T> collection, String messageFormat, Object... messageArgs) {
        notNull(collection);
        int i = 0;
        for (T element : collection) {
            if (element == null) {
                throw new IllegalArgumentException(String.format(messageFormat, ArrayUtils.addAll(messageArgs, (Object) Integer.valueOf(i))));
            }
            i++;
        }
    }

    public static <K,T> void notContainsNullElements(Map<K,T> map) {
        notContainsNullElements(map, DEFAULT_NOT_CONTAINS_NULL_SIMPLE_FORMAT_MESSAGE, new Object[]{});
    }

    public static <K,T> void notContainsNullElements(Map<K,T> map, String messageFormat, Object... messageArgs) {
        notNull(map);
        notContainsNullElements(map.keySet(),messageFormat,messageArgs);
        notContainsNullElements(map.values(),messageFormat,messageArgs);
    }
}
