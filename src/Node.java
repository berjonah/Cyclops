class Node implements IObserver {

    private Gate gate;
    private boolean state;
    private boolean inUse;

    Node(Gate gate) {
        this.gate = gate;
    }


    double getXPos(double offset) {
        return gate.getNodeXPos(offset, this);
    }

    double getYPos(double offset) {
        return gate.getNodeYPos(offset, this);
    }

    public Gate getGate()
    {
        return gate;
    }

    public boolean getInUse()
    {
        return inUse;
    }

    public void setInUse(boolean inUse)
    {
        this.inUse = inUse;
    }

    @Override
    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public boolean getState() {
        return this.state;
    }

    @Override
    public void update() {
        gate.checkNodes();
    }
}
