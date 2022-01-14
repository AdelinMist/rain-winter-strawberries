package il.ac.haifa.ClinicSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PrimaryController {

    private SimpleClient chatClient;
    @FXML
    private Button ExitButton;

    @FXML
    private Button LoginButton;

    @FXML
    private PasswordField PassButton;

    @FXML
    private TextField userButton;

    @FXML
    void CheckLogin(ActionEvent event) throws IOException{

        //SimpleClient.getClient().sendToServer("#LOGIN"+" "+userButton.getText()+" "+PassButton.getText());
    }

    @FXML
    void CloseThePage(ActionEvent event) {
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }

    public void setClient(SimpleClient client) {
        this.chatClient = client;
    }
}