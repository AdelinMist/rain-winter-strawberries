package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Clinic;
import il.ac.haifa.ClinicSystem.entities.DoctorClinic;
import il.ac.haifa.ClinicSystem.entities.VaccineClinic;
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

public class VaccineClinicController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button changeBtn;

    @FXML
    private TableView<VaccineClinic> vaccineClinicTable;

    @FXML
    private TableColumn<VaccineClinic, String> day;

    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<VaccineClinic, String> name;

    @FXML
    private Button exitBtn;

    @FXML
    private TableColumn<VaccineClinic, String> place;

    @FXML
    private TableColumn<VaccineClinic, String> hour;


    private SimpleClient chatClient;
    private List<Clinic> curClinic;
    private List<VaccineClinic> doctorClinics;
    private ObservableList<VaccineClinic> cList = FXCollections.observableArrayList();
    private Alert notSelectedAlert = new Alert(Alert.AlertType.ERROR);


    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        App.setRoot("contentMenu");
    }

    @FXML
    void next_page(ActionEvent event) throws IOException {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
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
        curClinic = chatClient.getClinicList();
        name.setCellValueFactory(new PropertyValueFactory<VaccineClinic, String>("name"));
        place.setCellValueFactory(new PropertyValueFactory<VaccineClinic, String>("place"));
        day.setCellValueFactory(new PropertyValueFactory<VaccineClinic, String>("day"));
        hour.setCellValueFactory(new PropertyValueFactory<VaccineClinic, String>("time"));



        loadData();
    }

    public void loadData() throws InterruptedException {
        if(curClinic!= null) {
            cList.removeAll(cList);
            for (int i = 0; i < curClinic.size(); i++) {
                doctorClinics = curClinic.get(i).getV_app();

                for (VaccineClinic dc : doctorClinics) {
                    dc.setNameProperty(new SimpleStringProperty());
                    dc.setPlaceProperty(new SimpleStringProperty());
                    dc.setName(dc.getClinic().getName());
                    dc.setPlace(dc.getClinic().getLocation());
                }
                for (VaccineClinic c : doctorClinics) {
                    c.setDayProperty(new SimpleStringProperty());
                    c.setTimeProperty(new SimpleStringProperty());
                    c.setTime(c.getVaccine().getTime());
                    c.setDay(c.getVaccine().getDay());
                }
                cList.addAll(doctorClinics);
            }
        }
        vaccineClinicTable.setItems(cList);

    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

    public void setClinic(List<Clinic> c) {
        this.curClinic = c;
    }

}

