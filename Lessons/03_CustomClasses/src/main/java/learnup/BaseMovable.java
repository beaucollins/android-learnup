package learnup;

public abstract class BaseMovable implements Movable {

    public static final int DEFAULT_POSITION = 0;

    private int mPosition;

    public BaseMovable(int startPosition) {
        setPosition(startPosition);
    }

    public BaseMovable() {
        this(DEFAULT_POSITION);
    }

    abstract public int getMoveBy();

    @Override
    public void forward() {
        mPosition += getMoveBy();
    }

    @Override
    public void backward() {
        mPosition -= getMoveBy();
    }

    @Override
    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

}