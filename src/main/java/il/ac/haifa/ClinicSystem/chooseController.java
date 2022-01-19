package il.ac.haifa.ClinicSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

/*the purpose of this scene is to allow the user to choose whether
he is treated as if he is using the online service or as if he is in the
clinic already
*/
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
