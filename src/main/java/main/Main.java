package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.ServiceView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        ServiceView serviceView = new ServiceView("/fxml/Main.fxml");
        primaryStage.setTitle("Title");
        primaryStage.setScene(new Scene(serviceView));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
