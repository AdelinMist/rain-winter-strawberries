package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Clinic;
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
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicListController.class.getResource("doctorClinicList.fxml"));
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
    void next_page(ActionEvent event) throws IOException {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
        name.setCellValueFactory(new PropertyValueFactory<Clinic, String>("name"));
        place.setCellValueFactory(new PropertyValueFactory<Clinic, String>("location"));
        day.setCellValueFactory(new PropertyValueFactory<Clinic, ChoiceBox<String>>("dayOfWeek"));


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

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }


}


