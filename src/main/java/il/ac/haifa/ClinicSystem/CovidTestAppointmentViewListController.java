package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Corna_cheak_Appointment;
import il.ac.haifa.ClinicSystem.entities.User;
import il.ac.haifa.ClinicSystem.entities.Vaccine_Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CovidTestAppointmentViewListController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button cancelBtn;

    @FXML
    private TableColumn<Corna_cheak_Appointment, String> clinic;

    @FXML
    private TableView<Corna_cheak_Appointment> appointmentTable;

    @FXML
    private TableColumn<Corna_cheak_Appointment, LocalDate> date;

    @FXML
    private Label listLbl;

    @FXML
    private Button returnBtn;

    @FXML
    private TableColumn<Corna_cheak_Appointment, String> time;

    private SimpleClient chatClient;
    private List<Corna_cheak_Appointment> appointments;
    private ObservableList<Corna_cheak_Appointment> cList = FXCollections.observableArrayList();
    private Alert notSelectedAlert = new Alert(Alert.AlertType.ERROR);

    /**
     * the function reads the selected appointment from the screen
     * and proceeds to delete it by sending a request to the server
     * @param event given to us by the button OnClick, is unnecessary
     * @throws InterruptedException
     */
    @FXML
    void cancelAppointment(ActionEvent event) throws InterruptedException {
        Corna_cheak_Appointment app = appointmentTable.getSelectionModel().getSelectedItem();
        if(app == null){
            notSelectedAlert.setContentText("No Appointment Selected!");
            notSelectedAlert.showAndWait();
            return;
        }
        int app_id = app.getId();
        chatClient.setGotList(false);

        try {
            chatClient.sendToServer("#Delete_Covid_Test_Appointment|"+app_id);
        } catch (IOException e) {
            e.printStackTrace();
        }

        synchronized(chatClient.getLock()) {
            while(!chatClient.getGotList()) {

                chatClient.getLock().wait();

            }
        }

        appointments = chatClient.getCovidTestAppList();

        loadData(appointments);
    }

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        ((Stage)n.getScene().getWindow()).close();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
        date.setCellValueFactory(new PropertyValueFactory<Corna_cheak_Appointment, LocalDate>("date"));
        clinic.setCellValueFactory(new PropertyValueFactory<Corna_cheak_Appointment, String>("clinic"));
        time.setCellValueFactory(new PropertyValueFactory<Corna_cheak_Appointment, String>("time"));

        loadData(null);
    }

    /**
     *
     * making a request to the server for the Covid Test Appointments of the user List and loading it into the program
     * @param apps
     * @throws InterruptedException
     * @see User
     */
    public void loadData(List<Corna_cheak_Appointment> apps) throws InterruptedException {
        if(apps == null){
            User user = this.chatClient.getUser();
            int user_id = user.getId();
            chatClient.setGotList(false);

            try {
                chatClient.sendToServer("#CovidTestAppointments|" + user_id);
            } catch (IOException e) {
                e.printStackTrace();
            }

            synchronized(chatClient.getLock()) {
                while(!chatClient.getGotList()) {

                    chatClient.getLock().wait();

                }
            }
            appointments = chatClient.getCovidTestAppList();
        }

        cList.removeAll(cList);
        cList.addAll(appointments);
        appointmentTable.setItems(cList);

    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}
