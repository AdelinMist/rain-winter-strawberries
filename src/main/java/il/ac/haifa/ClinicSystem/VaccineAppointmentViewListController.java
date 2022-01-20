package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Clinic;
import il.ac.haifa.ClinicSystem.entities.User;
import il.ac.haifa.ClinicSystem.entities.Vaccine_Appointment;
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

public class VaccineAppointmentViewListController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button cancelBtn;

    @FXML
    private TableColumn<Vaccine_Appointment, String> clinic;

    @FXML
    private TableView<Vaccine_Appointment> appointmentTable;

    @FXML
    private TableColumn<Vaccine_Appointment, LocalDate> date;

    @FXML
    private Label listLbl;

    @FXML
    private Button returnBtn;

    @FXML
    private TableColumn<Vaccine_Appointment, String> time;

    private SimpleClient chatClient;
    private List<Vaccine_Appointment> appointments;
    private ObservableList<Vaccine_Appointment> cList = FXCollections.observableArrayList();
    private Alert notSelectedAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    void cancelAppointment(ActionEvent event) throws InterruptedException {
        Vaccine_Appointment app = appointmentTable.getSelectionModel().getSelectedItem();
        if(app == null){
            notSelectedAlert.setContentText("No Appointment Selected!");
            notSelectedAlert.showAndWait();
            return;
        }
        int app_id = app.getId();
        chatClient.setGotList(false);

        try {
            chatClient.sendToServer("#Delete_Vaccine_Appointment|"+app_id);
        } catch (IOException e) {
            e.printStackTrace();
        }

        synchronized(chatClient.getLock()) {
            while(!chatClient.getGotList()) {

                chatClient.getLock().wait();

            }
        }

        appointments = chatClient.getVacList();

        loadData(appointments);
    }

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        ((Stage)n.getScene().getWindow()).close();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
        date.setCellValueFactory(new PropertyValueFactory<Vaccine_Appointment, LocalDate>("date"));
        clinic.setCellValueFactory(new PropertyValueFactory<Vaccine_Appointment, String>("clinic"));
        time.setCellValueFactory(new PropertyValueFactory<Vaccine_Appointment, String>("time"));

        loadData(null);
    }

    public void loadData(List<Vaccine_Appointment> apps) throws InterruptedException {
        if(apps == null){
            User user = this.chatClient.getUser();
            int user_id = user.getId();

            chatClient.setGotList(false);

            try {
                chatClient.sendToServer("#VaccineAppointments|" + user_id);
            } catch (IOException e) {
                e.printStackTrace();
            }

            synchronized(chatClient.getLock()) {
                while(!chatClient.getGotList()) {

                    chatClient.getLock().wait();

                }
            }
            appointments = chatClient.getVacList();
        }

        cList.removeAll(cList);
        cList.addAll(appointments);
        appointmentTable.setItems(cList);

    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}