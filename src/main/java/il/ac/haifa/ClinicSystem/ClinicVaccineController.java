package il.ac.haifa.ClinicSystem;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import il.ac.haifa.ClinicSystem.entities.Clinic;
import il.ac.haifa.ClinicSystem.entities.Vaccine_Appointment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import java.time.LocalDate;
public class ClinicVaccineController {

    @FXML
    private Button addBtn;

    @FXML
    private Button doctorClinicBtn;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<Vaccine_Appointment> vaccineTable;


    @FXML
    private TableColumn<Vaccine_Appointment, String> day;

    @FXML
    private TableColumn<Vaccine_Appointment, String> hour;

    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<Clinic, String> place;

    @FXML
    private TableColumn<Clinic, String> name;


    @FXML
    private Button returnBtn;

    private SimpleClient chatClient;
    private Vaccine_Appointment curClinic;
    private List<Vaccine_Appointment> clinics;
    private ObservableList<Vaccine_Appointment> cList = FXCollections.observableArrayList();
    private Alert notSelectedAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        App.setRoot("contentMenu");
    }

    @FXML
    void next_page(ActionEvent event) throws IOException {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {

        name.setCellValueFactory(new PropertyValueFactory<Clinic, String>("name"));
        place.setCellValueFactory(new PropertyValueFactory<Clinic, String>("location"));
        day.setCellValueFactory(new PropertyValueFactory<Vaccine_Appointment, String>("dayOfWeek"));
        hour.setCellValueFactory(new PropertyValueFactory<Vaccine_Appointment, String>("time"));

        chatClient.setGotList(false);

        try {
            chatClient.sendToServer("#ClinicList");
        } catch (IOException e) {
            e.printStackTrace();
        }

        synchronized(chatClient.getLock()) {
            while(!chatClient.getGotList()) {

                chatClient.getLock().wait();

            }
        }
        clinics = chatClient.getVacList();
        cList.removeAll(cList);
        cList.addAll(clinics);
        vaccineTable.setItems(cList);
    }


    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }


}


