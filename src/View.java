import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class View extends JFrame
{

    private Canvas canvas;

    public View()
    {
        canvas = new Canvas();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,600);
        this.setLayout(new FlowLayout());

        canvas.setPreferredSize(new Dimension(500,500));
        canvas.setBackground(Color.white);
        this.add(canvas);
    }
    public void addMouseMotionListenerCanvas(MouseListener mouseListener,MouseMotionListener mouseMotionListener)
    {
        canvas.addMouseListener(mouseListener);
        canvas.addMouseMotionListener(mouseMotionListener);
    }

    public void Render(IRenderable[] shapes)
    {
        canvas.Render(shapes);
    }

    private class Canvas extends JPanel
    {
        private IRenderable[] shapes=new IRenderable[0];

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            for (IRenderable shape: shapes)
            {
                shape.render(g);
            }
        }

        public void Render(IRenderable[] shapes)
        {
            this.shapes = shapes;
            this.repaint();
            //this.shapes = null;
        }
    }
}



