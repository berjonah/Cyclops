//import javafx.geometry.Rectangle2D;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

class View extends JFrame {

    private Canvas drawingCanvas;
    private Canvas ButtonCanvas;
    private JButton b_PlaceAndGate;
    private JButton b_PlaceOrGate;
    private JButton b_PlaceNotGate;
    private JButton b_PlaceSwitch;
    private JButton b_MakeConnection;
    private JButton b_Delete;
    private JButton b_CancelPlace;
    //private JMenuItem addGate;
    private JSlider ZoomSlider;
    private JMenuItem m_open;
    private JMenuItem m_save;


    View() {
        this.setName("Name");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(300,200);
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.black);

        ButtonCanvas = new Canvas();
        ButtonCanvas.setBackground(Color.LIGHT_GRAY);
        ButtonCanvas.setLayout(new GridLayout(7, 1));
        drawingCanvas = new Canvas();
        drawingCanvas.setBackground(Color.WHITE);

        ZoomSlider = new JSlider(5, 50);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = 100;
        this.add(ButtonCanvas, constraints);

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
        this.add(drawingCanvas, constraints);

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
        b_MakeConnection = new JButton("CONNECT");
        b_Delete = new JButton("DELETE");
        b_CancelPlace = new JButton("CANCEL");
        ButtonCanvas.add(b_PlaceAndGate);
        ButtonCanvas.add(b_PlaceOrGate);
        ButtonCanvas.add(b_PlaceNotGate);
        ButtonCanvas.add(b_PlaceSwitch);
        ButtonCanvas.add(b_MakeConnection);
        ButtonCanvas.add(b_Delete);
        ButtonCanvas.add(b_CancelPlace);

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        m_save = new JMenuItem("Save");
        file.add(m_save);
        m_open = new JMenuItem("Open");
        file.add(m_open);
        //addGate = new JMenuItem("Add Gate");
        //file.add(addGate);
        menuBar.add(file);

        this.setJMenuBar(menuBar);
    }

    double getScale() {
        return ZoomSlider.getValue();
    }

    void setScale(double scale) {
        ZoomSlider.setValue((int) scale);
    }

    Rectangle2D getCanvasBounds() {
        return new Rectangle2D.Double(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
    }

    void addMouseListenerDrawingCanvas(MouseListener mouseListener) {
        drawingCanvas.addMouseListener(mouseListener);
    }

    void addMouseMotionListenerDrawingCanvas(MouseMotionListener mouseMotionListener) {
        drawingCanvas.addMouseMotionListener(mouseMotionListener);
    }

    void addMouseWheelListenerDrawingCanvas(MouseWheelListener mouseWheelListener) {
        drawingCanvas.addMouseWheelListener(mouseWheelListener);
    }

    void addChangeListenerZoomSlider(ChangeListener changeListener) {
        ZoomSlider.addChangeListener(changeListener);
    }

    void addActionListenerPlaceAndGate(ActionListener actionListener) {
        b_PlaceAndGate.addActionListener(actionListener);
    }

    void addActionListenerPlaceOrGate(ActionListener actionListener) {
        b_PlaceOrGate.addActionListener(actionListener);
    }

    void addActionListenerPlaceNotGate(ActionListener actionListener) {
        b_PlaceNotGate.addActionListener(actionListener);
    }

    void addActionListenerPlaceSwitch(ActionListener actionListener) {
        b_PlaceSwitch.addActionListener(actionListener);
    }

    void addActionListenerMakeConnection(ActionListener actionListener) {
        b_MakeConnection.addActionListener(actionListener);
    }

    void addActionListenerDelete(ActionListener actionListener) {
        b_Delete.addActionListener(actionListener);
    }

    void addActionListenerCancelPlace(ActionListener actionListener) {
        b_CancelPlace.addActionListener(actionListener);
    }

    void addActionListenerOpen(ActionListener actionListener) {
        m_open.addActionListener(actionListener);
    }

    void addActionListenerSave(ActionListener actionListener) {
        m_save.addActionListener(actionListener);
    }

    void Update(List<? extends IRenderable> shapes, Cursor cursor) {
        Render(shapes);
        setCursor(cursor);
    }

    private void Render(List<? extends IRenderable> shapes) {
        drawingCanvas.Render(shapes);
    }

    public void setCursor(Cursor cursor) {
        drawingCanvas.setCursor(cursor);
        ButtonCanvas.setCursor(cursor);
    }

    public File chooseFile()
    {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.CANCEL_OPTION)
        {
            return null;
        }
        return fileChooser.getSelectedFile();
    }

    private class Canvas extends JPanel {
        private List<? extends IRenderable> shapes = new ArrayList<>();

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHints(rh);
            super.paintComponent(g2);
            for (IRenderable shape : shapes) {
                shape.render(g2);
            }
        }

        void Render(List<? extends IRenderable> shapes) {
            this.shapes = shapes;
            this.repaint();
        }
    }



}



