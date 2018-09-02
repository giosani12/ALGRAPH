package sample;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.scene.control.TextArea;

public class Controller {

    @FXML
    private Pane mainPane; // Pane principale
    
    @FXML
    private TextArea textTab; // Log testuale

    private GraphFX mainGraph; // Grafo principale
    
    private java.lang.String getLatestNumber() {
        int i=0;
        File tmp = new File("./data/PH"+Integer.toString(i)+".ph");
        while (tmp.exists()) {
            i++;
            tmp = new File("./data/PH"+Integer.toString(i)+".ph");
        }
        return Integer.toString(i);
    }

    public void initialize() { // inizializza schermata

    }
    
    public void saveGraphOnAction(javafx.event.ActionEvent actionEvent) { // gestisce pulsante "Salva"
    	ArrayList<myNode> save = mainGraph.getNodes();
    	java.nio.file.Path p = Paths.get(getLatestNumber());
    	try (OutputStream out = new BufferedOutputStream(
    	      Files.newOutputStream(p, CREATE, WRITE,TRUNCATE_EXISTING))) {
    	      for (myNode t : save) {
    	    	  t.saveNode(out);
    	      }
    	} catch (IOException x) {
    		System.err.println(x);
    	}
    }
    
    public void loadGraphOnAction(javafx.event.ActionEvent actionEvent) { // gestisce pulsante "Carica"
    	FileChooser fChoose = new FileChooser();
    	fChoose.setTitle("Seleziona .GRAFO file");
    	fChoose.showOpenDialog(mainPane.getScene().getWindow());
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
                    boolean tmp = mainPane.getChildren().add(mainGraph.getConnectionObj(i,j));
                    if (!tmp) textTab.setText("Fallita linea " + i);
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
        mainGraph.addConnection(0,1);
        printConns();
        //mainPane.getChildren().add(mainGraph.getConnectionObj(0,1));
        mainPane.getChildren().add(mainGraph.getNodes().get(0));
        mainPane.getChildren().add(mainGraph.getNodes().get(1));
    }
}
