
public abstract class UnaryGate extends Gate {
    protected Node node;
    UnaryGate(double xPos, double yPos , double scale) {
        super(xPos, yPos, scale);

        node = new Node(this);
    }

    @Override
    public Node getNode(int index)
    {
        if (index == 0)
            return node;
        else
            throw new IllegalArgumentException("Argument must be 0 for unary gate");
    }

    @Override
    public Node getNode()
    {
        return node;
    }
}
