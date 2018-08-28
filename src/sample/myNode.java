package sample;

public class myNode {
    private boolean[] connections;
    private int Xpos;
    private int Ypos;
    private int pos;
    private int priority;

    public myNode(int dim, int POS, int PRIORITY) {
        connections = new boolean[dim];
        Xpos=0;
        Ypos=0;
        pos=POS;
        priority=PRIORITY;
        for (int i=0; i<dim; i++) {
            connections[i]=false;
        }
    }

    public myNode(int dim, int POS) {
        connections = new boolean[dim];
        Xpos=0;
        Ypos=0;
        pos=POS;
        priority=0;
        for (int i=0; i<dim; i++) {
            connections[i]=false;
        }
    }

    public void setPositions(int X, int Y) {
        Xpos=X;
        Ypos=Y;
    }

    public int getX() {
        return Xpos;
    }

    public int getY() {
        return Ypos;
    }

    public int getPos() { return  pos; }

    public int getPriority() { return priority; }

    public void setPriority(int PRIORITY) { priority=PRIORITY; }

    public void setPos(int POS) { pos=POS; }

    public void addConnection(int adj) {
        connections[adj]=true;
    }

    public void removeConnection(int id) {
        connections[id]=false;
    }

    public myNode removeNode() {
        myNode out=this;
        return out;
    }

    public boolean isAdj(int b) {
        return connections[b];
    }
}
