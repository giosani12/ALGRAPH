package sample;

import javafx.scene.shape.Line;

public class myConn extends Line {

    private int weight; // peso della linea

    public myConn(int in) { // costruttore che imposta il peso della connessione
        weight=in;
    }

    public void setCoords(double ax, double ay, double bx, double by) { // imposta coordinate degli estremi della linea
        setStartX(ax);
        setStartY(ay);
        setEndX(bx);
        setEndY(by);
    }
    public int getWeight() { // ritorna peso della connessione, -1 se inesistente
        if (weight<60000) return weight;
        else return -1;
    }

    public void setWeight(int WEIGHT) { // imposta peso della connessione
        weight=WEIGHT;
    }
}