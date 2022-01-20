
package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.SimpleClient;
import il.ac.haifa.ClinicSystem.entities.Clinic;
import il.ac.haifa.ClinicSystem.entities.User;
import il.ac.haifa.ClinicSystem.entities.FamilyDoctorAppointment;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class FamilyDoctorAppointmentViewListController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button cancelBtn;

    @FXML
    private TableColumn<FamilyDoctorAppointment, String> clinic;

    @FXML
    private TableView<FamilyDoctorAppointment> appointmentTable;

    @FXML
    private TableColumn<FamilyDoctorAppointment, LocalDate> date;

    @FXML
    private Label listLbl;

    @FXML
    private Button returnBtn;

    @FXML
    private TableColumn<FamilyDoctorAppointment, String> time;

    private SimpleClient chatClient;
    private List<FamilyDoctorAppointment> appointments;
    private ObservableList<FamilyDoctorAppointment> cList = FXCollections.observableArrayList();
    private Alert notSelectedAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    void cancelAppointment(ActionEvent event) throws InterruptedException {
        FamilyDoctorAppointment app = appointmentTable.getSelectionModel().getSelectedItem();
        if(app == null){
            notSelectedAlert.setContentText("No Appointment Selected!");
            notSelectedAlert.showAndWait();
            return;
        }
        int app_id = app.getId();
        chatClient.setGotList(false);

        try {
            chatClient.sendToServer("#Delete_Family_Appointment|"+app_id);
        } catch (IOException e) {
            e.printStackTrace();
        }

        synchronized(chatClient.getLock()) {
            while(!chatClient.getGotList()) {

                chatClient.getLock().wait();

            }
        }

        appointments = chatClient.getFamilyDoctorAppList();

        loadData(appointments);
    }

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        ((Stage)n.getScene().getWindow()).close();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
        date.setCellValueFactory(new PropertyValueFactory<FamilyDoctorAppointment, LocalDate>("date"));
        clinic.setCellValueFactory(new PropertyValueFactory<FamilyDoctorAppointment, String>("clinic"));
        time.setCellValueFactory(new PropertyValueFactory<FamilyDoctorAppointment, String>("time"));

        loadData(null);
    }

    public void loadData(List<FamilyDoctorAppointment> apps) throws InterruptedException {
        if(apps == null){
            User user = this.chatClient.getUser();
            int user_id = user.getId();

            chatClient.setGotList(false);

            try {
                chatClient.sendToServer("#FamilyAppointments|" + user_id);
            } catch (IOException e) {
                e.printStackTrace();
            }

            synchronized(chatClient.getLock()) {
                while(!chatClient.getGotList()) {

                    chatClient.getLock().wait();

                }
            }
            appointments = chatClient.getFamilyDoctorAppList();
        }

        cList.removeAll(cList);
        cList.addAll(appointments);
        appointmentTable.setItems(cList);

    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}

