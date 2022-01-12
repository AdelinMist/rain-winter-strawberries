/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

package il.ac.haifa.ClinicSystem;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ContentMenuController {

    @FXML
    private Label MenuLabel;

    @FXML
    private Button clinicListBtn;

    @FXML
    private Button covidBtn;

    @FXML
    private Button exitBtn;
    
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

    public void setClient(SimpleClient c) {
      	 this.chatClient = c;
       } 

}
