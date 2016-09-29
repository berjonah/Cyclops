import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.Color;


class NotGate extends UnaryGate {

    NotGate(double xPos, double yPos , double scale)
    {
        super(xPos, yPos, scale);

        GeneralPath path = new GeneralPath();

        path.moveTo(0,0);
        path.lineTo(0,2);
        path.lineTo(Math.sqrt(3),1);
        path.closePath();

        area = new Area(path);
        Area Circle = new Area(new Ellipse2D.Double(area.getBounds2D().getMaxX(),area.getBounds2D().getCenterY() - .15,0.3,0.3));
        area.add(Circle);
        transform = new AffineTransform();
        transform.setToIdentity();
        transform.translate(xPos, yPos);
        transform.scale(scale,scale);
        area.transform(transform);
    }

    @Override
    public void render(Graphics2D g)
    {
        g.setColor(Color.WHITE);
        g.fill(area);
        if(getState())
            g.setColor(Color.green);
        else
            g.setColor(Color.red);
        g.draw(area);
    }

    @Override
    public void clicked(double xPos, double yPos)
    {}

    @Override
    public double getNodeXPos(double offset, Node node)
    {
        return area.getBounds2D().getMinX() + currentScale * offset;
    }

    @Override
    public double getNodeYPos(double offset, Node node)
    {
        return area.getBounds2D().getCenterY() + currentScale * offset;
    }

    @Override
    public void checkNodes()
    {
        setState(!node.getState());
    }
}
