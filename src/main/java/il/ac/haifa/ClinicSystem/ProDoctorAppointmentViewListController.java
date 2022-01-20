package il.ac.haifa.ClinicSystem;

        import il.ac.haifa.ClinicSystem.entities.ProDoctorAppointment;
        import il.ac.haifa.ClinicSystem.entities.User;
        import il.ac.haifa.ClinicSystem.entities.Vaccine_Appointment;
        import javafx.beans.property.SimpleStringProperty;
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

public class ProDoctorAppointmentViewListController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button cancelBtn;

    @FXML
    private TableColumn<ProDoctorAppointment, String> clinic;

    @FXML
    private TableView<ProDoctorAppointment> appointmentTable;

    @FXML
    private TableColumn<ProDoctorAppointment, String> doctor;

    @FXML
    private Label listLbl;

    @FXML
    private Button returnBtn;

    @FXML
    private TableColumn<ProDoctorAppointment, String> spec;


    @FXML
    private TableColumn<ProDoctorAppointment, LocalDate> date;


    @FXML
    private TableColumn<ProDoctorAppointment, String> time;

    private SimpleClient chatClient;
    private List<ProDoctorAppointment> appointments;
    private ObservableList<ProDoctorAppointment> cList = FXCollections.observableArrayList();
    private Alert notSelectedAlert = new Alert(Alert.AlertType.ERROR);

    /**
     * the function reads the selected appointment from the screen
     * and proceeds to delete it by sending a request to the server
     * @param event given to us by the button OnClick, is unnecessary
     * @throws InterruptedException
     */
    @FXML
    void cancelAppointment(ActionEvent event) throws InterruptedException {
        ProDoctorAppointment app = appointmentTable.getSelectionModel().getSelectedItem();
        if(app == null){
            notSelectedAlert.setContentText("No Appointment Selected!");
            notSelectedAlert.showAndWait();
            return;
        }
        int app_id = app.getId();
        chatClient.setGotList(false);

        try {
            chatClient.sendToServer("#Delete_Pro_Doctor_Appointment|"+app_id);
        } catch (IOException e) {
            e.printStackTrace();
        }

        synchronized(chatClient.getLock()) {
            while(!chatClient.getGotList()) {

                chatClient.getLock().wait();

            }
        }

        appointments = chatClient.getProDoctorAppList();

        loadData(appointments);
    }

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        ((Stage)n.getScene().getWindow()).close();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
        date.setCellValueFactory(new PropertyValueFactory<ProDoctorAppointment, LocalDate>("date"));
        clinic.setCellValueFactory(new PropertyValueFactory<ProDoctorAppointment, String>("clinic"));
        time.setCellValueFactory(new PropertyValueFactory<ProDoctorAppointment, String>("time"));
        doctor.setCellValueFactory((TableColumn.CellDataFeatures<ProDoctorAppointment, String> cellData)->new SimpleStringProperty(cellData.getValue().getDoctor().getName()));
        spec.setCellValueFactory((TableColumn.CellDataFeatures<ProDoctorAppointment, String> cellData)->new SimpleStringProperty(cellData.getValue().getDoctor().getSpecialization()));

        loadData(null);
    }

    /**
     *
     * making a request to the server for the Covid Test Appointments of the user List and loading it into the program
     * @param apps
     * @throws InterruptedException
     * @see User
     */
    public void loadData(List<ProDoctorAppointment> apps) throws InterruptedException {
        if(apps == null){
            User user = this.chatClient.getUser();
            int user_id = user.getId();
            chatClient.setGotList(false);

            try {
                chatClient.sendToServer("#ProDoctorAppointments|" + user_id);
            } catch (IOException e) {
                e.printStackTrace();
            }

            synchronized(chatClient.getLock()) {
                while(!chatClient.getGotList()) {

                    chatClient.getLock().wait();

                }
            }
            appointments = chatClient.getProDoctorAppList();
        }

        cList.removeAll(cList);
        cList.addAll(appointments);
        appointmentTable.setItems(cList);

    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}

