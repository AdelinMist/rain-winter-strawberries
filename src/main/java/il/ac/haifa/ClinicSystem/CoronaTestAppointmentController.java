package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Clinic;
import il.ac.haifa.ClinicSystem.entities.Corna_cheak_Appointment;
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

public class CoronaTestAppointmentController {

    @FXML
    private Button addBtn;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<Clinic> table;

    @FXML
    private TableColumn<Clinic, String> clinic_name;

    @FXML
    private DatePicker date_pick;

    @FXML
    private TableColumn<Clinic, String> place;

    @FXML
    private Button returnBtn;

    @FXML
    private ChoiceBox<?> time_pick;
    private List<Clinic> clinics;
    private ObservableList<Clinic> cList = FXCollections.observableArrayList();

    @FXML
    void next_page(ActionEvent event) {

    }

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        App.setRoot("clientMenu");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
        clinic_name.setCellValueFactory(new PropertyValueFactory<Clinic, String>("name"));
        place.setCellValueFactory(new PropertyValueFactory<Clinic, String>("location"));
        time_pick = new ChoiceBox<String>();
        date_pick = new DatePicker();


        loadData();
    }

    private void loadData() throws InterruptedException {
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
        cList.removeAll(cList);
        cList.addAll(clinics);
        table.setItems(cList);
        Clinic clinic = table.getSelectionModel().getSelectedItem();
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                ObservableList<String> data = FXCollections.observableArrayList();
                int index = 0;
                switch (date_pick.getValue().getDayOfWeek().toString()){
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
                    for (LocalTime j = clinic.getCovidTestOpenHours().get(index); j.isBefore(clinic.getCovidTestCloseHours().get(index)); j = j.plusMinutes(10)) {
                        //check if the appointment is taken
                        String time = j.toString(); // details of the appointment
                        LocalDate date = date_pick.getValue();
                        List<Corna_cheak_Appointment> list = clinic.getCorna_cheak_Appointments1();
                        boolean ok = true;
                        System.out.println(list.size());
                        for (int i = 0; i < list.size(); ++i) {
                            Corna_cheak_Appointment corna_cheak_appointment = list.get(i);
                            String time_app = corna_cheak_appointment.getTime();
                            LocalDate date_app= corna_cheak_appointment.getDate();
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
                    time_pick = new ChoiceBox<String>(data);
                    time_pick.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                        if (newSelection != null) {
                            //c.setTime(newSelection);
                            //c.setDay(d.getValue().toString());
                            //??????????

                        }
                    });

                }
                else{
                    time_pick = new ChoiceBox<String>(data);
                }
            }
        };
    }



    private SimpleClient chatClient;
    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }



}
