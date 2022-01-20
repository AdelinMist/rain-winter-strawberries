package il.ac.haifa.ClinicSystem;
import il.ac.haifa.ClinicSystem.entities.*;
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

public class clinics_of_doctor_typeController {

    @FXML
    private Button addBtn;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableColumn<Clinic, DatePicker> dayPicker;

    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<Clinic, String> name;

    @FXML
    private TableColumn<Clinic, String> place;

    @FXML
    private Button returnBtn;

    @FXML
    private TableColumn<Clinic, ChoiceBox<String>> timeOptions;

    @FXML
    private TableView<Clinic> vaccineClinicTable;

    private SimpleClient chatClient;
    private Doctor doctor = DoctorAppointmentController.doctornext;


    private List<Clinic> curClinic = new ArrayList<>();
    private ObservableList<Clinic> cList = FXCollections.observableArrayList();
    private Alert notSelectedAlert = new Alert(Alert.AlertType.ERROR);
    Alert succsessAlert = new Alert(Alert.AlertType.INFORMATION);
    @FXML
    void nextPage(ActionEvent event) throws InterruptedException {
        Clinic clinic = vaccineClinicTable.getSelectionModel().getSelectedItem();

        String time = clinic.getTimeOptions().getSelectionModel().getSelectedItem();
        LocalDate date = clinic.getDayPicker().getValue();
        System.out.println(time + "\n" + date + "\n" + clinic.getName());
        ProDoctorAppointment appointment = new ProDoctorAppointment(date, time, clinic,DoctorAppointmentController.doctornext);
        clinic.add_pro_Appointment(appointment);
        doctor.add(appointment);
        Patient user = (Patient) chatClient.getUser();// add the appointment to the clinic
        user.add_pro_Appointment(appointment);// add the appointment to the user
        appointment.setUser(user);
        appointment.setUsername(user.getName());
        appointment.setName(doctor.getSpecialization());
        appointment.setDoctor(doctor);
        try {
            chatClient.sendToServer(clinic);
        } catch (IOException e) {
            e.printStackTrace();
        }


        succsessAlert.setTitle("Appointment confirmed");
        succsessAlert.setHeaderText("You made an appointment to " + date + " at " + time);
        succsessAlert.showAndWait();
        SendMail mail = new SendMail();
        mail.send_remainder_pro(chatClient.getUser(),appointment);
        loadData();
    }

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        App.setRoot("orderAppointmentMenu");
    }
    @FXML
    void initialize() throws IOException, InterruptedException {

        name.setCellValueFactory(new PropertyValueFactory<Clinic, String>("name"));
        place.setCellValueFactory(new PropertyValueFactory<Clinic, String>("location"));
        dayPicker.setCellValueFactory(new PropertyValueFactory<Clinic, DatePicker>("dayPicker"));
        timeOptions.setCellValueFactory(new PropertyValueFactory<Clinic, ChoiceBox<String>>("timeOptions"));




        loadData();
    }

    private void loadData() throws InterruptedException {
        List<DoctorClinic> temp = new ArrayList<>();
        curClinic = new ArrayList<>();
         temp = doctor.getDoctorClinics();
        for(int i = 0; i<temp.size(); i++){
            curClinic.add(temp.get(i).getClinic());
        }

        if (curClinic != null) {
            for (int i = 0; i < curClinic.size(); i++) {
                Clinic clinic = curClinic.get(i);
                // if user.ownclinic != clinic.getname() than break!
                ProDoctorAppointment c = new ProDoctorAppointment();
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
                            for (LocalTime j = clinic.getOpenHours().get(index); j.isBefore(clinic.getCloseHours().get(index)); j = j.plusMinutes(10)) {
                                //check if the appointment is taken
                                String time = j.toString(); // details of the appointment
                                LocalDate date = clinic.getDayPicker().getValue();
                                List<ProDoctorAppointment> list = doctor.getAppointments();
                                boolean ok = true;
                                System.out.println(list.size());
                                for (int i = 0; i < list.size(); ++i) {
                                    ProDoctorAppointment appointment = list.get(i);
                                    String time_app = appointment.getTime();
                                    LocalDate date_app = appointment.getDate();
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
}
