package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Clinic;
import il.ac.haifa.ClinicSystem.entities.DoctorClinic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class ChangeHoursDoctorController {

    @FXML
    private Button commitBtn;

    @FXML
    private ChoiceBox<String> dayChoice;

    @FXML
    private Button exitBtn;

    @FXML
    private TextField startTime;

    @FXML
    private TextField stopTime;


    private SimpleClient chatClient;
    private String chosenDay;
    private DoctorClinic newDoctorClinic;
    private DoctorClinic curDoctorClinic;
    private boolean isValid = false;
    private LocalTime nTime = null;
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);

    /**
     * <p> See ChangeHoursController for additional explanations about the function </p>
     * @see ChangeHoursController
     * @param event when the button gets pressed it passes event to the function
     * @throws IOException
     */
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
        newDoctorClinic =  new DoctorClinic(curDoctorClinic);
        if (selectedDay != null){
            if(!startTime.getText().isEmpty() && !stopTime.getText().isEmpty()){
                newDoctorClinic.getWorkingHours().put(selectedDay, new Pair<>(LocalTime.parse(startTime.getText()), LocalTime.parse(stopTime.getText())));
            }
            else if (!startTime.getText().isEmpty()) {
                newDoctorClinic.getWorkingHours().put(selectedDay, new Pair<>(LocalTime.parse(startTime.getText()), newDoctorClinic.getWorkingHours().get(selectedDay).getValue()));
            }
            else{
                newDoctorClinic.getWorkingHours().put(selectedDay, new Pair<>(newDoctorClinic.getWorkingHours().get(selectedDay).getKey(), LocalTime.parse(stopTime.getText())));
            }
        }
        chatClient.sendToServer(newDoctorClinic);

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

    public void setParams(SimpleClient sc, DoctorClinic dc, String chosenDay) {
        this.chatClient = sc;
        this.curDoctorClinic = dc;
        this.chosenDay = chosenDay;
    }

}
