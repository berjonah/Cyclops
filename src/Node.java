import java.awt.*;

/**
 * Created by Andrew Fillmore on 9/10/2016.
 */
public class Node implements IObserver{

    Gate gate;
    boolean state;

    public Node(Gate gate)
    {
        this.gate = gate;
    }




    public double getXPos(double offset)
    {
        return gate.getNodeXPos(offset, this);
    }

    public double getYPos(double offset)
    {
        return  gate.getNodeYPos(offset, this);
    }

    @Override
    public void setState(boolean state)
    {
        this.state = state;
    }
    @Override
    public boolean getState()
    {
        return this.state;
    }

    @Override
    public void update()
    {
        gate.checkNodes();
    }
}
