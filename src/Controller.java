import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Andrew Fillmore on 7/25/2016.
 */
public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model)
    {
        this.view = view;
        this.model=model;

        this.view.addMouseMotionListenerCanvas(new MouseHandler(),new MouseMotionHandler());

    }

    private class MouseHandler implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent event) {
            //view.Render(model.getVectorGraphics());
        }

        @Override
        public void mousePressed(MouseEvent event) {
            view.Render(model.getVectorGraphics(event.getX(),event.getY()));
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            //view.Render(model.getVectorGraphics());
        }

        @Override
        public void mouseEntered(MouseEvent event) {
            //view.Render(model.getVectorGraphics());
        }

        @Override
        public void mouseExited(MouseEvent event) {
            //view.Render(model.getVectorGraphics());
        }
    }

    private class MouseMotionHandler implements MouseMotionListener
    {
        @Override
        public void mouseDragged(MouseEvent event)
        {
            view.Render(model.getVectorGraphics(event.getX(),event.getY()));
        }

        @Override
        public void mouseMoved(MouseEvent event)
        {
            view.Render(model.getVectorGraphics(event.getX(),event.getY()));
        }

    }
}
