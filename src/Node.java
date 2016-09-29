class Node implements IObserver {

    Gate gate;
    private boolean state;

    Node(Gate gate) {
        this.gate = gate;
    }


    double getXPos(double offset) {
        return gate.getNodeXPos(offset, this);
    }

    double getYPos(double offset) {
        return gate.getNodeYPos(offset, this);
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
