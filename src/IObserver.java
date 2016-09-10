/**
 * Created by Andrew Fillmore on 9/3/2016.
 */
public interface IObserver {
    void update();
    void setState(boolean state);
    boolean getState();
}
