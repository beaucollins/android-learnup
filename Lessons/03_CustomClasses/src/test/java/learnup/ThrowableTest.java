package learnup;

import junit.framework.TestCase;

public class ThrowableTest extends TestCase {

    /**
     * ImmovableObject throws a RuntimeException if it's moved
     */
    public void testMoveUnmovable() {
        ImmovableObject movable = new ImmovableObject(1);

        movable.forward();

    }

    public void testAwesome() {

        Awesome awesome = new Awesome();

        // awesome.beMediocre();
    }


    /**
     * Exceptions that are not RuntimeExceptions (or descendants) must be
     * defined by the methods that will throw them
     */

    private class Awesome {

        void beMediocre() throws Exception {
            throw new Exception("I can only be awesome");
        }

    }

}