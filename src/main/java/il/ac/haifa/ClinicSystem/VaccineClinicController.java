package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import java.time.*;
import java.time.chrono.*;
import il.ac.haifa.ClinicSystem.entities.Clinic;
import il.ac.haifa.ClinicSystem.entities.DoctorClinic;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class VaccineClinicController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button changeBtn;

    @FXML
    private TableView<VaccineClinic> vaccineClinicTable;

    @FXML
    private TableColumn<VaccineClinic, DatePicker> dayPicker;

    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<VaccineClinic, String> name;

    @FXML
    private Button exitBtn;

    @FXML
    private TableColumn<VaccineClinic, String> place;

    @FXML
    private TableColumn<VaccineClinic, ChoiceBox<String>> timeOptions;


    private SimpleClient chatClient;
    private List<Clinic> curClinic;
    private List<VaccineClinic> vaccineClinics;
    private ObservableList<VaccineClinic> cList = FXCollections.observableArrayList();
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
        curClinic = chatClient.getClinicList();
        name.setCellValueFactory(new PropertyValueFactory<VaccineClinic, String>("name"));
        place.setCellValueFactory(new PropertyValueFactory<VaccineClinic, String>("place"));
        dayPicker.setCellValueFactory(new PropertyValueFactory<VaccineClinic, DatePicker>("dayPicker"));
        timeOptions.setCellValueFactory(new PropertyValueFactory<VaccineClinic, ChoiceBox<String>>("timeOptions"));



        loadData();
    }

    public void loadData() throws InterruptedException {


        if(curClinic!= null) {
            for (int i = 0; i < curClinic.size(); i++) {
                vaccineClinics = curClinic.get(i).getV_app();
                Clinic clinic = curClinic.get(i);

                for (VaccineClinic dc : vaccineClinics) {
                    dc.setNameProperty(new SimpleStringProperty());
                    dc.setPlaceProperty(new SimpleStringProperty());

                    dc.setName(dc.getClinic().getName());
                    dc.setPlace(dc.getClinic().getLocation());

                }

                for(VaccineClinic c : vaccineClinics) {

                    DatePicker d = new DatePicker();
                    c.setDayPicker(d);
                    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e)
                        {

                            List<String> hours = new ArrayList<>();
                            for (LocalTime j = clinic.getCovidVaccOpenHours().get(0);j.isBefore(clinic.getCovidVaccCloseHours().get(0)); j = j.plusMinutes(10)) {
                                VaccineClinic temp = new VaccineClinic(clinic,new Vaccine_Appointment(d.getValue().toString(),j.toString(),true));
                                if(!clinic.getV_app().contains(temp)) //check if the appointment is taken
                                    hours.add(j.toString());
                            }
                            ObservableList<String> data = FXCollections.observableArrayList();
                            data.addAll(hours);
                            c.setTimeOptions(new ChoiceBox<String>(data));
                            c.getTimeOptions().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                                if (newSelection != null) {
                                    c.setTime(newSelection);
                                    c.setDay(d.getValue().toString());

                                }
                            });
                            c.setTimeProperty(new SimpleStringProperty());
                            c.setDayProperty(new SimpleStringProperty());

                        }
                    };
                    d.setOnAction(event);





                }

            }
        }
        cList.removeAll(cList);
        cList.addAll(vaccineClinics);
        vaccineClinicTable.setItems(cList);

    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

    public void setClinic(List<Clinic> c) {
        this.curClinic = c;
    }

}

