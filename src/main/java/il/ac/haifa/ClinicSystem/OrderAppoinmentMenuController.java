/**
 * Sample Skeleton for 'clientMenu.fxml' Controller Class
 */

package il.ac.haifa.ClinicSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class OrderAppoinmentMenuController {

    private SimpleClient chatClient;

    @FXML
    private Button covid_test;

    @FXML
    private Button vaccine;

    @FXML
    private Button sister;

    @FXML // fx:id="doctorBTN"
    private Button doctorBTN; // Value injected by FXMLLoader

    @FXML
    private Button returnBtn;

    @FXML
    void gotoQuiz(ActionEvent event) throws IOException {
        App.setRoot("quiz_corona_test");
    }

    @FXML
    void clickDoctor(ActionEvent event) throws IOException {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
        App.setRoot("doctorSpecialty");

    }
    @FXML
    void gotovac(ActionEvent event) throws IOException {
        App.setRoot("ClinicVaccine");
    }
    @FXML
    void goSister(ActionEvent event) throws IOException {
        App.setRoot("SisterAppointment");
    }
    @FXML
    void goLab(ActionEvent event) throws IOException {
        App.setRoot("labAppointment");
    }
    @FXML
    void goFamily(ActionEvent event) throws IOException {
        App.setRoot("FamilyAppointment");
    }

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        App.setRoot("clientMenu");
    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}
