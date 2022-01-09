/**
 * Sample Skeleton for 'clientMenu.fxml' Controller Class
 */

package il.ac.haifa.ClinicSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ClientMenuController {

    private SimpleClient chatClient;

    @FXML // fx:id="doctorBTN"
    private Button doctorBTN; // Value injected by FXMLLoader

    @FXML
    void clickDoctor(ActionEvent event) throws IOException {
        App.setRoot("doctorList");
    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}
