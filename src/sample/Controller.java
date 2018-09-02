package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;

public class Controller {

    @FXML
    private Pane mainPane; // Pane principale

    private GraphFX mainGraph; // Grafo principale

    public void initialize() { // inizializza schermata

    }
    
    public void saveGraphOnAction(javafx.event.ActionEvent actionEvent) { // gestisce pulsante "Salva"
    	
    }
    
    public void loadGraphOnAction(javafx.event.ActionEvent actionEvent) { // gestisce pulsante "Carica"
    	
    }
    
    public void newConnOnAction(javafx.event.ActionEvent actionEvent) { // gestisce pulsante "Edge"
    	
    }

    public void newGraphOnAction(javafx.event.ActionEvent actionEvent) { // gestisce pulsante "Genera Grafo"
        mainPane.getChildren().removeIf(n -> n instanceof Circle|| n instanceof Line);
    	mainGraph = new GraphFX(128);
        mainGraph.randomGraph(10);
        for (int i=0;i<10;i++) {
            mainPane.getChildren().add(mainGraph.getNodes().get(i));
        }
        printConns();
    }

    public void printConns() { // stampa tutte le linee contenute nel grafo mainGraph
        int i=0, j;
        while (i<mainGraph.getDimension()) {
            j=i+1;
            while (j<mainGraph.getDimension()) {
                if (mainGraph.getNodes().get(i).isAdj(j)) {
                    mainPane.getChildren().add(mainGraph.getConnectionObj(i,j));
                }
                j++;
            }
            i++;
        }
    }

    public void newNodeOnAction(ActionEvent actionEvent) { // gestisce pulsante "Nodo"
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
