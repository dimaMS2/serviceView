package faces;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class ConnectParamRow extends AnchorPane {
    @FXML
    private Label title;
    @FXML
    private ChoiceBox<String> choiceBox;

    public ConnectParamRow(String name, List<String> items) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ConnectParamRow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        init(name, items);
    }

    private void init(String name, List<String> items) {
        title.setText(name);
        choiceBox.getItems().addAll(items);
        choiceBox.getSelectionModel().selectFirst();
    }

    public ChoiceBox<String> getChoiceBox() {
        return choiceBox;
    }
}
