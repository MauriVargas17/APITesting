import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class tareaJSONComparison {

    public boolean compareJSON(JSONObject expected, JSONObject actual){
        int differences = 0;

        for (String key : actual.keySet()) {
            try {
                Object expectedValue = expected.get(key);
                Object actualValue = actual.get(key);

                if (!"ignore".equals(expectedValue) && !expectedValue.equals(actualValue)) {
                    System.out.println("Expected: " + expectedValue + ", Actual: " + actualValue);
                    differences++;
                }
            } catch (JSONException e) {
                System.out.println("Issue: "+e.getMessage());
            }
        }

        return differences > 0;

    }

    @Test
    public void testCompareJSON_SameValues() {
        JSONObject expected = new JSONObject("{\"key1\":\"foobar\",\"key2\":\"ignore\",\"key3\":\"baz\"}");
        JSONObject actual = new JSONObject("{\"key1\":\"foobar\",\"key2\":\"fly\",\"key3\":\"baz\"}");
        boolean differences = compareJSON(expected, actual);
        assertEquals(false, differences);
    }

    @Test
    public void testCompareJSON_DifferentValues() {
        JSONObject expected = new JSONObject("{\"key1\":\"foobar\",\"key2\":\"ignore\",\"key3\":\"bazo\"}");
        JSONObject actual = new JSONObject("{\"key1\":\"boefar\",\"key2\":\"fly\",\"key3\":\"baz\"}");
        boolean differences = compareJSON(expected, actual);
        assertEquals(true, differences);
    }

    @Test
    public void testCompareJSON_InvalidKey() {
        JSONObject expected = new JSONObject("{\"key1\":\"foobar\",\"key2\":\"ignore\",\"key3\":\"baz\"}");
        JSONObject actual = new JSONObject();
        actual.put("key", "12");
        assertDoesNotThrow(() -> compareJSON(expected, actual));
    }


}
