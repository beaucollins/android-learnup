package learnup;

import junit.framework.TestCase;

public class PrimitivesTest extends TestCase {

    /**
     * Playing with primitives. Primitives are more memory efficient and do not
     * require garbage collection so are useful in performance critical code paths.
     *
     * See http://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
     */
    public void testPrimitives() {

        // int is an 32-bit integer
        int i = 1;
        // float
        float f = 1.5f;

        // obviously 1 and 1.5 are not equal
        assertFalse(i == f);

        // but casting a float to an int causes a loss in precision
        // so not they are equal
        assertTrue(i == (int)f);

        // short is a 16-bit integer
        short s = 2;
        // long is a 32-bit integer
        long l = 2;

        assertEquals(s, l);

        boolean isTrue = true;
        boolean isFalse = false;

        assertEquals(isTrue, !isFalse);

    }

    /**
     * When a primitive is casted to it's Object equivalent, it is considered
     * "boxed" (e.g. it is now contained inside an Object).
     *
     * The compiler will automatically box and un-box primitive values when needed.
     *
     * Boxed primitives take up memory on the "heap" and require garbage collection
     */
    public void testBoxing() {

        // a "boxed" int is an instance of Integer
        Integer a = new Integer(5);
        Object o = new Object();

        // primitive int is not an Object instance
        int b = 5;

        // Object.equals expects an Object as the argument so
        // the `int b` is boxed to an Integer
        assertTrue(a.equals(b));

        // when comparing an Integer to a primitive int, the primitive is boxed
        assertTrue(a == b);

    }

    /**
     * Equality can be tricky when dealing with boxed primitives.
     *
     * In this example, a and b are two different instance of Integer represent "5".
     * They are equal in value, but they are _not_ the same instance.
     */
    public void testObjectInstance() {

        // two different Integer instances of int 5
        Integer a = new Integer(5);
        Integer b = new Integer(5);

        // a and b are equal to each other
        assertEquals(a, b);

        // however they are not the same instance
        assertFalse(a == b);

    }

    /**
     * Strings are somewhat of a special case. They can be declared with a literal value
     * like primitives, but there is not primitive version of String.
     *
     * Strings are backed by an array of char[]
     */
    public void testCharsAndStrings() {
        // define a String with a literal value using double quotes
        String hello = "hello world";

        // a char is defined with single quotes
        char h = 'h';

        assertEquals(0, hello.indexOf(h));

    }

}