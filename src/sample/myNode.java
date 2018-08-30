package sample;

import javafx.scene.shape.Circle;

public class myNode extends Circle {
    private boolean[] connections;
    private int pos;
    private int priority;
    private int MAX_x;
    private int MAX_y;

    public myNode(int dim, int POS, int PRIORITY) {
        connections = new boolean[dim];
        /*Xpos=0;
        Ypos=0;*/
        pos=POS;
        priority=PRIORITY;
        for (int i=0; i<dim; i++) {
            connections[i]=false;
        }
    }

    public myNode(int dim, int POS) {
        connections = new boolean[dim];
        /*Xpos=0;
        Ypos=0;*/
        pos=POS;
        priority=0;
        for (int i=0; i<dim; i++) {
            connections[i]=false;
        }
    }

    public boolean setPosition(int x, int y) {
        if ((-1<x)&&(x<MAX_x)&&(-1<y)&&(x<MAX_y)) {
            setCenterX(x);
            setCenterY(y);
            return true;
        }
        else return false;
    }

    /*public void setPositions(int X, int Y) {
        Xpos=X;
        Ypos=Y;
    }

    public int getX() {
        return Xpos;
    }

    public int getY() {
        return Ypos;
    }*/

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
