package il.ac.haifa.ClinicSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class chooseController {


    @FXML
    private Button fromHome;

    @FXML
    private Button physical;

    @FXML
    void fromHome(ActionEvent event) throws IOException {
        App.setRoot("Login");
    }

    @FXML
    void physical(ActionEvent event) throws IOException {
        App.setRoot("LoginPhy");
    }

}
