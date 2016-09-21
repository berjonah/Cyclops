import com.sun.prism.BufferedImageTools;
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
    private Canvas ButtonCanvas;
    private JButton b_PlaceAndGate;
    private JButton b_PlaceOrGate;
    private JButton b_PlaceNotGate;
    private JButton b_PlaceSwitch;
    private JButton b_CancelPlace;
    private JMenuItem addGate;
    private JSlider ZoomSlider;



    public View()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(160,80);
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.black);

        ButtonCanvas = new Canvas();
        ButtonCanvas.setBackground(Color.LIGHT_GRAY);
        ButtonCanvas.setLayout(new GridLayout(5,1));
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
        this.add(ButtonCanvas,constraints);

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

        b_PlaceAndGate = new JButton("AND");
        b_PlaceOrGate = new JButton("OR");
        b_PlaceNotGate = new JButton("NOT");
        b_PlaceSwitch = new JButton("SWITCH");
        b_CancelPlace = new JButton("CANCEL");
        ButtonCanvas.add(b_PlaceAndGate);
        ButtonCanvas.add(b_PlaceOrGate);
        ButtonCanvas.add(b_PlaceNotGate);
        ButtonCanvas.add(b_PlaceSwitch);
        ButtonCanvas.add(b_CancelPlace);

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


    public void addChangeListenerZoomSlider(ChangeListener changeListener)
    {
        ZoomSlider.addChangeListener(changeListener);
    }

    public void addActionListenerPlaceAndGate(ActionListener actionListener)
    {
        b_PlaceAndGate.addActionListener(actionListener);
    }

    public void addActionListenerPlaceOrGate(ActionListener actionListener)
    {
        b_PlaceOrGate.addActionListener(actionListener);
    }

    public void addActionListenerPlaceNotGate(ActionListener actionListener)
    {
        b_PlaceNotGate.addActionListener(actionListener);
    }

    public void addActionListenerPlaceSwitch(ActionListener actionListener)
    {
        b_PlaceSwitch.addActionListener(actionListener);
    }

    public void addActionListenerCancelPlace(ActionListener actionListener)
    {
        b_CancelPlace.addActionListener(actionListener);
    }

    public void Update(List<? extends IRenderable> shapes, Cursor cursor)
    {
        Render(shapes);
        setCursor(cursor);
    }

    public void Render(List<? extends IRenderable> shapes)
    {
        drawingCanvas.Render(shapes);
    }

    public void setCursor(Cursor cursor)
    {
        drawingCanvas.setCursor(cursor);
        ButtonCanvas.setCursor(cursor);
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



