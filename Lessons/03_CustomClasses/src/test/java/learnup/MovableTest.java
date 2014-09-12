package learnup;

import junit.framework.TestCase;

public class MovableTest extends TestCase {

    int mWallMoveAttempts = 0;

    public void testBase() {
        BaseMovable chair = new BaseMovable(3) {
            @Override
            public int getMoveBy() {
                return 2;
            }
        };

        chair.forward();

        assertEquals(5, chair.getPosition());

        chair.backward();

        assertEquals(3, chair.getPosition());

    }

    public void testBox() {

        Box box = new Box(5);
        BaseMovable pencil = new BaseMovable(10) {
            @Override
            public int getMoveBy() {
                return 1;
            }
        };

        // put the pencil in the box
        box.addItem(pencil);

        // pencil is now at the same position as the box
        assertEquals(5, pencil.getPosition());

        box.forward();

        assertEquals(6, box.getPosition());
        assertEquals(6, pencil.getPosition());
    }

    public void testWall() {
        Wall wall = new Wall(5);

        wall.forward();
        wall.backward();

        // while it presents a movable interface, it didn't move
        assertEquals(5, wall.getPosition());

        assertEquals(2, mWallMoveAttempts);
    }

    private class Wall implements Movable {

        private final int mPosition;

        Wall(int position) {
            mPosition = position;
        }

        @Override
        public void forward() {
            mWallMoveAttempts ++;
        }

        @Override
        public void backward() {
            mWallMoveAttempts ++;
        }

        @Override
        public void setPosition(int position) {
            mWallMoveAttempts ++;
        }

        @Override
        public int getPosition() {
            return mPosition;
        }
    }

}