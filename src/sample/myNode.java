package sample;

import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class myNode extends Circle {
    private boolean[] connections;
    private int pos;
    private int priority;


    public myNode(int dim, int POS, int PRIORITY) {
        connections = new boolean[dim];
        pos=POS;
        priority=PRIORITY;
        for (int i=0; i<dim; i++) {
            connections[i]=false;
        }
    }

    public myNode(int dim, int POS) {
        connections = new boolean[dim];
        pos=POS;
        priority=0;
        for (int i=0; i<dim; i++) {
            connections[i]=false;
        }
    }

    public myNode(int dim, int POS, double X, double Y) {
        connections = new boolean[dim];
        setCenterX(X);
        setCenterY(Y);
        setRadius(10.0);
        pos=POS;
        priority=0;
        for (int i=0; i<dim; i++) {
            connections[i]=false;
        }
    }

    public void setPosition(int x, int y) {
        setCenterX(x);
        setCenterY(y);
    }

    public void saveNode(FileOutputStream stream) throws IOException
    {
        stream.write(pos);
        stream.write(priority);
        for (boolean item : connections)
        {
            stream.write(item ? 1 : 0);
        }
    }

    public void loadNode(FileInputStream stream, int lenght) throws IOException
    {
        byte[] data = new byte[lenght];
        pos=stream.read();
        priority=stream.read();
        stream.read(data,0,lenght);
        for (int X = 0; X < lenght; X++)
        {
            if (data[X] != 0)
            {
                connections[X] = true;
                continue;
            }
            connections[X] = false;
        }
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

    public boolean getConnection(int sel) {
        return connections[sel];
    }

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
