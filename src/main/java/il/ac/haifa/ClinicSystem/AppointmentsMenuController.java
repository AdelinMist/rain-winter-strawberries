package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Clinic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AppointmentsMenuController {

    @FXML
    private Button covidTestAppointmentBtn;

    @FXML
    private Button doctorAppointmentBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private Button vaccineAppointmentBtn;

    private SimpleClient chatClient;

    //buttons that proceed to the next needed screen
    @FXML
    void showCovidTestAppointments(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicListController.class.getResource("covidTestAppointmentViewList.fxml"));
        CovidTestAppointmentViewListController controller = new CovidTestAppointmentViewListController();
        controller.setClient(chatClient);
        fxmlLoader.setController(controller);
        scene = new Scene(fxmlLoader.load(), 773, 445);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void showDoctorAppointments(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicListController.class.getResource("doctorAppointmentViewList.fxml"));
        DoctorAppointmentViewListController controller = new DoctorAppointmentViewListController();
        //controller.setClient(chatClient);
        fxmlLoader.setController(controller);
        scene = new Scene(fxmlLoader.load(), 773, 445);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void showVaccineAppointments(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicListController.class.getResource("vaccineAppointmentViewList.fxml"));
        VaccineAppointmentViewListController controller = new VaccineAppointmentViewListController();
        controller.setClient(chatClient);
        fxmlLoader.setController(controller);
        scene = new Scene(fxmlLoader.load(), 773, 445);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        App.setRoot("clientMenu");
    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }
}
