package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.ReportControllers.WeeklyReportListController;
import il.ac.haifa.ClinicSystem.entities.Clinic;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ChooseClinicMenuController {

    @FXML
    private Button continueBtn;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ListView<Clinic> clinicListView;

    @FXML
    private Label listLbl;

    @FXML
    private Button returnBtn;

    private SimpleClient chatClient;
    private List<Clinic> clinics;
    private ObservableList<Clinic> cList = FXCollections.observableArrayList();
    private Alert notSelectedAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        App.setRoot("systemManagerMenu");
    }

    @FXML
    void showClinicManagerMenu(ActionEvent event) throws IOException {
        Clinic thisClinic = clinicListView.getSelectionModel().getSelectedItem();

        Stage stage = new Stage();
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(ChooseClinicMenuController.class.getResource("weeklyReportList.fxml"));
        WeeklyReportListController controller = new WeeklyReportListController();
        controller.setParams(chatClient, thisClinic);
        fxmlLoader.setController(controller);
        scene = new Scene(fxmlLoader.load(), 1031, 419);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
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

        cList.removeAll(cList);
        cList.addAll(clinics);
        clinicListView.setItems(cList);

    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }


}


