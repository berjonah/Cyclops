import javax.swing.SwingUtilities;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

class Model {

    private List<Gate> ModelGates;
    private List<Connection> connections;
    private Gate beingDragged;
    private Gate connectionStart;
    private Gate connectionEnd;
    private double scale;

    private ClickState clickState;

    private double MIN_SCALE = 5;
    private double MAX_SCALE = 50;

    Model() {
        scale = 10;
        ModelGates = new ArrayList<>();
        connections = new ArrayList<>();
        clickState = ClickState.DEFAULT;

        /*
        Gate s1 = GateFactory.getGate(GateType.SWITCH, 120, 120, scale);
        Gate s2 = GateFactory.getGate(GateType.SWITCH, 25, 25, scale);
        Gate bar = GateFactory.getGate(GateType.AND_GATE, 50, 50, scale);
        Gate foo = GateFactory.getGate(GateType.OR_GATE, 300, 20, scale);
        Gate fooBar = GateFactory.getGate(GateType.AND_GATE, 100, 350, scale);
        Gate not = GateFactory.getGate(GateType.NOT_GATE, 150, 30, scale);
        //connections.add(s1.attach(not));
        makeConnection(s1, not.getNode(0));
        //connections.add(not.attach(bar));
        makeConnection(not, bar.getNode(0));
        //connections.add(s2.attach(bar));
        makeConnection(s2, bar.getNode(1));
        //connections.add(s1.attach(foo));
        makeConnection(s1, foo.getNode(0));
        //connections.add(s4.attach(foo));
        makeConnection(s2, foo.getNode(1));
        //connections.add(foo.attach(fooBar));
        makeConnection(foo, fooBar.getNode(0));
        //connections.add(bar.attach(fooBar));
        makeConnection(bar, fooBar.getNode(1));
        ModelGates.add(s1);
        ModelGates.add(s2);
        ModelGates.add(bar);
        ModelGates.add(foo);
        ModelGates.add(fooBar);
        ModelGates.add(not);
        */
    }

    public void makeGate(GateType gateType, double xPos, double yPos) {
        ModelGates.add(GateFactory.getGate(gateType, xPos, yPos, scale));
    }

    private void makeConnection(Gate from, Node to) {
        from.registerObserver(to);
        to.setInUse(true);
        connections.add(new Connection(from, to));
        from.notifyObservers();
    }

    double getScale() {
        return scale;
    }

    void setScale(double xPos, double yPos, double scale) {
        this.scale = scale;
        for (Gate gate : ModelGates) {
            gate.updateScale(scale, xPos, yPos);
        }
    }

    private void addGate(GateType gateType, double xPos, double yPos) {
        Gate newGate = GateFactory.getGate(gateType, xPos, yPos, scale);
        ModelGates.add(newGate);
        newGate.checkNodes();
    }

    private void deleteGate(Gate gate) {
        ArrayList<Connection> connectionsToBeDeleted = new ArrayList<>();
        for (Connection connection: connections
             ) {
            if (connection.getInput() == gate || connection.getOutputGate() == gate)
            {
                connectionsToBeDeleted.add(connection);
            }
        }

        for (Connection connection:connectionsToBeDeleted
             ) {
            deleteConnection(connection);
            connection.setInput(null);
            connection.setOutput(null);
        }
        gate.setState(false);
        gate.notifyObservers();
        gate.removeAllObservers();
        ModelGates.remove(gate);
    }

    private void deleteConnection(Connection connection) {
        connection.getOutput().setInUse(false);
        connections.remove(connection);
    }

    void mouseClick(MouseEvent event) {
        boolean selected;
        switch (clickState)
        {
            case DEFAULT:
                for (Gate gate : ModelGates
                        ) {
                    gate.clicked(event.getX(), event.getY());
                }
                setClickState(ClickState.DEFAULT);
                break;
            case DELETE:
                boolean deleted = false;
                Gate gateToBeDeleted = null;
                for (Gate gate : ModelGates) {
                    if (gate.contains(event.getX(), event.getY()) && !deleted) {
                        gateToBeDeleted = gate;
                        deleted = true;
                    }
                }
                if(gateToBeDeleted != null) {
                    deleteGate(gateToBeDeleted);
                    setClickState(ClickState.DEFAULT);
                }
                else
                {
                    setClickState(ClickState.DELETE);
                }
                break;
            case CONNECTION_START:
                selected = false;
                for (Gate gate : ModelGates) {
                    if (gate.contains(event.getX(), event.getY()) && !selected) {
                        connectionStart = gate;
                        selected = true;
                    }
                }
                setClickState(ClickState.CONNECTION_END);
                break;
            case CONNECTION_END:
                selected = false;
                for (Gate gate : ModelGates) {
                    if (gate.contains(event.getX(), event.getY()) && !selected && gate != connectionStart) {
                        connectionEnd = gate;
                        makeConnection(connectionStart, connectionEnd.getNode());
                        selected = true;
                    }
                }
                if(selected)
                    setClickState(ClickState.DEFAULT);
                else
                    setClickState(ClickState.CONNECTION_END);
                break;
            case PLACE_AND_GATE:
                addGate(GateType.AND_GATE, event.getX(), event.getY());
                setClickState(ClickState.DEFAULT);
                break;
            case PLACE_OR_GATE:
                addGate(GateType.OR_GATE, event.getX(), event.getY());
                setClickState(ClickState.DEFAULT);
                break;
            case PLACE_NOT_GATE:
                addGate(GateType.NOT_GATE, event.getX(), event.getY());
                setClickState(ClickState.DEFAULT);
                break;
            case PLACE_SWITCH:
                addGate(GateType.SWITCH, event.getX(), event.getY());
                setClickState(ClickState.DEFAULT);
                break;
            default:
                //JOptionPane.showMessageDialog(null, "Something went wrong in the clickState switch in Model.mouseClick");
        }
    }

