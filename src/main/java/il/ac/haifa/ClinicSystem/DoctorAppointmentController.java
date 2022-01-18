package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DoctorAppointmentController {
    //when you need the Doctor type, write DoctorSpecialtyController.specialty
    //private String specialty = DoctorSpecialtyController.specialty;
    private SimpleClient chatClient;
    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

    @FXML
    private Button addBtn;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableColumn<Doctor, String> name;

    @FXML
    private Label listLbl;

    @FXML
    private Button returnBtn;

    @FXML
    private TableView<Doctor> vaccineClinicTable;
    public static Doctor doctornext;

    private List<Doctor> doctors;
    private ObservableList<Doctor> cList = FXCollections.observableArrayList();

    @FXML
    void nextPage(ActionEvent event) throws IOException {
        doctornext = vaccineClinicTable.getSelectionModel().getSelectedItem();
        App.setRoot("clinics_of_doctor_type");
    }

    @FXML
    void returnToMenu(ActionEvent event) {

    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
        name.setCellValueFactory(new PropertyValueFactory<Doctor, String>("name")); //might need to change the "doctor"

        loadData();
    }

    public void loadData() throws InterruptedException {
        chatClient.setGotList(false);

        try {
            chatClient.sendToServer("#Doctors");
        } catch (IOException e) {
            e.printStackTrace();
        }

        synchronized(chatClient.getLock()) {
            while(!chatClient.getGotList()) {

                chatClient.getLock().wait();

            }
        }
        doctors = chatClient.getDoctorList();
        List<Doctor> doctors1 = new ArrayList<>();

       String type = DoctorSpecialtyController.specialty;
        for(Doctor doctor : doctors) {
            if(doctor.getSpecialization().equals(type))
                doctors1.add(doctor);
        }

        cList.removeAll(cList);
        cList.addAll(doctors1);
        vaccineClinicTable.setItems(cList);

    }
}

