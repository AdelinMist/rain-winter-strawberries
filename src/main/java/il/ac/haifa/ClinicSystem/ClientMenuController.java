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
    @FXML
    private Button covid_test;
    @FXML
    private Button vaccine;

    @FXML // fx:id="doctorBTN"
    private Button doctorBTN; // Value injected by FXMLLoader

    @FXML
    void gotoQuiz(ActionEvent event) throws IOException {
        App.setRoot("quiz_corona_test");
    }

    @FXML
    void clickDoctor(ActionEvent event) throws IOException {
        App.setRoot("doctorList");
    }
    @FXML
    void gotovac(ActionEvent event) throws IOException {
        App.setRoot("ClinicVaccine");
    }
    @FXML
    void goSister(ActionEvent event) throws IOException {
        App.setRoot("SisterAppointment");
    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}
