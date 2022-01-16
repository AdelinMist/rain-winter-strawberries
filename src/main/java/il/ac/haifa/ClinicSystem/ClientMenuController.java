package il.ac.haifa.ClinicSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ClientMenuController {

    private SimpleClient chatClient;

    @FXML
    private Button exitBtn;

    @FXML
    private Button orderBtn;

    @FXML
    private Button viewBtn;

    @FXML
    void exit(ActionEvent event) throws IOException {
        chatClient.closeConnection();
    }

    @FXML
    void showOrderMenu(ActionEvent event) throws IOException {
        App.setRoot("orderAppointmentMenu");
    }

    @FXML
    void showViewMenu(ActionEvent event) throws IOException {
        App.setRoot("appointmentsMenu");
    }


    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}
