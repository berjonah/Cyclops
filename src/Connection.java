import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;
import java.awt.Color;


class Connection implements IRenderable {
    private Gate input;
    private Node output;
    private CubicCurve2D cubicCurve2D;

    Connection(Gate input, Node output) {
        this.input = input;
        this.output = output;
        cubicCurve2D = new CubicCurve2D.Double();
    }

    @Override
    public void render(Graphics2D graphics2D) {
        if (input.getState())
            graphics2D.setColor(Color.GREEN);
        else
            graphics2D.setColor(Color.RED);
        cubicCurve2D.setCurve(input.getOutXPos(0), input.getOutYPos(0),
                input.getOutXPos(2), input.getOutYPos(0),
                output.getXPos(-2), output.getYPos(0),
                output.getXPos(0), output.getYPos(0));
        graphics2D.draw(cubicCurve2D);

    }
}


