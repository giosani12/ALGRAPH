package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class Controller {

    @FXML
    private Pane mainPane;

    private GraphFX mainGraph;

    public void initialize() {

    }

    public void newGraphOnAction(javafx.event.ActionEvent actionEvent) {
        mainGraph = new GraphFX(128);
        mainGraph.randomGraph(5);
        for (int i=0;i<4;i++) {
            mainPane.getChildren().add(mainGraph.getNodes().get(i));
        }
        printConns();
    }

    public void printConns() {
        int i=0, j;
        while (i<mainGraph.getDimension()) {
            j=i+1;
            while (j<mainGraph.getDimension()) {
                if (mainGraph.getNodes().get(i).getConnection(j)) {
                    mainPane.getChildren().add(mainGraph.getConnectionObj(i,j));
                }
                j++;
            }
            i++;
        }
    }

    public void newNodeOnAction(ActionEvent actionEvent) {
        mainGraph = new GraphFX(128);
        mainGraph.addNode(100,100);
        mainGraph.addNode(100,200);
        myConn test = new myConn(5);
        test.setCoords(100,100,100,200);
        //mainPane.getChildren().add(test);
        mainPane.getChildren().add(mainGraph.addConnection(0,1));
        //mainPane.getChildren().add(mainGraph.getConnectionObj(0,1));
        mainPane.getChildren().add(mainGraph.getNodes().get(0));
        mainPane.getChildren().add(mainGraph.getNodes().get(1));
    }
}
