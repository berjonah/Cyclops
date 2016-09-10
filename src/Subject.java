import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Fillmore on 9/3/2016.
 */
public abstract class Subject {
    List<IObserver> IObserverList;

    public Subject()
    {
        IObserverList = new ArrayList<>();
    }

    public void registerObserver(IObserver IObserver)
    {
        IObserverList.add(IObserver);
    }
    public abstract void notifyObservers();

}
