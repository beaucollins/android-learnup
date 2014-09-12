package learnup;

import junit.framework.TestCase;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class CollectionsTest extends TestCase {

    /**
     * java.util.List is an interface that represents an object that contains
     * a list of other objects.
     *
     * java.util.ArrayList is a java.util.List of that interface that uses an
     * array for its implementation of the List interface.
     */
    public void testArrayList() {

        // define a new ArrayList
        List<String> list = new ArrayList<String>();

        assertEquals(0, list.size());

        list.add("Hello");

        assertEquals(1, list.size());

        assertEquals("Hello", list.get(0));

        list.add("World");

        String message = "";
        for (Object item : list) {
            message += item + " ";
        }

        assertEquals("Hello World ", message);
    }

    /**
     * A java.util.Map is an interface that represents is a key/value pair storage
     * container.
     *
     * java.util.HashMap is a
     */
    public void testHashMap() {
        Map<Integer,String> ordinals = new HashMap<Integer,String>();

        ordinals.put(1, "first");
        ordinals.put(2, "second");
        ordinals.put(3, "third");
        ordinals.put(12, "twelfth");

        assertEquals(4, ordinals.size());

        assertEquals("second", ordinals.get(2));

        String name = ordinals.remove(12);

        assertEquals(3, ordinals.size());
        assertEquals("twelfth", name);

    }

}