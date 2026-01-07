package utils;

import org.testng.Assert;

import java.util.List;

public class AssertUtils {

    public static void assertListNotEmpty(List<?> list) {
         Assert.assertFalse(list.isEmpty(), "List should not be empty.");
    }

    public static void assertListContains(List<String> actual, String expected) {
        boolean isMatched = false;
       for(String text : actual) {
           if(text.equalsIgnoreCase(expected)) {
               isMatched = true;
               break;
           }
       }
        Assert.assertTrue(isMatched, "Expected Input is not in the list.");
    }

    public static void assertValueEquals(String actual, String expected) {
        Assert.assertEquals(actual, expected, "actual not matched with expected.");
    }
}
