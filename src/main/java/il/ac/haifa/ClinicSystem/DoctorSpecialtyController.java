package il.ac.haifa.ClinicSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class DoctorSpecialtyController {

    static String value;
    private Alert notSelectedAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    private Button backBTN;

    @FXML
    private Button nextBTN;

    @FXML
    private ComboBox<String> specialtyBox;

    @FXML
    void goBack(ActionEvent event) throws IOException {
        App.setRoot("clientMenu");
    }

    @FXML
    void goNext(ActionEvent event) throws IOException {
        /*EventHandler<ActionEvent> event1 =
                new EventHandler<ActionEvent>() {
                    Label selected = new Label("default item selected");
                    public void handle(ActionEvent e)
                    {
                        selected.setText(specialtyBox.getValue() + " selected");
                    }
                };*/
        value = specialtyBox.getValue();
        if(value == null){
            notSelectedAlert.setContentText("No specialty Selected!");
            notSelectedAlert.showAndWait();
            return;
        }
        App.setRoot("doctorList");
    }
}
