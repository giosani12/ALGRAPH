package sample;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.nio.file.Path;
import java.io.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML
    private Pane mainPane; // Pane principale
    
    @FXML
    private TextArea textTab; // Log testuale

    @FXML
    private Button saveGraph;
    
    @FXML
    private Button startAlg;
    
    @FXML
    private Button nextStep;
    
    @FXML
    private Button finalStep;
    
    private GraphFX mainGraph; // Grafo principale
    private JohnsonAlg mainAlg;
    private int lastUnode;
    private boolean finishAlg;
    
    double orgSceneX, orgSceneY;
    double getSceneX, getSceneY;
    double orgTranslateX, orgTranslateY;
    
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
    	nextStep.setDisable(true);
    	finalStep.setDisable(true);
    	startAlg.setDisable(true);
    	saveGraph.setDisable(true);
    	finishAlg=false;
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
    	fChoose.getExtensionFilters().add(new ExtensionFilter("Graph Files", "*.GRAFO"));
    	File selectedFile =fChoose.showOpenDialog(mainPane.getScene().getWindow());
    	Path inputPath = selectedFile.toPath();
    	try (InputStream in = Files.newInputStream(inputPath)/*;
    		BufferedReader reader =
    	    new BufferedReader(new InputStreamReader(in))*/) {
    		//mainGraph = new GraphFX()
    	} catch (IOException x) {
   		    System.err.println(x);
   		}
    	saveGraph.setDisable(false);
    }
    
    public void newConnOnAction(javafx.event.ActionEvent actionEvent) { // gestisce pulsante "Edge"
    	
    }
    
    EventHandler<MouseEvent> circleOnMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
            orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
        }
    };
    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) { double offsetX = t.getSceneX() - orgSceneX;
        double offsetY = t.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

            ((Circle)(t.getSource())).setTranslateX(newTranslateX);
            ((Circle)(t.getSource())).setTranslateY(newTranslateY);
        }
    };
    
    public void startAlgOnAction(javafx.event.ActionEvent actionEvent) {
    	mainAlg = new JohnsonAlg(mainGraph.getNodes().get(1), mainGraph.getDimension());
    	mainGraph.getNodes().get(1).setFill(javafx.scene.paint.Color.RED);
    	System.out.println(mainGraph.getNodes().get(1).getPos());
    	lastUnode=mainGraph.getNodes().get(1).getPos();
    	nextStep.setDisable(false);
    	finalStep.setDisable(false);
    	startAlg.setDisable(true);
    	nextStepOnAction(null);
    }
    
    public void nextStepOnAction(javafx.event.ActionEvent actionEvent) {
    	if (!mainAlg.isSEmpty()) {
    		//mainGraph.getNodes().get(lastUnode).setFill(javafx.scene.paint.Color.BLACK);
    	   	myNode tempU = mainAlg.firstIterator();
    	   	tempU.setFill(javafx.scene.paint.Color.RED);
    	   	System.out.println(tempU.getPos());
    	   	ArrayList<myNode> v = mainGraph.adjs(tempU.getPos());
    	   	while(!v.isEmpty()) {
    	   		myNode temp= v.remove(0);
    	   		System.out.println("il disturbo mi è causato da:"+temp.getPos()+" e "+tempU.getPos());
    	   		if (temp.getPos()!=tempU.getPos()) mainAlg.secondIterator(temp, mainGraph.getConnection(tempU.getPos(), temp.getPos()));
    	   	}
    	   	lastUnode=tempU.getPos();
    	}
    	else if (!finishAlg)finalStepOnAction(null);
    }
    
    public void finalStepOnAction(javafx.event.ActionEvent actionEvent) {
    	finishAlg=true;
    	while (!mainAlg.isSEmpty()) {
    		nextStepOnAction(null);
    	}
    	System.out.println("FINITO!!");
    	//mainGraph.getNodes().get(lastUnode).setFill(javafx.scene.paint.Color.BLACK);
    }

    public void newGraphOnAction(javafx.event.ActionEvent actionEvent) { // gestisce pulsante "Genera Grafo"
        mainPane.getChildren().removeIf(n -> n instanceof Circle|| n instanceof Line);
    	mainGraph = new GraphFX(128);
        mainGraph.randomGraph(10);
        for (int i=1;i<=10;i++) {
            mainPane.getChildren().add(mainGraph.getNodes().get(i));
        }
        for (Node n: mainPane.getChildren()) {
            if ( n instanceof Circle) {
                n.setOnMousePressed(circleOnMousePressedEventHandler);
                n.setOnMouseDragged(circleOnMouseDraggedEventHandler);
            }
        }
        printConns();
        saveGraph.setDisable(false);
        startAlg.setDisable(false);
    }

    public void printConns() { // stampa tutte le linee contenute nel grafo mainGraph
        int i=1, j;
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
        mainPane.getChildren().add(mainGraph.getNodes().get(1));
        mainPane.getChildren().add(mainGraph.getNodes().get(2));
        saveGraph.setDisable(false);
        startAlg.setDisable(false);
    }
}
