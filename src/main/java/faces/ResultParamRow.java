package faces;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ResultParamRow extends AnchorPane {
    @FXML
    private Label paramLabel, valueLabel, unitLabel;

    public ResultParamRow(String paramName, String value, String unit) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ResultParamRow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        init(paramName, value, unit);
    }

    private void init(String name, String value, String unit) {
        paramLabel.setText(name);
        valueLabel.setText(value);
        if (unit != null) unitLabel.setText(unit);
    }
}
