import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

class Model {
    
    private List<Gate> ModelGates;
    private List<Connection> connections;
    private Gate beingDragged;
    private double scale;
    public double MIN_SCALE = 5;
    public double MAX_SCALE = 50;

    public Model()
    {
        scale = 10;
        ModelGates = new ArrayList<>();
        connections = new ArrayList<>();
        Gate s1 = GateFactory.getGate(GateType.SWITCH,120,120,scale);
        Gate s2 = GateFactory.getGate(GateType.SWITCH,25,25,scale);
        Gate bar = GateFactory.getGate(GateType.OR_GATE,50,50,scale);
        Gate foo = GateFactory.getGate(GateType.OR_GATE,300,20,scale);
        Gate fooBar = GateFactory.getGate(GateType.AND_GATE,100,350,scale);
        Gate not = GateFactory.getGate(GateType.NOT_GATE,150,30,scale);
        //connections.add(s1.attach(not));
        makeConnection(s1,not.getNode(0));
        //connections.add(not.attach(bar));
        makeConnection(not, bar.getNode(0));
        //connections.add(s2.attach(bar));
        makeConnection(s2,bar.getNode(1));
        //connections.add(s1.attach(foo));
        makeConnection(s1,foo.getNode(0));
        //connections.add(s4.attach(foo));
        makeConnection(s2,foo.getNode(1));
        //connections.add(foo.attach(fooBar));
        makeConnection(foo,fooBar.getNode(0));
        //connections.add(bar.attach(fooBar));
        makeConnection(bar,fooBar.getNode(1));
        ModelGates.add(s1);
        ModelGates.add(s2);
        ModelGates.add(bar);
        ModelGates.add(foo);
        ModelGates.add(fooBar);
        ModelGates.add(not);
    }

    public void makeGate(GateType gateType, double xPos, double yPos)
    {
        ModelGates.add(GateFactory.getGate(gateType,xPos,yPos,scale));
    }

    public void makeConnection(Gate from, Node to)
    {
        from.registerObserver(to);
        connections.add(new Connection(from,to));
    }

    public double getScale()
    {
        return scale;
    }

    public void setScale(double xPos, double yPos, double scale)
    {
        this.scale = scale;
        for (Gate gate : ModelGates) {
            gate.updateScale(scale,xPos,yPos);
        }
    }
    public void addGate( GateType gateType, double xPos, double yPos)
    {
        ModelGates.add(GateFactory.getGate(gateType,xPos,yPos, scale));
    }

    public void mouseClick(MouseEvent event)
    {
        for (Gate gate : ModelGates
             ) {
            gate.clicked(event.getX(),event.getY());
        }
    }

    public void mouseDown(MouseEvent event)
    {
        if(SwingUtilities.isLeftMouseButton(event))
        {
            boolean selected = false;
            for (Gate gate : ModelGates) {
                if (gate.contains(event.getX(), event.getY()) && !selected) {
                    beingDragged = gate;
                    selected = true;
                }
                gate.previousXPos = event.getX();
                gate.previousYPos = event.getY();
            }
        }
        else if(SwingUtilities.isRightMouseButton(event))
        {
            for (Gate gate: ModelGates) {
                gate.previousXPos = event.getX();
                gate.previousYPos = event.getY();
            }
        }

    }

    public void mouseUp(MouseEvent event)
    {
        if(SwingUtilities.isLeftMouseButton(event)) {
            beingDragged = null;
        }
    }

    public void mouseDrag(MouseEvent event)
    {
        if(SwingUtilities.isLeftMouseButton(event))
        {
            for (Gate gate: ModelGates) {
                if(gate.equals(beingDragged))
                {
                    gate.updateTranslate(event.getX(),event.getY());
                }
            }
        }
        else if(SwingUtilities.isRightMouseButton(event))
        {
            for (Gate gate: ModelGates) {
                gate.updateTranslate(event.getX(),event.getY());
            }
        }
    }
    
    
    public List<? extends IRenderable> getVectorGraphics()
    {
        List<IRenderable> renderables = new ArrayList<>();
        renderables.addAll(connections);
        List<Gate> ReverseModelGates = new ArrayList<>();
        for(int i=ModelGates.size()-1; i>=0;i--)
        {
            ReverseModelGates.add(ModelGates.get(i));
        }
        renderables.addAll(ReverseModelGates);
        return renderables;
    }

    public void MouseWheel(MouseWheelEvent event)
    {
        double scaleChange = event.getPreciseWheelRotation();

        if(scale - scaleChange >= MIN_SCALE && scale - scaleChange <= MAX_SCALE) {
            scale -= scaleChange;
            for (Gate gate : ModelGates) {
                gate.updateScale(scale, event.getX(), event.getY());
            }

        }

    }
}


