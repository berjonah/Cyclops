/**
 * Created by Andrew Fillmore on 8/4/2016.
 */
public class GateFactory {

    static public Gate getGate(GateType gateType, double xPos, double yPos, double scale)
    {
        switch (gateType)
        {
            case AND_GATE:
                return new AndGate(xPos,yPos,scale);
            case OR_GATE:
                return new OrGate(xPos, yPos, scale);
            case NOT_GATE:
                return new NotGate(xPos, yPos, scale);
            case SWITCH:
                return new Switch(xPos, yPos, scale);
            default:
                return null;
        }
    }
}


