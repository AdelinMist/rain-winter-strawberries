package il.ac.haifa.ClinicSystem.ReportControllers;

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

public class WeeklyReportListController {

    @FXML
    private Button doctorWaitTimesBtn;

    @FXML
    private Button notArrivedBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button visitBtn;

    private SimpleClient chatClient;
    private Clinic thisClinic;

    @FXML
    void exit(ActionEvent event) {
        Node n = (Node)event.getSource();
        ((Stage)n.getScene().getWindow()).close();
    }

    @FXML
    void showDoctorWaitTimesReport(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        Scene scene = (n.getScene());
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMenuController.class.getResource("doctorWaitTimesReport.fxml"));
        DoctorWaitTimesReportController controller = new DoctorWaitTimesReportController();
        controller.setParams(chatClient, thisClinic);
        fxmlLoader.setController(controller);
        scene.setRoot(fxmlLoader.load());
    }

    @FXML
    void showNotArrivedReport(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        Scene scene = (n.getScene());
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMenuController.class.getResource("notArrivedReport.fxml"));
        NotArrivedReportController controller = new NotArrivedReportController();
        controller.setParams(chatClient, thisClinic);
        fxmlLoader.setController(controller);
        scene.setRoot(fxmlLoader.load());
    }

    @FXML
    void showVisitsReportMenu(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        Scene scene = (n.getScene());
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMenuController.class.getResource("reportVisitsMenu.fxml"));
        ReportVisitsMenuController controller = new ReportVisitsMenuController();
        controller.setParams(chatClient, thisClinic);
        fxmlLoader.setController(controller);
        scene.setRoot(fxmlLoader.load());
    }

    public void setParams(SimpleClient sc, Clinic c) {
        this.chatClient = sc;
        this.thisClinic = c;
    }

}

