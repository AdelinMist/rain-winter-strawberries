package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Clinic;
import il.ac.haifa.ClinicSystem.entities.VaccineClinic;
import il.ac.haifa.ClinicSystem.entities.Vaccine_Appointment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
        App.setRoot("clientMenu");
    }

    @FXML
    void next_page(ActionEvent event) throws IOException, InterruptedException {
        VaccineClinic vaccineClinic = vaccineClinicTable.getSelectionModel().getSelectedItem();
        Clinic clinic = vaccineClinic.getClinic();
        String time = vaccineClinic.getTimeOptions().getSelectionModel().getSelectedItem();
        LocalDate date =vaccineClinic.getDayPicker().getValue();
        System.out.println(time + "\n" + date + "\n" + clinic.getName());
        Vaccine_Appointment appointment = new Vaccine_Appointment(date,time,clinic);
        clinic.add_vaccine_appointment(appointment);
        try {
            chatClient.sendToServer(clinic);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadData();
        //maybe add hear massage like "the appointment added successfully
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {

        name.setCellValueFactory(new PropertyValueFactory<VaccineClinic, String>("name"));
        place.setCellValueFactory(new PropertyValueFactory<VaccineClinic, String>("place"));
        dayPicker.setCellValueFactory(new PropertyValueFactory<VaccineClinic, DatePicker>("dayPicker"));
        timeOptions.setCellValueFactory(new PropertyValueFactory<VaccineClinic, ChoiceBox<String>>("timeOptions"));



        loadData();
    }

    public void loadData() throws InterruptedException {

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
                    c.setTimeOptions(new ChoiceBox<String>());
                    c.setTimeProperty(new SimpleStringProperty());
                    c.setDayProperty(new SimpleStringProperty());
                    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e)
                        {
                            ObservableList<String> data = FXCollections.observableArrayList();
                            int index = 0;
                            switch (d.getValue().getDayOfWeek().toString()){
                                case "SUNDAY":
                                    index = 0;
                                    break;
                                case "MONDAY":
                                    index = 1;
                                    break;
                                case "TUESDAY":
                                    index = 2;
                                    break;
                                case "WEDNESDAY":
                                    index = 3;
                                    break;
                                case "THURSDAY":
                                    index = 4;
                                    break;
                                case "FRIDAY":
                                    index = 5;
                                    break;
                                default:
                                    index = -1;
                            }
                            if(index !=-1) {
                                List<String> hours = new ArrayList<>();
                                for (LocalTime j = clinic.getCovidVaccOpenHours().get(index); j.isBefore(clinic.getCovidVaccCloseHours().get(index)); j = j.plusMinutes(10)) {
                                    //check if the appointment is taken
                                    String time = j.toString(); // details of the appointment
                                    LocalDate date = c.getDayPicker().getValue();
                                    List<Vaccine_Appointment> list = clinic.getVaccine_appointments();
                                    boolean ok = true;
                                    System.out.println(list.size());
                                    for (int i = 0; i < list.size(); ++i) {
                                        Vaccine_Appointment vaccine_appointment = list.get(i);
                                        String time_app = vaccine_appointment.getTime();
                                        LocalDate date_app= vaccine_appointment.getDate();
                                       // System.out.println("time app: " + time_app+ "time: " + time +"1"+"\n"+ "date_app: "+ date_app + " date: "+ date);
                                        //System.out.println("time compare: " + (time_app == time) + " date compare " + date.isEqual(date_app) );
                                        if(time_app.equals(time) && date.isEqual(date_app)){// the appointment is token

                                            ok = false;
                                            break;
                                        }
                                    }
                                    if(ok) {
                                        hours.add(j.toString());
                                    }

                                }
                                data.addAll(hours);
                                c.getTimeOptions().setItems(data);
                                c.getTimeOptions().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                                    if (newSelection != null) {
                                        c.setTime(newSelection);
                                        c.setDay(d.getValue().toString());

                                    }
                                });

                            }
                            else{
                                c.getTimeOptions().setItems(data);
                            }
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

