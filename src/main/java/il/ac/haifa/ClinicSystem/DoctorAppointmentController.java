package il.ac.haifa.ClinicSystem;
import il.ac.haifa.ClinicSystem.entities.Clinic;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DoctorAppointmentController {
    //when you need the Doctor type, write DoctorSpecialtyController.specialty
    //private String specialty = DoctorSpecialtyController.specialty;
    private SimpleClient chatClient;
    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<Clinic> clinicTable;

    @FXML
    private AnchorPane doctorName;

    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<Clinic, String> name;

    @FXML
    private TableColumn<Clinic, String> doctor;

    @FXML
    private Button nextBTN;

    @FXML
    private TableColumn<Clinic, String> place;

    @FXML
    private Button returnBtn;

    private List<Clinic> clinics;
    private ObservableList<Clinic> cList = FXCollections.observableArrayList();

    @FXML
    void nextPage(ActionEvent event) {

    }

    @FXML
    void returnToMenu(ActionEvent event) {

    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
        name.setCellValueFactory(new PropertyValueFactory<Clinic, String>("name"));
        place.setCellValueFactory(new PropertyValueFactory<Clinic, String>("location"));
        doctor.setCellValueFactory(new PropertyValueFactory<Clinic, String>("doctor")); //might need to change the "doctor"

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

        //List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        for(Clinic c : clinics) {
            ObservableList<String> data = FXCollections.observableArrayList();
        }

        cList.removeAll(cList);
        cList.addAll(clinics);
        clinicTable.setItems(cList);

    }
}

