package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.ReportControllers.WeeklyReportListController;
import il.ac.haifa.ClinicSystem.entities.Clinic;
import il.ac.haifa.ClinicSystem.entities.WeeklyClinicReport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ClinicManagerMenuController {

    @FXML
    private Label MenuLabel;

    @FXML
    private Button clinicBtn;

    @FXML
    private Button doctorBtn;

    @FXML
    private Button covidBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button showReportsBtn;

    private SimpleClient chatClient;
    private Clinic thisClinic;

    @FXML
    void exitProgram(ActionEvent event) throws IOException {
        chatClient.closeConnection();
    }

    @FXML
    void showClinicHours(ActionEvent event) throws IOException, InterruptedException {
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMenuController.class.getResource("changeHours.fxml"));
        ChangeHoursController controller = new ChangeHoursController();
        controller.setParams(chatClient, thisClinic, null);
        fxmlLoader.setController(controller);
        scene = new Scene(fxmlLoader.load(), 1031, 419);
        stage.setScene(scene);
        stage.showAndWait();
        loadData();
    }

    @FXML
    void showDoctorHours(ActionEvent event) throws IOException, InterruptedException {
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMenuController.class.getResource("doctorClinicList.fxml"));
        DoctorClinicListController controller = new DoctorClinicListController();
        controller.setClient(chatClient);
        controller.setClinic(thisClinic);
        fxmlLoader.setController(controller);
        scene = new Scene(fxmlLoader.load(), 1214, 419);
        stage.setScene(scene);
        stage.showAndWait();
        loadData();
    }

    @FXML
    void showCovidServices(ActionEvent event) throws IOException, InterruptedException {
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMenuController.class.getResource("changeHoursCovid.fxml"));
        ChangeHoursCovidController controller = new ChangeHoursCovidController();
        controller.setParams(chatClient, thisClinic, null);
        fxmlLoader.setController(controller);
        scene = new Scene(fxmlLoader.load(), 1031, 419);
        stage.setScene(scene);
        stage.showAndWait();
        loadData();
    }

    @FXML
    void showWeeklyReports(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMenuController.class.getResource("weeklyReportList.fxml"));
        WeeklyReportListController controller = new WeeklyReportListController();
        controller.setParams(chatClient, thisClinic);
        fxmlLoader.setController(controller);
        scene = new Scene(fxmlLoader.load(), 1031, 419);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void loadData() throws InterruptedException {
        chatClient.setGotClinic(false);

        try {
            chatClient.sendToServer("#Clinic|"+thisClinic.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }

        synchronized(chatClient.getLock()) {
            while(!chatClient.getGotClinic()) {

                chatClient.getLock().wait();

            }
        }
        thisClinic = chatClient.getClinic();
    }

    public void setParams(SimpleClient sc, Clinic c) {
        this.chatClient = sc;
        this.thisClinic = c;
    }

}

