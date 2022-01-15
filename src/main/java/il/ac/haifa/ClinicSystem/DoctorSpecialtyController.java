package il.ac.haifa.ClinicSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class DoctorSpecialtyController {

    @FXML
    private Button backBTN;

    @FXML
    private Button nextBTN;

    @FXML
    private ComboBox<String> specialtyBox;

    @FXML
    void goBack(ActionEvent event) {

    }

    @FXML
    void goNext(ActionEvent event) {

    }

    void initialize() throws IOException, InterruptedException {
        ObservableList<String> langs = FXCollections.observableArrayList("Java", "JavaScript", "C#", "Python");
        specialtyBox.setItems(langs);
    }
}
