package il.ac.haifa.ClinicSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class doctorScreenController {

    @FXML
    private AnchorPane GoToSeeAppointments;

    @FXML
    void GoToSeeAppointments(MouseEvent event) throws IOException {


    }

    @FXML
    void goToApList(ActionEvent event) throws IOException {
        App.setRoot("doctorApinmntMngmnt");
    }

}
