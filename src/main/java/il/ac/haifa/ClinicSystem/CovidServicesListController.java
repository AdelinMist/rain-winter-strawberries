package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.*;
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

public class CovidServicesListController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button changeBtn;

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

    @FXML
    private TableColumn<Clinic, String> testEnd;

    @FXML
    private TableColumn<Clinic, String> testStart;

    @FXML
    private TableColumn<Clinic, String> vaccEnd;

    @FXML
    private TableColumn<Clinic, String> vaccStart;

    private SimpleClient chatClient;
    private List<Clinic> clinics;
    private ObservableList<Clinic> cList = FXCollections.observableArrayList();
    private Alert notSelectedAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        App.setRoot("contentMenu");
    }

    @FXML
    void showChangeHours(ActionEvent event) throws InterruptedException, IOException { //change so that choseמ day is displayed on changeHours window, if there is any.
        Clinic curClinic = clinicTable.getSelectionModel().getSelectedItem();
        String chosenDay = curClinic.getDayOfWeek().getSelectionModel().getSelectedItem();
        if(curClinic == null){
            notSelectedAlert.setContentText("No Clinic Selected!");
            notSelectedAlert.showAndWait();
            return;
        }
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicListController.class.getResource("changeHoursCovid.fxml"));
        ChangeHoursCovidController controller = new ChangeHoursCovidController();
        controller.setParams(chatClient, curClinic, chosenDay);
        fxmlLoader.setController(controller);
        scene = new Scene(fxmlLoader.load(), 1031, 419);
        stage.setScene(scene);
        stage.showAndWait();
        loadData();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
        name.setCellValueFactory(new PropertyValueFactory<Clinic, String>("name"));
        place.setCellValueFactory(new PropertyValueFactory<Clinic, String>("location"));
        day.setCellValueFactory(new PropertyValueFactory<Clinic, ChoiceBox<String>>("dayOfWeek"));
        testStart.setCellValueFactory(new PropertyValueFactory<Clinic, String>("curCovidTestOpenHour"));
        testEnd.setCellValueFactory(new PropertyValueFactory<Clinic, String>("curCovidTestCloseHour"));
        vaccStart.setCellValueFactory(new PropertyValueFactory<Clinic, String>("curCovidVaccOpenHour"));
        vaccEnd.setCellValueFactory(new PropertyValueFactory<Clinic, String>("curCovidVaccCloseHour"));

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
            c.setCurCovidTestOpenHourProperty(new SimpleStringProperty());
            c.setCurCovidTestCloseHourProperty(new SimpleStringProperty());
            c.setCurCovidVaccOpenHourProperty(new SimpleStringProperty());
            c.setCurCovidVaccCloseHourProperty(new SimpleStringProperty());
            c.getDayOfWeek().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    switch (newSelection) {
                        case "Sunday":
                            c.setCurCovidTestOpenHour(c.getCovidTestOpenHours().get(0).toString());
                            c.setCurCovidVaccOpenHour(c.getCovidVaccOpenHours().get(0).toString());
                            c.setCurCovidTestCloseHour(c.getCovidTestCloseHours().get(0).toString());
                            c.setCurCovidVaccCloseHour(c.getCovidVaccCloseHours().get(0).toString());
                            break;
                        case "Monday":
                            c.setCurCovidTestOpenHour(c.getCovidTestOpenHours().get(1).toString());
                            c.setCurCovidVaccOpenHour(c.getCovidVaccOpenHours().get(1).toString());
                            c.setCurCovidTestCloseHour(c.getCovidTestCloseHours().get(1).toString());
                            c.setCurCovidVaccCloseHour(c.getCovidVaccCloseHours().get(1).toString());
                            break;
                        case "Tuesday":
                            c.setCurCovidTestOpenHour(c.getCovidTestOpenHours().get(2).toString());
                            c.setCurCovidVaccOpenHour(c.getCovidVaccOpenHours().get(2).toString());
                            c.setCurCovidTestCloseHour(c.getCovidTestCloseHours().get(2).toString());
                            c.setCurCovidVaccCloseHour(c.getCovidVaccCloseHours().get(2).toString());
                            break;
                        case "Wednesday":
                            c.setCurCovidTestOpenHour(c.getCovidTestOpenHours().get(3).toString());
                            c.setCurCovidVaccOpenHour(c.getCovidVaccOpenHours().get(3).toString());
                            c.setCurCovidTestCloseHour(c.getCovidTestCloseHours().get(3).toString());
                            c.setCurCovidVaccCloseHour(c.getCovidVaccCloseHours().get(3).toString());
                            break;
                        case "Thursday":
                            c.setCurCovidTestOpenHour(c.getCovidTestOpenHours().get(4).toString());
                            c.setCurCovidVaccOpenHour(c.getCovidVaccOpenHours().get(4).toString());
                            c.setCurCovidTestCloseHour(c.getCovidTestCloseHours().get(4).toString());
                            c.setCurCovidVaccCloseHour(c.getCovidVaccCloseHours().get(4).toString());
                            break;
                        case "Friday":
                            c.setCurCovidTestOpenHour(c.getCovidTestOpenHours().get(5).toString());
                            c.setCurCovidVaccOpenHour(c.getCovidVaccOpenHours().get(5).toString());
                            c.setCurCovidTestCloseHour(c.getCovidTestCloseHours().get(5).toString());
                            c.setCurCovidVaccCloseHour(c.getCovidVaccCloseHours().get(5).toString());
                            break;
                        default:

                    }
                }
            });

        }

        cList.removeAll(cList);
        cList.addAll(clinics);
        clinicTable.setItems(cList);

    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}

