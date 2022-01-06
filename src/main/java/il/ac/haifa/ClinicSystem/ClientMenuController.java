package il.ac.haifa.ClinicSystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ClientMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button doctorBTN;

    @FXML
    void clickDoctor(ActionEvent event) throws IOException {
        App.setRoot("doctorList");
    }

    @FXML
    void initialize() {
        assert doctorBTN != null : "fx:id=\"doctorBTN\" was not injected: check your FXML file 'clientMenu.fxml'.";

    }

}
