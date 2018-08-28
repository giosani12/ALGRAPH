package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("ALGRAPH Algoritmo di Johnson");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);

        Text scenetitle = new Text("Inserire numero di nodi o caricare grafo");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid.add(scenetitle, 0, 0, 3, 1);

        Label numberOfNodes = new Label("Numero Nodi:");
        grid.add(numberOfNodes, 0, 1, 2,1);

        TextField nodesTextField = new TextField();
        grid.add(nodesTextField, 3, 1);

        Button SIbtn = new Button("Genera");
        HBox SIhbBtn = new HBox(10);
        SIhbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        SIhbBtn.getChildren().add(SIbtn);
        grid.add(SIhbBtn, 0, 2);

        Button Gbtn = new Button("Carica Grafo");
        HBox GhbBtn = new HBox(10);
        GhbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        GhbBtn.getChildren().add(Gbtn);
        grid.add(GhbBtn, 3, 2);

        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
