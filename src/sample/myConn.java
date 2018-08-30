package sample;

import javafx.scene.shape.Line;

public class myConn extends Line {
    private int weight;

    public myConn(int in) {
        weight=in;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int WEIGHT) {
        weight=WEIGHT;
    }
}