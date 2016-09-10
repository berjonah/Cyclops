import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

class AndGate extends Gate {

    private Node topNode;
    private Node botNode;

    AndGate(double xPos, double yPos, double scale)
    {
        super(xPos,yPos, scale);

        topNode = new Node(this);
        botNode = new Node(this);

        area = new Area(new Rectangle2D.Double(0,0,1,2));
        Area Circle = new Area(new Ellipse2D.Double(0,0,2,2));
        area.add(Circle);

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
    }

    @Override
    public Node getNode(int index)
    {
        switch (index)
        {
            case 0:
                return topNode;
            case 1:
                return botNode;
            default:
                throw new IllegalArgumentException("Argument must be 0 or 1 for binary gate");
        }

    }

    @Override
    public double getNodeXPos(double offset, Node node)
    {
        return area.getBounds2D().getMinX() + currentScale * offset;
    }

    @Override
    public double getNodeYPos(double offset, Node node){
        if(node == topNode) {
            return area.getBounds2D().getCenterY() + currentScale * (offset-.5);
        }
        else if(node == botNode)
        {
            return area.getBounds2D().getCenterY() + currentScale * (offset+.5);
        }
        return 0;
    }

    @Override
    public void checkNodes()
    {
        setState(topNode.getState() && botNode.getState());
    }
}
