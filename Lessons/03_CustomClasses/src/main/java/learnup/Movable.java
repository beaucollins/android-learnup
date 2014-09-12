package learnup;

public interface Movable {

    void forward() throws ImmovableObject.NotMovable;
    void backward() throws ImmovableObject.NotMovable;
    void setPosition(int position) throws ImmovableObject.NotMovable;
    int getPosition();

}