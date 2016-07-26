import java.awt.*;
import java.util.Random;

/**
 * Created by Andrew Fillmore on 7/25/2016.
 */
public class Model {
    public IRenderable[] getVectorGraphics(int x, int y)
    {
        IRenderable[] array = new AndGate[1];
        array[0] = new AndGate(x,y);
        return array;
    }

    private class AndGate implements IRenderable
    {
        int x,y;
        Random gen;

        public AndGate(int x, int y)
        {
            this.x=x;
            this.y=y;
            gen = new Random();
        }
        @Override
        public void render(Graphics g)
        {
            g.drawOval(x-10,y-10,20,20);
            g.drawRect(x-20,y-20,40,40);
        }
    }
}


