import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Fillmore on 1/28/2017.
 */
public class Data implements Serializable {

    private List<Gate> ModelGates;
    private List<Connection> connections;
    private double scale;

    public Data() {
        this.ModelGates = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    public List<Gate> getModelGates() {
        return ModelGates;
    }

    public void setModelGates(List<Gate> modelGates) {
        ModelGates = modelGates;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }




}
