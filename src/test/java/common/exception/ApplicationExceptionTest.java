package common.exception;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by SuslovAI on 14.08.2017.
 */
public class ApplicationExceptionTest {

    public static class Contact {

        private String name;
        private Integer age;
        private String phoneNumber;

        public Contact(String name, Integer age, String phoneNumber) {
            this.name = name;
            this.age = age;
            this.phoneNumber = phoneNumber;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        @Override
        public String toString() {
            return "Contact{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    '}';
        }
    }

    @Test
    public void testApplicationExceptionWithoutContext() {
        RuntimeException e = new ApplicationException("test");
        assertEquals("test", e.getMessage());
    }

    @Test
    public void testApplicationExceptionWithOneContext() {
        String expectedString = "TestMessage. Contexts { Context 'TestContext' data: { [Contact{name='testName1', age=100, phoneNumber='8914123456'}, " +
                "Contact{name='testName2', age=101, phoneNumber='8914123457'}] } }.";

        ApplicationException e = new ApplicationException("TestMessage", "TestContext",
                new Contact("testName1", 100, "8914123456"),
                new Contact("testName2", 101, "8914123457")
        );
        assertEquals(expectedString, e.getMessage());
        e = new ApplicationException("TestMessage", "TestContext");
        e.addContextData(new Contact("testName1", 100, "8914123456"));
        e.addContextData(new Contact("testName2", 101, "8914123457"));
        assertEquals(expectedString, e.getMessage());
    }

    @Test
    public void testApplicationExceptionWithTwoContexts() {
        String expectedString = "TestMessage. Contexts { Context 'secondContext' data: { [123, testString] }, " +
                "Context 'firstContext' data: { [Contact{name='testName1', age=100, phoneNumber='8914123456'}, Contact{name='testName2', age=101, phoneNumber='8914123457'}] } }.";

        ApplicationException e = new ApplicationException("TestMessage", "firstContext",
                new Contact("testName1", 100, "8914123456"),
                new Contact("testName2", 101, "8914123457")
        );
        e.addContext("EmptyContext");
        e.addContext("secondContext");
        e.addContextData(123);
        e.addContextData("testString");
        assertEquals(expectedString, e.getMessage());
    }

    @Test
    public void testApplicationExceptionWithThreeContextsAndMapParameters() {
        String expectedString = "TestMessage. Contexts { Context 'thirdContext' data: { {number=321, string=testString2} }, Context 'secondContext' data: { [123, testString1] }, " +
                "Context 'firstContext' data: { {secondContact=Contact{name='testName2', age=101, phoneNumber='8914123457'}, firstContact=Contact{name='testName1', age=100, " +
                "phoneNumber='8914123456'}} } }.";

        Map contextData = new HashMap<String, Object>();
        contextData.put("firstContact", new Contact("testName1", 100, "8914123456"));
        contextData.put("secondContact", new Contact("testName2", 101, "8914123457"));

        ApplicationException e = new ApplicationException("TestMessage", "firstContext", contextData);
        e.addContext("secondContext");
        e.addContextData(123);
        e.addContextData("testString1");
        e.addContext("thirdContext");
        e.addContextData("number", 321);
        e.addContextData("string", "testString2");
        assertEquals(expectedString, e.getMessage());
    }


}