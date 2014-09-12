package learnup;

import junit.framework.TestCase;

/**
 * Arrays are storage containers for a lists of things, their capacity must be
 * provided when they are defined and cannot be changed.
 */
public class ArrayTest extends TestCase {

    public void testIntArray() {
        // arrays are defined by using "[]" after a datatype

        // this int[] array is defined in-place with a capacity of 5
        int[] odd = new int[] { 1, 3, 5, 7, 9 };

        assertEquals(5, odd.length);

        // members of the array are then referenced by index
        assertEquals(odd[0], 1);

        // the "even" array is defined with a capacity of 3
        int[] even = new int[3];

        assertEquals(even.length, 3);

        even[0] = 2;
        even[1] = 4;
        even[2] = 6;

        // attempting to assign a value beyond the capacity of the array will throw
        // an ArrayIndexOutOfBoundsException
        try {
            even[3] = 8;
        } catch (ArrayIndexOutOfBoundsException e) {
            assertEquals("3", e.getMessage());
        }

        // arrays can have their values replaced
        even[1] = 6;
        even[2] = 8;

        assertEquals(14, even[1] + even[2]);

    }

    public void testLoopArray() {

        Object[] objects = new Object[]{ 1, 2, 3};

        // a common for loop
        for (int i = 0; i < objects.length; i++) {
            // Look, the int values have been boxed!
            assertEquals(Integer.class, objects[i].getClass());
        }

        int i = 0;
        // fast iteration
        for (Object o : objects) {
            assertEquals(Integer.class, objects[i].getClass());
            i++;
        }

        assertEquals(3, i);

    }

    /*
    // TODO: compute the total and make the test pass
    public void testIntArrayAverage() {

        int[] values = new int[]{ 2, 6, 8, 11 };


        assertEquals(27, total);
    }
    */

}
