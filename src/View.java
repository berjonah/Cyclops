import javafx.geometry.Rectangle2D;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class View extends JFrame
{

    private Canvas drawingCanvas;
    private Canvas displayCanvas;
    private JMenuItem addGate;
    private JSlider ZoomSlider;



    public View()
    {
        displayCanvas = new Canvas();
        drawingCanvas = new Canvas();



        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1600,800);
        this.setLayout(new BorderLayout(5,5));

        displayCanvas.setBackground(Color.WHITE);
        displayCanvas.setMinimumSize(new Dimension(150,0));
        drawingCanvas.setBackground(Color.WHITE);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,displayCanvas,drawingCanvas);
        splitPane.setDividerLocation(150);
        this.add(splitPane,BorderLayout.CENTER);

        ZoomSlider = new JSlider(5,50);
        this.add(ZoomSlider,BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem save = new JMenuItem("Save");
        file.add(save);
        JMenuItem open = new JMenuItem("Open");
        file.add(open);
        addGate = new JMenuItem("Add Gate");
        file.add(addGate);
        menuBar.add(file);

        this.setJMenuBar(menuBar);
    }

    public double getScale()
    {
        return ZoomSlider.getValue();
    }

    public void setScale(double scale)
    {
        ZoomSlider.setValue((int)scale);
    }

    public Rectangle2D getCanvasBounds()
    {
        return new Rectangle2D(0,0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
    }

    public void addMouseListenerDrawingCanvas(MouseListener mouseListener)
    {
        drawingCanvas.addMouseListener(mouseListener);
    }

    public void addMouseMotionListenerDrawingCanvas(MouseMotionListener mouseMotionListener)
    {
        drawingCanvas.addMouseMotionListener(mouseMotionListener);
    }

    public void addMouseWheelListenerDrawingCanvas(MouseWheelListener mouseWheelListener)
    {
        drawingCanvas.addMouseWheelListener(mouseWheelListener);
    }

    public void addActionListenerAddGate(ActionListener actionListener)
    {
        addGate.addActionListener(actionListener);
    }

    public void addChangeListenerZoomSlider(ChangeListener changeListener)
    {
        ZoomSlider.addChangeListener(changeListener);
    }

    public void Render(List<? extends IRenderable> shapes)
    {
        drawingCanvas.Render(shapes);
    }

    private class Canvas extends JPanel
    {
        private List<? extends IRenderable> shapes = new ArrayList<>();

        @Override
        public void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D)g;
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHints(rh);
            super.paintComponent(g2);
            for (IRenderable shape: shapes) {
                shape.render(g2);
            }
        }

        public void Render(List<? extends IRenderable> shapes)
        {
            this.shapes = shapes;
            this.repaint();
        }
    }

}



