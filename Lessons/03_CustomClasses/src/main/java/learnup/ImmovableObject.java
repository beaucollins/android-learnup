package learnup;

import com.sun.tools.corba.se.idl.constExpr.Not;

public class ImmovableObject implements Movable {

    public static class NotMovable extends RuntimeException {
        NotMovable() {
            super("This object cannot be moved");
        }
    }

    private final int mPosition;

    public ImmovableObject(int position) {
        mPosition = position;
    }

    @Override
    public void forward() {
        throw new NotMovable();
    }

    @Override
    public void backward() {
        throw new NotMovable();

    }

    @Override
    public void setPosition(int position) {
        throw new NotMovable();
    }

    @Override
    public int getPosition() {
        return mPosition;
    }
}
