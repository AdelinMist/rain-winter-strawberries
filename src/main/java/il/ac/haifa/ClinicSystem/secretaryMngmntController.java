package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class secretaryMngmntController {

    @FXML
    private TableView<Corna_cheak_Appointment> CCAppointment;

    @FXML
    private TableView<FamilyDoctorAppointment> FDAppointment;

    @FXML
    private TableView<labAppointment> LabAppointment;

    @FXML
    private TableView<ProDoctorAppointment> PDAppointment;

    @FXML
    private TableView<Sister_Appointment> SisAppointment;

    @FXML
    private TableView<Vaccine_Appointment> VacAppointment;

    @FXML
    private TableColumn<Corna_cheak_Appointment, String> hourCC;

    @FXML
    private TableColumn<FamilyDoctorAppointment, String> hourFD;

    @FXML
    private TableColumn<labAppointment, String> hourLab;

    @FXML
    private TableColumn<ProDoctorAppointment, String> hourPD;

    @FXML
    private TableColumn<Sister_Appointment, String> hourSis;

    @FXML
    private TableColumn<Vaccine_Appointment, String> hourVac;

    @FXML
    private TableColumn<Corna_cheak_Appointment, String> nameCC;

    @FXML
    private TableColumn<FamilyDoctorAppointment, String> nameFD;

    @FXML
    private TableColumn<labAppointment, String> nameLab;

    @FXML
    private TableColumn<ProDoctorAppointment, String> namePD;

    @FXML
    private TableColumn<Sister_Appointment, String> nameSis;

    @FXML
    private TableColumn<Vaccine_Appointment, String> nameVac;

    private SimpleClient chatClient;


    private Clinic currClinic;

    List<Corna_cheak_Appointment> cCA;
    List<FamilyDoctorAppointment> fDA;
    List<labAppointment> labA;
    List<ProDoctorAppointment> pDA;
    List<Sister_Appointment> sisA;
    List<Vaccine_Appointment> vacA;


    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
        currClinic = ((Secretary) chatClient.getUser()).getClinic();

        cCA = currClinic.getCorna_cheak_Appointments1();
        fDA = currClinic.getFamily_appointments1();
        labA = currClinic.getLab_appointments1();
        pDA = currClinic.getProAppointments();
        sisA = currClinic.getSister_appointments1();
        vacA = currClinic.getVaccine_appointments();

        nameCC.setCellValueFactory(new PropertyValueFactory<Corna_cheak_Appointment, String>("username"));
        hourCC.setCellValueFactory(new PropertyValueFactory<Corna_cheak_Appointment, String>("time"));

        nameFD.setCellValueFactory(new PropertyValueFactory<FamilyDoctorAppointment, String>("username"));
        hourFD.setCellValueFactory(new PropertyValueFactory<FamilyDoctorAppointment, String>("time"));

        nameLab.setCellValueFactory(new PropertyValueFactory<labAppointment, String>("username"));
        hourLab.setCellValueFactory(new PropertyValueFactory<labAppointment, String>("time"));

        namePD.setCellValueFactory(new PropertyValueFactory<ProDoctorAppointment, String>("username"));
        hourPD.setCellValueFactory(new PropertyValueFactory<ProDoctorAppointment, String>("time"));

        nameSis.setCellValueFactory(new PropertyValueFactory<Sister_Appointment, String>("username"));
        hourSis.setCellValueFactory(new PropertyValueFactory<Sister_Appointment, String>("time"));

        nameVac.setCellValueFactory(new PropertyValueFactory<Vaccine_Appointment, String>("username"));
        hourVac.setCellValueFactory(new PropertyValueFactory<Vaccine_Appointment, String>("time"));

        loadData();
    }


    private ObservableList<ProDoctorAppointment> PDList = FXCollections.observableArrayList();
    private ObservableList<Corna_cheak_Appointment> CCList = FXCollections.observableArrayList();
    private ObservableList<FamilyDoctorAppointment> FDList = FXCollections.observableArrayList();
    private ObservableList<labAppointment> labList = FXCollections.observableArrayList();
    private ObservableList<Sister_Appointment> sisList = FXCollections.observableArrayList();
    private ObservableList<Vaccine_Appointment> vacList = FXCollections.observableArrayList();

    public void loadData() throws InterruptedException {
        PDList.removeAll(PDList);
        PDList.addAll(pDA);
        PDAppointment.setItems(PDList);

        CCList.removeAll(CCList);
        CCList.addAll(cCA);
        CCAppointment.setItems(CCList);

        FDList.removeAll(FDList);
        FDList.addAll(fDA);
        FDAppointment.setItems(FDList);

        labList.removeAll(labList);
        labList.addAll(labA);
        LabAppointment.setItems(labList);

        sisList.removeAll(sisList);
        sisList.addAll(sisA);
        SisAppointment.setItems(sisList);

        vacList.removeAll(vacList);
        vacList.addAll(vacA);
        VacAppointment.setItems(vacList);


    }


    @FXML
    void returnToMenu(ActionEvent event) {

    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}
