import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;


abstract class Gate extends Subject implements IRenderable {

    protected double previousXPos;
    protected double previousYPos;
    protected double currentScale;
    protected boolean state;

    Area area;
    AffineTransform transform;

    public boolean getState()
    {
        return this.state;
    }

    public void setState(boolean state)
    {
        this.state = state;
        notifyObservers();
    }

    @Override
    public  void notifyObservers()
    {
        for (IObserver IObserver : IObserverList) {
            IObserver.setState(getState());
            IObserver.update();
        }
    }

    public Gate(double xPos, double yPos, double scale)
    {
        previousXPos = xPos;
        previousYPos = yPos;
        currentScale = scale;

    }

    public void updateTranslate(double xPos, double yPos)
    {
        transform.setToIdentity();
        transform.translate(-previousXPos,-previousYPos);
        transform.translate(xPos,yPos);
        area.transform(transform);

        previousXPos = xPos;
        previousYPos = yPos;
    }

    public void updateScale(double amount, double xPos, double yPos)
    {
        transform.setToIdentity();
        transform.translate(xPos,yPos);
        transform.scale(1/currentScale, 1/currentScale);
        transform.scale(amount,amount);
        transform.translate(-xPos,-yPos);
        area.transform(transform);
        currentScale = amount;
    }



    public double getOutXPos(double offset) {
        return area.getBounds2D().getMaxX() + currentScale * offset;
    }
    public double getOutYPos(double offset) {
        return area.getBounds2D().getCenterY() + currentScale * offset;
    }


    protected boolean contains(double xPos, double yPos)
    {
        return area.contains(xPos,yPos);
    }

    public abstract void clicked(double xPos, double yPos);
    public abstract Node getNode(int index);
    abstract double getNodeXPos(double offset, Node node);
    abstract double getNodeYPos(double offset, Node node);
    abstract void checkNodes();



}
