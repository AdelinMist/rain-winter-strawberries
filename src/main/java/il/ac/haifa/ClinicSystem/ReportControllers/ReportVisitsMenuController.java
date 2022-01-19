package il.ac.haifa.ClinicSystem.ReportControllers;

import il.ac.haifa.ClinicSystem.App;
import il.ac.haifa.ClinicSystem.ClinicManagerMenuController;
import il.ac.haifa.ClinicSystem.SimpleClient;
import il.ac.haifa.ClinicSystem.entities.Clinic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportVisitsMenuController {

    @FXML
    private Button covidTestBtn;

    @FXML
    private Button familyBtn;

    @FXML
    private Button labBtn;

    @FXML
    private Button nurseBtn;

    @FXML
    private Button pediatricBtn;

    @FXML
    private Button professionalBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private Button vaccineBtn;

    private SimpleClient chatClient;
    private Clinic thisClinic;

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        Scene scene = (n.getScene());
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMenuController.class.getResource("weeklyReportList.fxml"));
        WeeklyReportListController controller = new WeeklyReportListController();
        controller.setParams(chatClient, thisClinic);
        fxmlLoader.setController(controller);
        scene.setRoot(fxmlLoader.load());
    }

    @FXML
    void showCovidTestVisits(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        Scene scene = (n.getScene());
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMenuController.class.getResource("covidTestVisitsReport.fxml"));
        CovidTestVisitsReportController controller = new CovidTestVisitsReportController();
        controller.setParams(chatClient, thisClinic);
        fxmlLoader.setController(controller);
        scene.setRoot(fxmlLoader.load());
    }

    @FXML
    void showFamilyVisits(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        Scene scene = (n.getScene());
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMenuController.class.getResource("familyDocVisitsReport.fxml"));
        FamilyDocVisitsReportController controller = new FamilyDocVisitsReportController();
        controller.setParams(chatClient, thisClinic);
        fxmlLoader.setController(controller);
        scene.setRoot(fxmlLoader.load());
    }

    @FXML
    void showLabVisits(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        Scene scene = (n.getScene());
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMenuController.class.getResource("labVisitsReport.fxml"));
        LabVisitsReportController controller = new LabVisitsReportController();
        controller.setParams(chatClient, thisClinic);
        fxmlLoader.setController(controller);
        scene.setRoot(fxmlLoader.load());
    }

    @FXML
    void showNurseVisits(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        Scene scene = (n.getScene());
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMenuController.class.getResource("nurseVisitsReport.fxml"));
        NurseVisitsReportController controller = new NurseVisitsReportController();
        controller.setParams(chatClient, thisClinic);
        fxmlLoader.setController(controller);
        scene.setRoot(fxmlLoader.load());
    }

    @FXML
    void showPediatricVisits(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        Scene scene = (n.getScene());
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMenuController.class.getResource("pediatricDocVisitsReport.fxml"));
        PediatricDocVisitsReportController controller = new PediatricDocVisitsReportController();
        controller.setParams(chatClient, thisClinic);
        fxmlLoader.setController(controller);
        scene.setRoot(fxmlLoader.load());
    }

    @FXML
    void showProfessionalVisits(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        Scene scene = (n.getScene());
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMenuController.class.getResource("professionalDocVisitsReport.fxml"));
        ProfessionalDocVisitsReportController controller = new ProfessionalDocVisitsReportController();
        controller.setParams(chatClient, thisClinic);
        fxmlLoader.setController(controller);
        scene.setRoot(fxmlLoader.load());
    }

    @FXML
    void showVaccineVisits(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        Scene scene = (n.getScene());
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMenuController.class.getResource("vaccineVisitsReport.fxml"));
        VaccineVisitsReportController controller = new VaccineVisitsReportController();
        controller.setParams(chatClient, thisClinic);
        fxmlLoader.setController(controller);
        scene.setRoot(fxmlLoader.load());
    }

    public void setParams(SimpleClient sc, Clinic c) {
        this.chatClient = sc;
        this.thisClinic = c;
    }

}
