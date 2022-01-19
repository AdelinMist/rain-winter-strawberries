package il.ac.haifa.ClinicSystem;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import il.ac.haifa.ClinicSystem.SimpleClient;
import il.ac.haifa.ClinicSystem.entities.Clinic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ChangeHoursCovidController {

    @FXML
    private Button commitBtn;

    @FXML
    private ChoiceBox<String> dayChoice;

    @FXML
    private Button exitBtn;

    @FXML
    private TextField testEndText;

    @FXML
    private TextField testStartText;

    @FXML
    private TextField vaccEndTest;

    @FXML
    private TextField vaccStartText;


    private SimpleClient chatClient;
    private String chosenDay;
    private Clinic newClinic;
    private Clinic curClinic;
    private boolean isValid = false;
    private LocalTime nTime = null;
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    void commitChanges(ActionEvent event) throws IOException {
        String selectedDay = dayChoice.getSelectionModel().getSelectedItem();
        confirmAlert.setContentText("Are you sure you wish to change this clinic entity?");
        confirmAlert.showAndWait().ifPresent(response->{
            if(response == ButtonType.CANCEL) {
                return;
            }
        });
        //works similarly to ChangeHoursController
        newClinic =  new Clinic(curClinic);
        if(selectedDay != null){
            if (!testStartText.getText().isEmpty()) {
                switch (selectedDay) {
                    case "Sunday":
                        newClinic.getCovidTestOpenHours().set(0, LocalTime.parse(testStartText.getText()));
                        break;
                    case "Monday":
                        newClinic.getCovidTestOpenHours().set(1, LocalTime.parse(testStartText.getText()));
                        break;
                    case "Tuesday":
                        newClinic.getCovidTestOpenHours().set(2, LocalTime.parse(testStartText.getText()));
                        break;
                    case "Wednesday":
                        newClinic.getCovidTestOpenHours().set(3, LocalTime.parse(testStartText.getText()));
                        break;
                    case "Thursday":
                        newClinic.getCovidTestOpenHours().set(4, LocalTime.parse(testStartText.getText()));
                        break;
                    case "Friday":
                        newClinic.getCovidTestOpenHours().set(5, LocalTime.parse(testStartText.getText()));
                        break;
                    default:

                }
            }
            if (!testEndText.getText().isEmpty()) {
                switch (selectedDay) {
                    case "Sunday":
                        newClinic.getCovidTestCloseHours().set(0, LocalTime.parse(testEndText.getText()));
                        break;
                    case "Monday":
                        newClinic.getCovidTestCloseHours().set(1, LocalTime.parse(testEndText.getText()));
                        break;
                    case "Tuesday":
                        newClinic.getCovidTestCloseHours().set(2, LocalTime.parse(testEndText.getText()));
                        break;
                    case "Wednesday":
                        newClinic.getCovidTestCloseHours().set(3, LocalTime.parse(testEndText.getText()));
                        break;
                    case "Thursday":
                        newClinic.getCovidTestCloseHours().set(4, LocalTime.parse(testEndText.getText()));
                        break;
                    case "Friday":
                        newClinic.getCovidTestCloseHours().set(5, LocalTime.parse(testEndText.getText()));
                        break;
                    default:

                }
            }
            if (!vaccStartText.getText().isEmpty()) {
                switch (selectedDay) {
                    case "Sunday":
                        newClinic.getCovidVaccOpenHours().set(0, LocalTime.parse(vaccStartText.getText()));
                        break;
                    case "Monday":
                        newClinic.getCovidVaccOpenHours().set(1, LocalTime.parse(vaccStartText.getText()));
                        break;
                    case "Tuesday":
                        newClinic.getCovidVaccOpenHours().set(2, LocalTime.parse(vaccStartText.getText()));
                        break;
                    case "Wednesday":
                        newClinic.getCovidVaccOpenHours().set(3, LocalTime.parse(vaccStartText.getText()));
                        break;
                    case "Thursday":
                        newClinic.getCovidVaccOpenHours().set(4, LocalTime.parse(vaccStartText.getText()));
                        break;
                    case "Friday":
                        newClinic.getCovidVaccOpenHours().set(5, LocalTime.parse(vaccStartText.getText()));
                        break;
                    default:

                }
            }
            if (!vaccEndTest.getText().isEmpty()) {
                switch (selectedDay) {
                    case "Sunday":
                        newClinic.getCovidVaccCloseHours().set(0, LocalTime.parse(vaccEndTest.getText()));
                        break;
                    case "Monday":
                        newClinic.getCovidVaccCloseHours().set(1, LocalTime.parse(vaccEndTest.getText()));
                        break;
                    case "Tuesday":
                        newClinic.getCovidVaccCloseHours().set(2, LocalTime.parse(vaccEndTest.getText()));
                        break;
                    case "Wednesday":
                        newClinic.getCovidVaccCloseHours().set(3, LocalTime.parse(vaccEndTest.getText()));
                        break;
                    case "Thursday":
                        newClinic.getCovidVaccCloseHours().set(4, LocalTime.parse(vaccEndTest.getText()));
                        break;
                    case "Friday":
                        newClinic.getCovidVaccCloseHours().set(5, LocalTime.parse(vaccEndTest.getText()));
                        break;
                    default:

                }
            }
        }

        chatClient.sendToServer(newClinic);

    }

    @FXML
    void exitFromChangeHours(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        ((Stage)n.getScene().getWindow()).close();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws InterruptedException {
        List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        ObservableList<String> data = FXCollections.observableArrayList();
        data.addAll(days);
        dayChoice.setItems(data);
        dayChoice.setValue(chosenDay);
    }

    public void setParams(SimpleClient sc, Clinic c, String chosenDay) {
        this.chatClient = sc;
        this.curClinic = c;
        this.chosenDay = chosenDay;
    }

}
