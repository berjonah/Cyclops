import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

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

    public void registerObserver(IObserver iObserver)
    {
        IObserverList.add(iObserver);
    }
    public abstract void notifyObservers();

    public void removeObserver(IObserver iObserver)
    {
        IObserverList.remove(iObserver);
    }

    public void removeAllObservers()
    {
        IObserverList.removeAll(IObserverList);
    }

}
