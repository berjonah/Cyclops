import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;


class AndGate extends BinaryGate{

    AndGate(double xPos, double yPos, double scale) {
        super(xPos, yPos, scale);

        //area = new Area(new Rectangle2D.Double(0, 0, 1, 2));
        //Area Circle = new Area(new Ellipse2D.Double(0, 0, 2, 2));
        //area.add(Circle);

        transform = new AffineTransform();
        transform.setToIdentity();
        transform.translate(xPos, yPos);
        transform.scale(scale, scale);
        //getArea().transform(transform);

    }

    @Override
    protected Area getArea()
    {
        Area area = new Area(new Rectangle2D.Double(0, 0, 1, 2));
        Area Circle = new Area(new Ellipse2D.Double(0, 0, 2, 2));
        area.add(Circle);
        area.transform(transform);
        return area;
    }

    @Override
    public void render(Graphics2D g)
    {
        Area area = getArea();
        g.setColor(Color.WHITE);
        g.fill(area);
        if (getState())
            g.setColor(Color.green);
        else
            g.setColor(Color.red);
        g.draw(area);
    }

    @Override
    public void clicked(double xPos, double yPos) {
    }


    @Override
    public double getNodeXPos(double offset, Node node) {
        Area area = getArea();

        return area.getBounds2D().getMinX() + currentScale * offset;
    }

    @Override
    public double getNodeYPos(double offset, Node node)
    {
        Area area = getArea();


        if (node == topNode) {
            return area.getBounds2D().getCenterY() + currentScale * (offset - .5);
        } else if (node == botNode) {
            return area.getBounds2D().getCenterY() + currentScale * (offset + .5);
        }
        return 0;
    }

    @Override
    public void checkNodes() {
        setState(topNode.getState() && botNode.getState());
    }
}
