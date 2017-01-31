import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.io.File;

class Controller {
    private View view;
    private Model model;

    Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        this.view.addMouseListenerDrawingCanvas(new CanvasMouseListener());
        this.view.addMouseMotionListenerDrawingCanvas(new CanvasMouseMotionListener());
        this.view.addMouseWheelListenerDrawingCanvas(new CanvasMouseWheelListener());
        this.view.Update(model.getVectorGraphics(), model.getCurrentCursor());
        this.view.addChangeListenerZoomSlider(new ZoomSliderChangeListener());

        this.view.addActionListenerPlaceAndGate(new PlaceAndGateActionListener());
        this.view.addActionListenerPlaceOrGate(new PlaceOrGateActionListener());
        this.view.addActionListenerPlaceNotGate(new PlaceNotGateActionListener());
        this.view.addActionListenerPlaceSwitch(new PlaceSwitchActionListener());
        this.view.addActionListenerMakeConnection(new MakeConnectionActionListener());
        this.view.addActionListenerDelete(new DeleteActionListener());
        this.view.addActionListenerCancelPlace(new CancelPlaceActionListener());
        this.view.addActionListenerOpen(new OpenActionListener());
        this.view.addActionListenerSave(new SaveActionListener());

        this.view.setScale(model.getScale());
    }

    private class CanvasMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent event) {
            model.mouseClick(event);
            view.Update(model.getVectorGraphics(), model.getCurrentCursor());
        }

        @Override
        public void mousePressed(MouseEvent event) {
            model.mouseDown(event);
            view.Update(model.getVectorGraphics(), model.getCurrentCursor());
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            model.mouseUp(event);
            view.Update(model.getVectorGraphics(), model.getCurrentCursor());
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

    private class CanvasMouseMotionListener implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent event) {
            model.mouseDrag(event);
            view.Update(model.getVectorGraphics(), model.getCurrentCursor());
        }

        @Override
        public void mouseMoved(MouseEvent event) {
            //view.Update(model.getVectorGraphics(),model.getCurrentCursor());
        }

    }

    private class CanvasMouseWheelListener implements MouseWheelListener {
        @Override
        public void mouseWheelMoved(MouseWheelEvent event) {
            model.MouseWheel(event);
            view.setScale(model.getScale());
            view.Update(model.getVectorGraphics(), model.getCurrentCursor());
        }
    }


    private class PlaceAndGateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            model.setClickState(ClickState.PLACE_AND_GATE);
            view.Update(model.getVectorGraphics(), model.getCurrentCursor());

        }
    }

    private class PlaceOrGateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            model.setClickState(ClickState.PLACE_OR_GATE);
            view.Update(model.getVectorGraphics(), model.getCurrentCursor());
        }
    }

    private class PlaceNotGateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            model.setClickState(ClickState.PLACE_NOT_GATE);
            view.Update(model.getVectorGraphics(), model.getCurrentCursor());
        }
    }

    private class PlaceSwitchActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            model.setClickState(ClickState.PLACE_SWITCH);
            view.Update(model.getVectorGraphics(), model.getCurrentCursor());
        }
    }

    private class MakeConnectionActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            model.setClickState(ClickState.CONNECTION_START);
            view.Update(model.getVectorGraphics(), model.getCurrentCursor());
        }
    }

    private class DeleteActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            model.setClickState(ClickState.DELETE);
            view.Update(model.getVectorGraphics(), model.getCurrentCursor());
        }
    }

    private class CancelPlaceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            model.setClickState(ClickState.DEFAULT);
            view.Update(model.getVectorGraphics(), model.getCurrentCursor());
        }
    }

    private class OpenActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            File file = view.chooseFile();
            if(file != null) {
                model.open(file);
                view.setScale(model.getScale());
                view.Update(model.getVectorGraphics(), model.getCurrentCursor());
            }
        }
    }

    private class SaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            File file = view.chooseFile();
            if (file != null) {
                model.save(file);
                view.Update(model.getVectorGraphics(), model.getCurrentCursor());
            }
        }
    }

    private class ZoomSliderChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            double middleX = (view.getCanvasBounds().getMaxX() + view.getCanvasBounds().getMinX()) / 2;
            double middleY = (view.getCanvasBounds().getMaxY() + view.getCanvasBounds().getMinY()) / 2;
            model.setScale(view.getScale(), middleX, middleY);
            view.Update(model.getVectorGraphics(), model.getCurrentCursor());
        }
    }
}
