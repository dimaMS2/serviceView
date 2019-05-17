package server;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.ServiceView;

public class ServerApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        ServerView serverView = new ServerView();
        primaryStage.setTitle("Server");
        primaryStage.setScene(new Scene(serverView));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}