import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

class Switch extends Gate
{


    Switch(double xPos, double yPos, double scale)
    {
        super(xPos,yPos,scale);
        area = new Area(new Rectangle2D.Double(0,0,1,1));
        transform = new AffineTransform();
        transform.setToIdentity();
        transform.translate(xPos,yPos);
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
    {
        if(contains(xPos,yPos)) {
            setState(!state);
        }

    }

    @Override
    public Node getNode(int index)
    {
        throw new IllegalArgumentException("Switches have no nodes");
    }

    @Override
    public double getNodeXPos(double offset, Node node)
    {
        throw new IllegalArgumentException("Switches have no nodes");
    }

    @Override
    public double getNodeYPos(double offset, Node node)
    {
        throw new IllegalArgumentException("Switches have no nodes");
    }

    @Override
    public void checkNodes()
    {

    }
}
