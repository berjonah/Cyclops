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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(160,80);
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.black);

        displayCanvas = new Canvas();
        displayCanvas.setBackground(Color.LIGHT_GRAY);

        drawingCanvas = new Canvas();
        drawingCanvas.setBackground(Color.WHITE);

        ZoomSlider = new JSlider(5,50);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = 100;
        this.add(displayCanvas,constraints);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.weightx = 400;
        constraints.weighty = 500;
        constraints.ipadx = 100;
        constraints.ipady = 100;
        this.add(drawingCanvas,constraints);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = 100;
        this.add(ZoomSlider, constraints);

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



