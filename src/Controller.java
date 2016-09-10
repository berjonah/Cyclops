import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model)
    {
        this.view = view;
        this.model=model;

        this.view.addMouseListenerDrawingCanvas(new CanvasMouseListener());
        this.view.addMouseMotionListenerDrawingCanvas(new CanvasMouseMotionListener());
        this.view.addMouseWheelListenerDrawingCanvas(new CanvasMouseWheelListener());
        this.view.addActionListenerAddGate(new AddGateActionListener());
        this.view.Render(model.getVectorGraphics());
        this.view.addChangeListenerZoomSlider(new ZoomSliderChangeListener());

        this.view.setScale(model.getScale());
    }

    private class CanvasMouseListener implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent event)
        {
            model.mouseClick(event);
            view.Render(model.getVectorGraphics());
        }

        @Override
        public void mousePressed(MouseEvent event)
        {
            model.mouseDown(event);
            view.Render(model.getVectorGraphics());
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            model.mouseUp(event);
            view.Render(model.getVectorGraphics());
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

    private class CanvasMouseMotionListener implements MouseMotionListener
    {
        @Override
        public void mouseDragged(MouseEvent event)
        {
            model.mouseDrag(event);
            view.Render(model.getVectorGraphics());
        }

        @Override
        public void mouseMoved(MouseEvent event)
        {
            view.Render(model.getVectorGraphics());
        }

    }

    private class CanvasMouseWheelListener implements MouseWheelListener
    {
        @Override
        public void mouseWheelMoved(MouseWheelEvent event)
        {
            model.MouseWheel(event);
            view.setScale(model.getScale());
            view.Render(model.getVectorGraphics());
        }
    }
    
    private class AddGateActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionevent)
        {
            model.addGate(GateType.NOT_GATE, 800,300);
            view.Render(model.getVectorGraphics());
        }
    }

    private class ZoomSliderChangeListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent changeEvent)
        {
            double middleX = (view.getCanvasBounds().getMaxX() + view.getCanvasBounds().getMinX()) / 2;
            double middleY = (view.getCanvasBounds().getMaxY() + view.getCanvasBounds().getMinY()) / 2;
            model.setScale(middleX, middleY, view.getScale());
            view.Render(model.getVectorGraphics());
        }
    }
}
