package learnup;

import java.util.List;
import java.util.ArrayList;

public class Box extends BaseMovable {


    final private List<Movable> mContents;

    public Box(int startPosition, int numItems) {
        super(startPosition);
        mContents = new ArrayList<Movable>(numItems);
    }

    public Box(int startPosition) {
        this(startPosition, 5);
    }

    @Override
    public int getMoveBy() {
        return 1;
    }

    public void addItem(Movable item) {
        item.setPosition(getPosition());
        mContents.add(item);
    }

    public List<Movable> getContents() {
        return mContents;
    }

    @Override
    public void forward() {
        super.forward();
        updateContentsPosition();
    }

    @Override
    public void backward() {
        super.backward();
        updateContentsPosition();
    }

    @Override
    public void setPosition(int position) {
        super.setPosition(position);
        updateContentsPosition();
    }

    private void updateContentsPosition() {
        if (mContents == null) {
            return;
        }

        int position = getPosition();
        for(Movable item : mContents) {
            item.setPosition(position);
        }
    }

}