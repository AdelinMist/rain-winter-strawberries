package il.ac.haifa.ClinicSystem;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import il.ac.haifa.ClinicSystem.entities.*;
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

public class ApinmntMngmntController {


    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<Clinic> clinicTable;


    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<Clinic, String> name;


    @FXML
    private Button returnBtn;

    @FXML
    private Button showAppointments;

    private SimpleClient chatClient;
    private List<Clinic> clinics;
    private ObservableList<Clinic> cList = FXCollections.observableArrayList();
    private Alert notSelectedAlert = new Alert(Alert.AlertType.ERROR);


    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        App.setRoot("clientMenu");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
        name.setCellValueFactory(new PropertyValueFactory<Clinic, String>("name"));
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
        clinics = chatClient.getClinicList();

        List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        for(Clinic c : clinics) {
            ObservableList<String> data = FXCollections.observableArrayList();
            data.addAll(days);
            c.setDayOfWeek(new ChoiceBox<String>(data));
            c.getDayOfWeek().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    switch (newSelection) {
                        case "Sunday":
                            c.setCurOpenHour(c.getOpenHours().get(0).toString());
                            c.setCurCloseHour(c.getCloseHours().get(0).toString());
                            break;
                        case "Monday":
                            c.setCurOpenHour(c.getOpenHours().get(1).toString());
                            c.setCurCloseHour(c.getCloseHours().get(1).toString());
                            break;
                        case "Tuesday":
                            c.setCurOpenHour(c.getOpenHours().get(2).toString());
                            c.setCurCloseHour(c.getCloseHours().get(2).toString());
                            break;
                        case "Wednesday":
                            c.setCurOpenHour(c.getOpenHours().get(3).toString());
                            c.setCurCloseHour(c.getCloseHours().get(3).toString());
                            break;
                        case "Thursday":
                            c.setCurOpenHour(c.getOpenHours().get(4).toString());
                            c.setCurCloseHour(c.getCloseHours().get(4).toString());
                            break;
                        case "Friday":
                            c.setCurOpenHour(c.getOpenHours().get(5).toString());
                            c.setCurCloseHour(c.getCloseHours().get(5).toString());
                            break;
                        default:

                    }
                }
            });
            c.setCurOpenHourProperty(new SimpleStringProperty());
            c.setCurCloseHourProperty(new SimpleStringProperty());
        }

        cList.removeAll(cList);
        cList.addAll(clinics);
        clinicTable.setItems(cList);

    }

    @FXML
    void showAppointments(ActionEvent event) {
        Clinic curClinic = clinicTable.getSelectionModel().getSelectedItem();
        if(curClinic == null){
            notSelectedAlert.setContentText("No Clinic Selected!");
            notSelectedAlert.showAndWait();
            return;
        }

        int counter =1;
        LocalDate date = LocalDate.now();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);


        List<Sister_Appointment> list=curClinic.getSister_appointments1();
        for(Sister_Appointment i: list){

            if(i.getDate().equals(date)){
                List<Sister_Appointment> sisAppList = ((Patient) chatClient.getUser()).getSister_appointments1();
                for (Sister_Appointment appointment : sisAppList) {
                    if (appointment.getDate().equals(date)) {
                        alert.setTitle("Appointment details");
                        alert.setHeaderText("Your number is: " + counter);
                        alert.setContentText("Sister Appointment");
                        counter++;
                        alert.showAndWait();
                    }
                }
            }

        }


        List<Vaccine_Appointment> list1=curClinic.getVaccine_appointments();
        for(Vaccine_Appointment i: list1){

            if(i.getDate().equals(date)){
                List<Vaccine_Appointment> vacAppList = ((Patient) chatClient.getUser()).getVaccine_appointments1();
                for(Vaccine_Appointment appointment : vacAppList){
                    if(appointment.getDate().equals(date)){
                        alert.setTitle("Appointment details");
                        alert.setHeaderText("Your number is: " + counter);
                        alert.setContentText("Vaccine Appointment");
                        counter++;
                        alert.showAndWait();
                    }
                }
            }

        }


        List<Corna_cheak_Appointment> list2=curClinic.getCorna_cheak_Appointments1();
        for(Corna_cheak_Appointment i: list2){

            if(i.getDate().equals(date)){
                List<Corna_cheak_Appointment> crnaChkAppList=((Patient) chatClient.getUser()).getCorna_cheak_Appointments1();
                for(Corna_cheak_Appointment appointment : crnaChkAppList){
                    if(appointment.getDate().equals(date)){
                        alert.setTitle("Appointment details");
                        alert.setHeaderText("Your number is: " + counter);
                        alert.setContentText("Corona check Appointment");
                        counter++;
                        alert.showAndWait();
                    }
                }

            }
        }

        List<FamilyDoctorAppointment> list3=curClinic.getFamily_appointments1();
        for(FamilyDoctorAppointment i: list3){

            if(i.getDate().equals(date)){
                List<FamilyDoctorAppointment> crnaChkAppList=((Patient) chatClient.getUser()).getFamily_Appointments1();
                for(FamilyDoctorAppointment appointment : crnaChkAppList){
                    if(appointment.getDate().equals(date)){
                        alert.setTitle("Appointment details");
                        alert.setHeaderText("Your number is: " + counter);
                        alert.setContentText("Family Doctor Appointment Appointment");
                        counter++;
                        alert.showAndWait();
                    }
                }

            }
        }



    }


    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }


}
