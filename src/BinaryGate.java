
public abstract class BinaryGate extends Gate {
    protected Node topNode;
    protected Node botNode;

    BinaryGate(double xPos, double yPos, double scale)
    {
        super(xPos, yPos, scale);

        topNode = new Node(this);
        botNode = new Node(this);


    }

    @Override
    public Node getNode(int index) {
        switch (index) {
            case 0:
                return topNode;
            case 1:
                return botNode;
            default:
                throw new IllegalArgumentException("Argument must be 0 or 1 for binary gate");
        }
    }
}
