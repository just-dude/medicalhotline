package domain.common;

import common.argumentAssert.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by suslovai on 13.09.2017.
 */
public class AssertTest {


    @Test
    public void testNotNull() throws Exception {
        try {
            Assert.notNull(new Object());
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException not expected");
        }
        try {
            Assert.notNull(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Argument must be not null", e.getMessage());
        }
        try {
            Assert.notNull(null, "myArgumentName");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Argument myArgumentName must be not null", e.getMessage());
        }
        try {
            Assert.notNull(null, "My formatted message with  args: %s, %s", 1, "lastArgument");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("My formatted message with  args: 1, lastArgument", e.getMessage());
        }
    }

    @Test
    public void testNotContainsNull() throws Exception {
        try {
            Assert.notContainsNullElements(new ArrayList());
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException not expected");
        }
        try {
            Assert.notContainsNullElements(Arrays.asList("one", "two"));
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException not expected");
        }
        try {
            List<Object> collection = null;
            Assert.notContainsNullElements(collection);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Argument must be not null", e.getMessage());
        }
        try {
            Assert.notContainsNullElements(Arrays.asList("one", null));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Argument must not contains null at 1 index", e.getMessage());
        }
        try {
            Assert.notContainsNullElements(Arrays.asList("one", null), "My formatted message with args: %s, %s. Null argument at %s index",
                    "oneArg", "twoArg");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("My formatted message with args: oneArg, twoArg. Null argument at 1 index", e.getMessage());
        }

    }
}