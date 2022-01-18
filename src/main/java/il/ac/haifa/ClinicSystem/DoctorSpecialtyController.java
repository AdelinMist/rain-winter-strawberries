package il.ac.haifa.ClinicSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class DoctorSpecialtyController {

    public static String specialty;
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
        specialty = specialtyBox.getValue();
        if(specialty == null){
            notSelectedAlert.setContentText("No specialty Selected!");
            notSelectedAlert.showAndWait();
            return;
        }
        App.setRoot("doctorAppointment");
    }
}
