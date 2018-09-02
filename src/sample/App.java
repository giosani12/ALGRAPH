package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {
    public PriorityHeap temp = new PriorityHeap(4);
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainUI.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        /*myNode a = new myNode(4,0);
        myNode b = new myNode(4,1);
        temp.insert(a,1);
        temp.insert(b,2);
        temp.writeToFile();
        temp.readFromFile(4);
        temp.writeToFile();
        */
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
