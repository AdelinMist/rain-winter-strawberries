package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Clinic;
import il.ac.haifa.ClinicSystem.entities.Doctor;
import il.ac.haifa.ClinicSystem.entities.ProDoctorAppointment;
import il.ac.haifa.ClinicSystem.entities.Vaccine_Appointment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class doctorApinmntMngmntController {

    @FXML
    private TableView<ProDoctorAppointment> apointmentsTable;

    @FXML
    private BorderPane borderPane;


    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<ProDoctorAppointment, String> name;


    @FXML
    private Button returnBtn;

    @FXML
    private TableColumn<ProDoctorAppointment, String> time;

    private SimpleClient chatClient;


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
        name.setCellValueFactory(new PropertyValueFactory<ProDoctorAppointment, String>("name"));
        time.setCellValueFactory(new PropertyValueFactory<ProDoctorAppointment, String>("time"));
        loadData();
    }

    private List<ProDoctorAppointment> appointments;
    private ObservableList<ProDoctorAppointment> aList = FXCollections.observableArrayList();
    public void loadData() throws InterruptedException {
        chatClient.setGotList(false);

        try {
            chatClient.sendToServer("#ClinicList");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //making sure we are concurrency safe
        synchronized(chatClient.getLock()) {
            while(!chatClient.getGotList()) {

                chatClient.getLock().wait();

            }
        }
        appointments = ((Doctor)chatClient.getUser()).getAppointments();


        /*for(ProDoctorAppointment a : appointments) {
            ObservableList<String> data = FXCollections.observableArrayList();
            //we are loading a list of the days in the week in order to load them for the choice box

        }*/

        aList.removeAll(aList);
        aList.addAll(appointments);
        apointmentsTable.setItems(aList);

    }



    @FXML
    void returnToMenu(ActionEvent event) {

    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}
