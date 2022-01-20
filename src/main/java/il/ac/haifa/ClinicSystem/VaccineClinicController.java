package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Clinic;
import il.ac.haifa.ClinicSystem.entities.Patient;
import il.ac.haifa.ClinicSystem.entities.User;
import il.ac.haifa.ClinicSystem.entities.Vaccine_Appointment;
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
    private TableView<Clinic> vaccineClinicTable;

    @FXML
    private TableColumn<Clinic, DatePicker> dayPicker;

    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<Clinic, String> name;

    @FXML
    private Button exitBtn;

    @FXML
    private TableColumn<Clinic, String> place;

    @FXML
    private TableColumn<Clinic, ChoiceBox<String>> timeOptions;


    private SimpleClient chatClient;
    private List<Clinic> curClinic;
    private List<Vaccine_Appointment> next_vaccines;
    private ObservableList<Clinic> cList = FXCollections.observableArrayList();
    private Alert notSelectedAlert = new Alert(Alert.AlertType.ERROR);
    Alert succsessAlert = new Alert(Alert.AlertType.INFORMATION);


    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        App.setRoot("orderAppointmentMenu");
    }

    @FXML
    void next_page(ActionEvent event) throws IOException, InterruptedException {
        Clinic clinic = vaccineClinicTable.getSelectionModel().getSelectedItem();
        //Vaccine_Appointment vaccine = clinic.getVaccine_appointments().get(0);
        String time = clinic.getTimeOptions().getSelectionModel().getSelectedItem();
        LocalDate date = clinic.getDayPicker().getValue();
        System.out.println(time + "\n" + date + "\n" + clinic.getName());
        Vaccine_Appointment appointment = new Vaccine_Appointment(date, time, clinic);
        clinic.add_vaccine_appointment(appointment);
        Patient user = (Patient)chatClient.getUser();// add the appointment to the clinic
        user.add_vaccine_appointment(appointment);// add the appointment to the user
        appointment.setUser(user);
        appointment.setUsername(user.getName());
        try {
            chatClient.sendToServer(appointment);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadData();
        SendMail mail = new SendMail();
        mail.send_remainder_vaccine(chatClient.getUser(),appointment);

        // HttpApiTester sms = new HttpApiTester();
        //sms.sms();

        succsessAlert.setTitle("Appointment confirmed");
        succsessAlert.setHeaderText("You made an appointment to " + date + " at " + time);
        succsessAlert.showAndWait();
        loadData();
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {

        name.setCellValueFactory(new PropertyValueFactory<Clinic, String>("name"));
        place.setCellValueFactory(new PropertyValueFactory<Clinic, String>("location"));
        dayPicker.setCellValueFactory(new PropertyValueFactory<Clinic, DatePicker>("dayPicker"));
        timeOptions.setCellValueFactory(new PropertyValueFactory<Clinic, ChoiceBox<String>>("timeOptions"));
        //name = new TableColumn<Clinic, String>("name");
        //place = new TableColumn<Clinic, String>("location");
        //dayPicker = new TableColumn<Clinic, DatePicker>("dayPicker");
        //timeOptions = new TableColumn<Clinic, ChoiceBox<String>>("timeOptions");



        loadData();
    }

    public void loadData() throws InterruptedException {

        chatClient.setGotList(false);

        try {
            chatClient.sendToServer("#ClinicList");
        } catch (IOException e) {
            e.printStackTrace();
        }

        synchronized (chatClient.getLock()) {
            while (!chatClient.getGotList()) {

                chatClient.getLock().wait();

            }
        }
        curClinic = chatClient.getClinicList();
        next_vaccines = new ArrayList<>();
        if (curClinic != null) {
            for (int i = 0; i < curClinic.size(); i++) {
                Clinic clinic = curClinic.get(i);

                Vaccine_Appointment c = new Vaccine_Appointment();
                c.setClinic(clinic);
                DatePicker d = new DatePicker();
                // c.setDayPicker(d);
                clinic.setTimeOptions(new ChoiceBox<String>());
                clinic.setDayPicker(d);
                EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        ObservableList<String> data = FXCollections.observableArrayList();
                        int index;
                        switch (d.getValue().getDayOfWeek().toString()) {
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
                        if (index != -1) {
                            List<String> hours = new ArrayList<>();
                            for (LocalTime j = clinic.getCovidVaccOpenHours().get(index); j.isBefore(clinic.getCovidVaccCloseHours().get(index)); j = j.plusMinutes(10)) {
                                //check if the appointment is taken
                                String time = j.toString(); // details of the appointment
                                LocalDate date = clinic.getDayPicker().getValue();
                                List<Vaccine_Appointment> list = clinic.getVaccine_appointments();
                                boolean ok = true;
                                System.out.println(list.size());
                                for (int i = 0; i < list.size(); ++i) {
                                    Vaccine_Appointment vaccine_appointment = list.get(i);
                                    String time_app = vaccine_appointment.getTime();
                                    LocalDate date_app = vaccine_appointment.getDate();
                                    // System.out.println("time app: " + time_app+ "time: " + time +"1"+"\n"+ "date_app: "+ date_app + " date: "+ date);
                                    //System.out.println("time compare: " + (time_app == time) + " date compare " + date.isEqual(date_app) );
                                    if (time_app.equals(time) && date.isEqual(date_app)) {// the appointment is token

                                        ok = false;
                                        break;
                                    }
                                }
                                if (ok) {
                                    hours.add(j.toString());
                                }

                            }
                            data.addAll(hours);
                            clinic.getTimeOptions().setItems(data);
                            clinic.getTimeOptions().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                                if (newSelection != null) {
                                    c.setTime(newSelection);
                                    c.setDay(d.getValue().toString());


                                }
                            });

                        } else {
                            clinic.getTimeOptions().setItems(data);
                        }
                    }
                };
                d.setOnAction(event);



            }


        }
        cList.removeAll(cList);
        cList.addAll(curClinic);
        vaccineClinicTable.setItems(cList);

    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

    public void setClinic(List<Clinic> c) {
        this.curClinic = c;
    }

}
