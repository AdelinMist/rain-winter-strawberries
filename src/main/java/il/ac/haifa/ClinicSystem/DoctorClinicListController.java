package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Clinic;
import il.ac.haifa.ClinicSystem.entities.DoctorClinic;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DoctorClinicListController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button changeBtn;

    @FXML
    private TableView<DoctorClinic> doctorClinicTable;

    @FXML
    private TableColumn<DoctorClinic, ChoiceBox<String>> day;

    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<DoctorClinic, String> name;

    @FXML
    private Button exitBtn;

    @FXML
    private TableColumn<DoctorClinic, String> specialization;

    @FXML
    private TableColumn<DoctorClinic, String> startTime;

    @FXML
    private TableColumn<DoctorClinic, String> stopTime;

    private SimpleClient chatClient;
    private Clinic curClinic;
    private List<DoctorClinic> doctorClinics;
    private ObservableList<DoctorClinic> cList = FXCollections.observableArrayList();
    private Alert notSelectedAlert = new Alert(Alert.AlertType.ERROR);

    /**
     * changing the hours of the selected clinic to be the
     * time that was selected in the screen
     * @param event sent by button on Click, we don't need it
     * @throws InterruptedException
     * @throws IOException
     */
    @FXML
    void showChangeDoctorHours(ActionEvent event) throws InterruptedException, IOException {
        DoctorClinic curDoctorClinic = doctorClinicTable.getSelectionModel().getSelectedItem();
        String chosenDay = curDoctorClinic.getDayOfWeek().getSelectionModel().getSelectedItem();
        if(curDoctorClinic == null){
            notSelectedAlert.setContentText("No Doctor Selected!");
            notSelectedAlert.showAndWait();
            return;
        }
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicListController.class.getResource("changeHoursDoctor.fxml"));
        ChangeHoursDoctorController controller = new ChangeHoursDoctorController();
        controller.setParams(chatClient, curDoctorClinic, chosenDay);
        fxmlLoader.setController(controller);
        scene = new Scene(fxmlLoader.load(), 1031, 419);
        stage.setScene(scene);
        chatClient.setGotList(false);
        stage.showAndWait();

        synchronized(chatClient.getLock()) {
            while(!chatClient.getGotList()) {

                chatClient.getLock().wait();

            }
        }
        doctorClinics = chatClient.getDoctorClinicList();
        loadData();
    }

    @FXML
    void exitFromChangeHours(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        ((Stage)n.getScene().getWindow()).close();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
        doctorClinics = curClinic.getDoctorClinics();

        name.setCellValueFactory(new PropertyValueFactory<DoctorClinic, String>("name"));
        specialization.setCellValueFactory(new PropertyValueFactory<DoctorClinic, String>("specialization"));
        day.setCellValueFactory(new PropertyValueFactory<DoctorClinic, ChoiceBox<String>>("dayOfWeek"));
        startTime.setCellValueFactory(new PropertyValueFactory<DoctorClinic, String>("curStartTime"));
        stopTime.setCellValueFactory(new PropertyValueFactory<DoctorClinic, String>("curStopTime"));



        loadData();
    }

    public void loadData() throws InterruptedException {
        for(DoctorClinic dc : doctorClinics) {
            dc.setNameProperty(new SimpleStringProperty());
            dc.setSpecializationProperty(new SimpleStringProperty());
            dc.setName(dc.getDoctor().getName());
            dc.setSpecialization(dc.getDoctor().getSpecialization());
        }
        List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        for(DoctorClinic c : doctorClinics) {
            ObservableList<String> data = FXCollections.observableArrayList();
            data.addAll(days);
            c.setDayOfWeek(new ChoiceBox<String>(data));
            c.getDayOfWeek().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    if(c.getWorkingHours().get(newSelection) != null){
                        c.setCurStartTime(c.getWorkingHours().get(newSelection).getKey().toString());
                        c.setCurStopTime(c.getWorkingHours().get(newSelection).getValue().toString());
                    }
                    else{
                        c.setCurStartTime("");
                        c.setCurStopTime("");
                    }

                }
            });
            c.setCurStartTimeProperty(new SimpleStringProperty());
            c.setCurStopTimeProperty(new SimpleStringProperty());
        }

        cList.removeAll(cList);
        cList.addAll(doctorClinics);
        doctorClinicTable.setItems(cList);

    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

    public void setClinic(Clinic c) {
        this.curClinic = c;
    }

}

