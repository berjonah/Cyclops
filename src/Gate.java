import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.io.Serializable;


abstract class Gate extends Subject implements IRenderable, Serializable {

    double currentXPos;
    double currentYPos;
    double currentScale;
    boolean state;

    //transient Area area;
    AffineTransform transform;

    boolean getState() {
        return this.state;
    }

    void setState(boolean state) {
        if(this.state != state) {
            this.state = state;
            notifyObservers();
        }
    }

    @Override
    public void notifyObservers() {
        for (IObserver IObserver : IObserverList) {
            IObserver.setState(getState());
            IObserver.update();
        }
    }

    public Gate(double xPos, double yPos, double scale) {
        currentXPos = xPos;
        currentYPos = yPos;
        currentScale = scale;

    }

    void updateTransform(double scale, double xPos, double yPos)
    {
        transform.setToTranslation(xPos,yPos);
        transform.scale(scale,scale);

        currentXPos = xPos;
        currentYPos = yPos;
        currentScale = scale;
    }

    protected abstract Area getArea();

    double getOutXPos(double offset) {
        return getArea().getBounds2D().getMaxX() + currentScale * offset;
    }

    double getOutYPos(double offset) {
        return getArea().getBounds2D().getCenterY() + currentScale * offset;
    }

    boolean contains(double xPos, double yPos) {
        return getArea().contains(xPos, yPos);
    }

    public abstract void clicked(double xPos, double yPos);

    public abstract Node getNode(int index);

    public abstract Node getNode();

    abstract double getNodeXPos(double offset, Node node);

    abstract double getNodeYPos(double offset, Node node);

    abstract void checkNodes();


}
