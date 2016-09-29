import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.Color;

class OrGate extends BinaryGate {



    OrGate(double xPos, double yPos, double scale) {
        super(xPos, yPos, scale);

        GeneralPath path = new GeneralPath();
        path.moveTo(0, 0);
        path.lineTo(.5, 0);
        path.curveTo(1, 0, 1.5, 0, 2, 1);
        path.curveTo(1.5, 2, 1, 2, .5, 2);
        path.lineTo(0, 2);
        path.curveTo(.5, 1.5, .5, .5, 0, 0);
        path.closePath();

        area = new Area(path);

        transform = new AffineTransform();
        transform.setToIdentity();
        transform.translate(xPos, yPos);
        transform.scale(scale, scale);
        area.transform(transform);
    }

    @Override
    public void render(Graphics2D g) {
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
        return area.getBounds2D().getMinX() + currentScale * (offset + .30);
    }

    @Override
    public double getNodeYPos(double offset, Node node) {
        if (node == topNode) {
            return area.getBounds2D().getCenterY() + currentScale * (offset - .5);
        } else if (node == botNode) {
            return area.getBounds2D().getCenterY() + currentScale * (offset + .5);
        }
        return 0;
    }

    @Override
    public void checkNodes() {
        setState(topNode.getState() || botNode.getState());
    }
}


