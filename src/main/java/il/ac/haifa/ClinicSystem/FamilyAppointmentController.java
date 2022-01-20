package il.ac.haifa.ClinicSystem;
import il.ac.haifa.ClinicSystem.entities.Clinic;
import il.ac.haifa.ClinicSystem.entities.FamilyDoctorAppointment;
import il.ac.haifa.ClinicSystem.entities.Patient;
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
import java.util.Arrays;
import java.util.List;

    public class FamilyAppointmentController {

        @FXML
        private Button addBtn;

        @FXML
        private BorderPane borderPane;

        @FXML
        private TableColumn<Clinic, DatePicker> dayPicker;

        @FXML
        private Label listLbl;

        @FXML
        private TableColumn<Clinic,ChoiceBox<String>> family;

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


        private List<Clinic> curClinic;
        private int clinicIndex = -1;
        private List<FamilyDoctorAppointment> next_vaccines;
        private ObservableList<Clinic> cList = FXCollections.observableArrayList();
        private Alert notSelectedAlert = new Alert(Alert.AlertType.ERROR);
        Alert succsessAlert = new Alert(Alert.AlertType.INFORMATION);

        /**
         * We add the selected appointment into our DB
         * and proceed
         * @param event passed by Button OnClick, we don't use it
         * @throws InterruptedException
         */
        @FXML
        void nextPage(ActionEvent event) throws InterruptedException {
            Clinic clinic = vaccineClinicTable.getSelectionModel().getSelectedItem();

            String time = clinic.getTimeOptions().getSelectionModel().getSelectedItem();
            LocalDate date = clinic.getDayPicker().getValue();
            System.out.println(time + "\n" + date + "\n" + clinic.getName());
            FamilyDoctorAppointment appointment = new FamilyDoctorAppointment(date, time, clinic);
            clinic.add_Family_Appointment(appointment);
            Patient user = (Patient) chatClient.getUser();// add the appointment to the clinic
            user.add_Family_Appointment(appointment);// add the appointment to the user
            appointment.setUser(user);
            appointment.setUsername(user.getName());
            boolean is_family = false;
            if(clinic.getFamily().getValue().equals("family"))
                is_family = true;
            appointment.setIs_family(is_family);
            try {
                chatClient.sendToServer(appointment);
            } catch (IOException e) {
                e.printStackTrace();
            }


            succsessAlert.setTitle("Appointment confirmed");
            succsessAlert.setHeaderText("You made an appointment to " + date + " at " + time);
            succsessAlert.showAndWait();
            SendMail mail = new SendMail();
            mail.send_remainder_family(chatClient.getUser(),appointment);
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
            family.setCellValueFactory(new PropertyValueFactory<Clinic, ChoiceBox<String>>("family"));




            loadData();
        }

        private void loadData() throws InterruptedException {
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
                    Patient temp = (Patient)chatClient.getUser();
                    if(curClinic.get(i).getName().equals(temp.getClinic_name()))
                        clinicIndex = i;
                }
                if(clinicIndex != -1) {
                    // if user.ownclinic != clinic.getname() than break!
                    FamilyDoctorAppointment c = new FamilyDoctorAppointment();
                    c.setClinic(curClinic.get(clinicIndex));
                    DatePicker d = new DatePicker();
                    // c.setDayPicker(d);
                    List<String> days = Arrays.asList("family", "children");
                    ObservableList<String> data1 = FXCollections.observableArrayList();
                    data1.addAll(days);
                    curClinic.get(clinicIndex).setFamily(new ChoiceBox<String>(data1));
                    curClinic.get(clinicIndex).setTimeOptions(new ChoiceBox<String>());
                    curClinic.get(clinicIndex).setDayPicker(d);
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
                                for (LocalTime j = curClinic.get(clinicIndex).getOpenHours().get(index); j.isBefore(curClinic.get(clinicIndex).getCloseHours().get(index)); j = j.plusMinutes(10)) {
                                    //check if the appointment is taken
                                    String time = j.toString(); // details of the appointment
                                    LocalDate date = curClinic.get(clinicIndex).getDayPicker().getValue();
                                    List<FamilyDoctorAppointment> list = curClinic.get(clinicIndex).getFamily_appointments1();
                                    boolean ok = true;
                                    System.out.println(list.size());
                                    for (int i = 0; i < list.size(); ++i) {
                                        FamilyDoctorAppointment appointment = list.get(i);
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
                                curClinic.get(clinicIndex).getTimeOptions().setItems(data);
                                curClinic.get(clinicIndex).getTimeOptions().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                                    if (newSelection != null) {
                                        c.setTime(newSelection);
                                        c.setDay(d.getValue().toString());


                                    }
                                });

                            } else {
                                curClinic.get(clinicIndex).getTimeOptions().setItems(data);
                            }
                        }
                    };
                    d.setOnAction(event);


                }


        }
        cList.removeAll(cList);
        cList.addAll(curClinic.get(clinicIndex));
        vaccineClinicTable.setItems(cList);
    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }
}



