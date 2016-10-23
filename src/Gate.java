import java.awt.geom.AffineTransform;
import java.awt.geom.Area;


abstract class Gate extends Subject implements IRenderable {

    double previousXPos;
    double previousYPos;
    double currentScale;
    boolean state;

    Area area;
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
        previousXPos = xPos;
        previousYPos = yPos;
        currentScale = scale;

    }

    void updateTranslate(double xPos, double yPos) {
        transform.setToIdentity();
        transform.translate(-previousXPos, -previousYPos);
        transform.translate(xPos, yPos);
        area.transform(transform);

        previousXPos = xPos;
        previousYPos = yPos;
    }

    void updateScale(double amount, double xPos, double yPos) {
        transform.setToIdentity();
        transform.translate(xPos, yPos);
        transform.scale(1 / currentScale, 1 / currentScale);
        transform.scale(amount, amount);
        transform.translate(-xPos, -yPos);
        area.transform(transform);
        currentScale = amount;
    }


    double getOutXPos(double offset) {
        return area.getBounds2D().getMaxX() + currentScale * offset;
    }

    double getOutYPos(double offset) {
        return area.getBounds2D().getCenterY() + currentScale * offset;
    }


    boolean contains(double xPos, double yPos) {
        return area.contains(xPos, yPos);
    }

    public abstract void clicked(double xPos, double yPos);

    public abstract Node getNode(int index);

    public abstract Node getNode();

    abstract double getNodeXPos(double offset, Node node);

    abstract double getNodeYPos(double offset, Node node);

    abstract void checkNodes();


}
