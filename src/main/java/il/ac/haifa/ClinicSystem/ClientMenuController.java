package il.ac.haifa.ClinicSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ClientMenuController {

    @FXML
    private Button doctorBTN;

    @FXML
    void clickDoctor(ActionEvent event) throws IOException {
        App.setRoot("doctorList");
    }

    private SimpleClient chatClient;

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }
}
