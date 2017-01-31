import javax.swing.SwingUtilities;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Model {


    private Data data;
    private Gate beingDragged;
    private Gate connectionStart;
    private Gate connectionEnd;
    //private double scale;

    private double mousePrevX;
    private double mousePrevY;

    private ClickState clickState;

    private double MIN_SCALE = 5;
    private double MAX_SCALE = 50;

    Model() {

        data = new Data();
        data.setScale(10);
        clickState = ClickState.DEFAULT;

    }

    /*public void makeGate(GateType gateType, double xPos, double yPos) {
        data.getModelGates().add(GateFactory.getGate(gateType, xPos, yPos, scale));
    }
    */

    private void makeConnection(Gate from, Node to) {
        from.registerObserver(to);
        to.setInUse(true);
        data.getConnections().add(new Connection(from, to));
        from.notifyObservers();
    }

    double getScale() {
        return data.getScale();
    }

    void setScale(double scale, double focusX, double focusY) {

        for (Gate gate : data.getModelGates()) {
            gate.updateTransform(scale,
                    focusX + ((scale)/ data.getScale()) * (gate.currentXPos - focusX),
                    focusY + ((scale)/ data.getScale()) * (gate.currentYPos - focusY));
        }
        data.setScale(scale);
    }


    private void addGate(GateType gateType, double xPos, double yPos) {
        Gate newGate = GateFactory.getGate(gateType, xPos, yPos, data.getScale());
        data.getModelGates().add(newGate);
        newGate.checkNodes();
    }

    private void deleteGate(Gate gate) {
        ArrayList<Connection> connectionsToBeDeleted = new ArrayList<>();
        for (Connection connection: data.getConnections()
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
        data.getModelGates().remove(gate);
    }

    private void deleteConnection(Connection connection) {
        connection.getOutput().setInUse(false);
        data.getConnections().remove(connection);
    }

    void mouseClick(MouseEvent event) {
        boolean selected;
        switch (clickState)
        {
            case DEFAULT:
                for (Gate gate : data.getModelGates()
                        ) {
                    gate.clicked(event.getX(), event.getY());
                }
                setClickState(ClickState.DEFAULT);
                break;
            case DELETE:
                boolean deleted = false;
                Gate gateToBeDeleted = null;
                for (Gate gate : data.getModelGates()) {
                    if (gate.contains(event.getX(), event.getY()) && !deleted) {
                        gateToBeDeleted = gate;
                        deleted = true;
                    }
                }
                if(gateToBeDeleted != null) {
                    deleteGate(gateToBeDeleted);
                }
                setClickState(ClickState.DELETE);
                break;
            case CONNECTION_START:
                selected = false;
                for (Gate gate : data.getModelGates()) {
                    if (gate.contains(event.getX(), event.getY()) && !selected) {
                        connectionStart = gate;
                        selected = true;
                    }
                }
                setClickState(ClickState.CONNECTION_END);
                break;
            case CONNECTION_END:
                selected = false;
                for (Gate gate : data.getModelGates()) {
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
                    for (Gate gate : data.getModelGates()) {
                        if (gate.contains(event.getX(), event.getY()) && !selected) {
                            beingDragged = gate;
                            selected = true;
                        }
                        mousePrevX = event.getX();
                        mousePrevY = event.getY();
                    }
                    setClickState(clickState.DEFAULT);
                    break;
                case DELETE:
                    boolean deleted = false;
                    Gate gateToBeDeleted = null;
                    for (Gate gate : data.getModelGates()) {
                        if (gate.contains(event.getX(), event.getY()) && !deleted) {
                            gateToBeDeleted = gate;
                            deleted = true;
                        }
                    }
                    if(gateToBeDeleted != null) {
                        deleteGate(gateToBeDeleted);
                    }
                    setClickState(ClickState.DELETE);
                    break;
                case CONNECTION_START:
                    selected = false;
                    for (Gate gate : data.getModelGates()) {
                        if (gate.contains(event.getX(), event.getY()) && !selected) {
                            connectionStart = gate;
                            selected = true;
                        }
                    }
                    setClickState(ClickState.CONNECTION_END);
                    break;
                case CONNECTION_END:
                    selected = false;
                    for (Gate gate : data.getModelGates()) {
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
            mousePrevX = event.getX();
            mousePrevY = event.getY();
        }

    }

    void mouseUp(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            beingDragged = null;
        }
    }

    void mouseDrag(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            for (Gate gate : data.getModelGates()) {
                if (gate.equals(beingDragged)) {
                    gate.updateTransform(data.getScale(), event.getX() - mousePrevX + gate.currentXPos, event.getY() - mousePrevY + gate.currentYPos);
                }
            }
        } else if (SwingUtilities.isRightMouseButton(event)) {
            for (Gate gate : data.getModelGates()) {
                gate.updateTransform(data.getScale(), event.getX() - mousePrevX + gate.currentXPos, event.getY() - mousePrevY + gate.currentYPos);
            }
        }
        mousePrevX = event.getX();
        mousePrevY = event.getY();
    }


    List<? extends IRenderable> getVectorGraphics() {
        List<IRenderable> renderables = new ArrayList<>();
        renderables.addAll(data.getConnections());
        List<Gate> ReverseModelGates = new ArrayList<>();
        for (int i = data.getModelGates().size() - 1; i >= 0; i--) {
            ReverseModelGates.add(data.getModelGates().get(i));
        }
        renderables.addAll(ReverseModelGates);
        return renderables;
    }

    void MouseWheel(MouseWheelEvent event) {
        double scaleChange = -event.getPreciseWheelRotation();
        if (data.getScale() + scaleChange >= MIN_SCALE && data.getScale() + scaleChange <= MAX_SCALE) {
            for (Gate gate : data.getModelGates()) {
                gate.updateTransform(data.getScale() + scaleChange,
                        event.getX() + ((data.getScale() + scaleChange)/ data.getScale()) * (gate.currentXPos - event.getX()),
                        event.getY() + ((data.getScale() + scaleChange)/ data.getScale()) * (gate.currentYPos - event.getY()));
            }
            data.setScale(data.getScale() +scaleChange);
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

    boolean save(File file)
    {

        ObjectOutputStream objectOutputStream;
        try {
             objectOutputStream = new ObjectOutputStream(Files.newOutputStream(file.toPath()));
             objectOutputStream.writeObject(data);
             objectOutputStream.close();
        }
        catch (IOException ioException) {
            System.err.println("Error writing to file");
            System.err.println(file.toString());
            System.err.println(ioException.toString());
            System.err.println(ioException.getMessage());
            return false;
        }
        return true;
    }

    boolean open(File file)
    {
        ObjectInputStream objectInputStream;
        try
        {
            objectInputStream = new ObjectInputStream(Files.newInputStream(file.toPath()));
            data = (Data) objectInputStream.readObject();
        }
        catch (IOException ioException) {
            System.err.println("Error reading file");
            System.err.println(file.toString());
            System.err.println(ioException.toString());
            System.err.println(ioException.getMessage());
            return false;
        }
        catch (ClassNotFoundException classNotFoundException)
        {
            System.err.println("Error reading file");
            System.err.println(file.toString());
            System.err.println(classNotFoundException.toString());
            System.err.println(classNotFoundException.getMessage());
            return false;
        }
        return true;
    }
}


