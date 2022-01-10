package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Clinic;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ClinicVaccineController {

    @FXML
    private Button addBtn;

    @FXML
    private Button doctorClinicBtn;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<Clinic> clinicTable;


    @FXML
    private TableColumn<Clinic, ChoiceBox<String>> day;

    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<Clinic, String> place;

    @FXML
    private TableColumn<Clinic, String> name;

    @FXML
    private TableColumn<Clinic, ChoiceBox<String>> week;

    @FXML
    private Button returnBtn;

    private SimpleClient chatClient;
    private List<Clinic> clinics;
    private ObservableList<Clinic> cList = FXCollections.observableArrayList();
    private Alert notSelectedAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    void showChangeHours(ActionEvent event) throws InterruptedException, IOException { //change so that chosed day is displayed on changehours window, if there is any.
        Clinic curClinic = clinicTable.getSelectionModel().getSelectedItem();
        String chosenDay = curClinic.getDayOfWeek().getSelectionModel().getSelectedItem();
        if(curClinic == null){
            notSelectedAlert.setContentText("No Clinic Selected!");
            notSelectedAlert.showAndWait();
            return;
        }
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicListController.class.getResource("changeHours.fxml"));
        ChangeHoursController controller = new ChangeHoursController();
        controller.setParams(chatClient, curClinic, chosenDay);
        fxmlLoader.setController(controller);
        scene = new Scene(fxmlLoader.load(), 1031, 419);
        stage.setScene(scene);
        stage.showAndWait();
        loadData();
    }

    @FXML
    void showDoctorClinic(ActionEvent event) throws InterruptedException, IOException {
        Clinic curClinic = clinicTable.getSelectionModel().getSelectedItem();
        if(curClinic == null){
            notSelectedAlert.setContentText("No Clinic Selected!");
            notSelectedAlert.showAndWait();
            return;
        }
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(DoctorClinicListController.class.getResource("doctorClinicList.fxml"));
        DoctorClinicListController controller = new DoctorClinicListController();
        controller.setClient(chatClient);
        controller.setClinic(curClinic);
        fxmlLoader.setController(controller);
        scene = new Scene(fxmlLoader.load(), 1214, 419);
        stage.setScene(scene);
        stage.showAndWait();
        loadData();
    }

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        App.setRoot("contentMenu");
    }

    @FXML
    void next_page(ActionEvent event) throws IOException, InterruptedException {
        Clinic curClinic = clinicTable.getSelectionModel().getSelectedItem();
        String chosenDay = curClinic.getDayOfWeek().getSelectionModel().getSelectedItem();
        String chosenWeek = curClinic.getWeekofvacciene().getSelectionModel().getSelectedItem();
        if(curClinic == null){
            notSelectedAlert.setContentText("No Clinic Selected!");
            notSelectedAlert.showAndWait();
            return;
        }
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(VaccineAppointmentListController.class.getResource("vaccineAppointmentList.fxml"));
        VaccineAppointmentListController controller = new VaccineAppointmentListController();
        controller.setPram(chosenDay,chosenWeek,curClinic,chatClient);
        fxmlLoader.setController(controller);
        scene = new Scene(fxmlLoader.load(), 1214, 419);
        stage.setScene(scene);
        stage.showAndWait();
        loadData();

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
        name.setCellValueFactory(new PropertyValueFactory<Clinic, String>("name"));
        place.setCellValueFactory(new PropertyValueFactory<Clinic, String>("location"));
        day.setCellValueFactory(new PropertyValueFactory<Clinic, ChoiceBox<String>>("dayOfWeek"));
        week.setCellValueFactory(new PropertyValueFactory<Clinic, ChoiceBox<String>>("weekofvacciene"));


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
        List<String> weeks = Arrays.asList("0","1", "2", "3", "4", "5", "6");
        List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");

        for(Clinic c : clinics) {
            ObservableList<String> data = FXCollections.observableArrayList();
            data.addAll(days);
            c.setDayOfWeek(new ChoiceBox<String>(data));
            ObservableList<String> data1 = FXCollections.observableArrayList();
            data1.addAll(weeks);
            c.setWeekofvacciene(new ChoiceBox<String>(data1));

        }

        cList.removeAll(cList);
        cList.addAll(clinics);
        clinicTable.setItems(cList);

    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }


}


