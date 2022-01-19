package il.ac.haifa.ClinicSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class SystemManagerMenuController {

    @FXML
    private Label MenuLabel;

    @FXML
    private Button clinicListBtn;

    @FXML
    private Button covidBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button reportBtn;


    private SimpleClient chatClient;

    @FXML
    void exitProgram(ActionEvent event) throws IOException {
        chatClient.closeConnection();
    }

    @FXML
    void showClinicList(ActionEvent event) throws IOException {
        App.setRoot("clinicList");
    }

    @FXML
    void showCovidServices(ActionEvent event) throws IOException {
        App.setRoot("covidServicesList");
    }

    @FXML
    void showWeeklyClinicReport(ActionEvent event) throws IOException {
        App.setRoot("chooseClinic");
    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}
