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

    }

    public void newNodeOnAction(ActionEvent actionEvent) {
        mainGraph = new GraphFX(128);
        mainGraph.addNode(100,100);
        mainGraph.addNode(100,200);
        mainGraph.addRandomConnection();
        mainPane.getChildren().add(mainGraph.getNodes().get(0));
        mainPane.getChildren().add(mainGraph.getNodes().get(1));
    }
}