    void mouseDown(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            boolean selected;
            switch (clickState)
            {
                case DEFAULT:
                    selected = false;
                    for (Gate gate : ModelGates) {
                        if (gate.contains(event.getX(), event.getY()) && !selected) {
                            beingDragged = gate;
                            selected = true;
                        }
                        gate.previousXPos = event.getX();
                        gate.previousYPos = event.getY();
                    }
                    setClickState(clickState.DEFAULT);
                    break;
                case DELETE:
                    boolean deleted = false;
                    Gate gateToBeDeleted = null;
                    for (Gate gate : ModelGates) {
                        if (gate.contains(event.getX(), event.getY()) && !deleted) {
                            gateToBeDeleted = gate;
                            deleted = true;
                        }
                    }
                    if(gateToBeDeleted != null) {
                        deleteGate(gateToBeDeleted);
                        setClickState(ClickState.DEFAULT);
                    }
                    else
                    {
                        setClickState(ClickState.DELETE);
                    }
                    break;
                case CONNECTION_START:
                    selected = false;
                    for (Gate gate : ModelGates) {
                        if (gate.contains(event.getX(), event.getY()) && !selected) {
                            connectionStart = gate;
                            selected = true;
                        }
                    }
                    setClickState(ClickState.CONNECTION_END);
                    break;
                case CONNECTION_END:
                    selected = false;
                    for (Gate gate : ModelGates) {
                        if (gate.contains(event.getX(), event.getY()) && !selected && gate != connectionStart) {
                            connectionEnd = gate;
                            makeConnection(connectionStart, connectionEnd.getNode());
                            selected = true;
                        }
                    }
                    if(selected)
                        setClickState(ClickState.DEFAULT);
                    else
                        setClickState(ClickState.CONNECTION_END);
                    break;
                case PLACE_AND_GATE:
                    addGate(GateType.AND_GATE, event.getX(), event.getY());
                    setClickState(ClickState.DEFAULT);
                    break;
                case PLACE_OR_GATE:
                    addGate(GateType.OR_GATE, event.getX(), event.getY());
                    setClickState(ClickState.DEFAULT);
                    break;
                case PLACE_NOT_GATE:
                    addGate(GateType.NOT_GATE, event.getX(), event.getY());
                    setClickState(ClickState.DEFAULT);
                    break;
                case PLACE_SWITCH:
                    addGate(GateType.SWITCH, event.getX(), event.getY());
                    setClickState(ClickState.DEFAULT);
                    break;
                default:
                    //JOptionPane.showMessageDialog(null, "Something went wrong in the clickState switch in Model.mouseClick");
            }
        } else if (SwingUtilities.isRightMouseButton(event)) {
            for (Gate gate : ModelGates) {
                gate.previousXPos = event.getX();
                gate.previousYPos = event.getY();
            }
        }

    }

    void mouseUp(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            beingDragged = null;
        }
    }

    void mouseDrag(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            for (Gate gate : ModelGates) {
                if (gate.equals(beingDragged)) {
                    gate.updateTranslate(event.getX(), event.getY());
                }
            }
        } else if (SwingUtilities.isRightMouseButton(event)) {
            for (Gate gate : ModelGates) {
                gate.updateTranslate(event.getX(), event.getY());
            }
        }
    }


    List<? extends IRenderable> getVectorGraphics() {
        List<IRenderable> renderables = new ArrayList<>();
        renderables.addAll(connections);
        List<Gate> ReverseModelGates = new ArrayList<>();
        for (int i = ModelGates.size() - 1; i >= 0; i--) {
            ReverseModelGates.add(ModelGates.get(i));
        }
        renderables.addAll(ReverseModelGates);
        return renderables;
    }

    void MouseWheel(MouseWheelEvent event) {
        double scaleChange = event.getPreciseWheelRotation();

        if (scale - scaleChange >= MIN_SCALE && scale - scaleChange <= MAX_SCALE) {
            scale -= scaleChange;
            for (Gate gate : ModelGates) {
                gate.updateScale(scale, event.getX(), event.getY());
            }
        }
    }

    void setClickState(ClickState clickState) {
        this.clickState = clickState;
    }

    Cursor getCurrentCursor() {
        switch (clickState) {
            case DEFAULT:
                return new Cursor(Cursor.DEFAULT_CURSOR);
            case PLACE_AND_GATE:
            case PLACE_OR_GATE:
            case PLACE_NOT_GATE:
            case PLACE_SWITCH:
            case CONNECTION_START:
            case CONNECTION_END:
                return new Cursor(Cursor.CROSSHAIR_CURSOR);
            case DELETE:
                return new Cursor(Cursor.HAND_CURSOR);
            default:
                return new Cursor(Cursor.DEFAULT_CURSOR);
        }

    }
}


