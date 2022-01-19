package il.ac.haifa.ClinicSystem.ReportControllers;

import il.ac.haifa.ClinicSystem.ClinicManagerMenuController;
import il.ac.haifa.ClinicSystem.SimpleClient;
import il.ac.haifa.ClinicSystem.entities.Clinic;
import il.ac.haifa.ClinicSystem.entities.WeeklyClinicReport;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class DoctorWaitTimesReportController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<WeeklyClinicReport> reportTable;

    @FXML
    private TableColumn<WeeklyClinicReport, LocalDate> date;

    @FXML
    private TableColumn<WeeklyClinicReport, ChoiceBox<String>> doctor;

    @FXML
    private TableColumn<WeeklyClinicReport, Double> friday;

    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<WeeklyClinicReport, Double> monday;

    @FXML
    private Button returnBtn;

    @FXML
    private TableColumn<WeeklyClinicReport, Double> sunday;

    @FXML
    private TableColumn<WeeklyClinicReport, Double> thursday;

    @FXML
    private TableColumn<WeeklyClinicReport, Double> tuesday;

    @FXML
    private TableColumn<WeeklyClinicReport, Double> wednesday;

    private SimpleClient chatClient;
    private Clinic thisClinic;
    private List<WeeklyClinicReport> reports;
    private ObservableList<WeeklyClinicReport> rList = FXCollections.observableArrayList();

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

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
        loadData();

        date.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, LocalDate> cellData) ->
                new SimpleObjectProperty<LocalDate>(cellData.getValue().getDateOfCreation()));
        sunday.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Double> cellData) ->
                new SimpleDoubleProperty(cellData.getValue().getDoctorVisitsSunday().get(cellData.getValue().getDoctorOptions().getSelectionModel().getSelectedItem().toString())).asObject());
        monday.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Double> cellData) ->
                new SimpleDoubleProperty(cellData.getValue().getDoctorVisitsMonday().get(cellData.getValue().getDoctorOptions().getSelectionModel().getSelectedItem().toString())).asObject());
        tuesday.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Double> cellData) ->
                new SimpleDoubleProperty(cellData.getValue().getDoctorVisitsTuesday().get(cellData.getValue().getDoctorOptions().getSelectionModel().getSelectedItem().toString())).asObject());
        wednesday.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Double> cellData) ->
                new SimpleDoubleProperty(cellData.getValue().getDoctorVisitsWednesday().get(cellData.getValue().getDoctorOptions().getSelectionModel().getSelectedItem().toString())).asObject());
        thursday.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Double> cellData) ->
                new SimpleDoubleProperty(cellData.getValue().getDoctorVisitsThursday().get(cellData.getValue().getDoctorOptions().getSelectionModel().getSelectedItem().toString())).asObject());
        friday.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Double> cellData) ->
                new SimpleDoubleProperty(cellData.getValue().getDoctorVisitsFriday().get(cellData.getValue().getDoctorOptions().getSelectionModel().getSelectedItem().toString())).asObject());
        doctor.setCellValueFactory(new PropertyValueFactory<WeeklyClinicReport, ChoiceBox<String>>("doctorOptions"));
    }

    public void loadData() throws InterruptedException {
        chatClient.setGotList(false);

        try {
            chatClient.sendToServer("#WeeklyReports|"+thisClinic.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }

        synchronized(chatClient.getLock()) {
            while(!chatClient.getGotList()) {

                chatClient.getLock().wait();

            }
        }
        reports = chatClient.getWeeklyClinicReportList();

        for(WeeklyClinicReport w: reports){
            ObservableList<String> data = FXCollections.observableArrayList();
            data.addAll(w.getDoctorVisitsSunday().keySet());

            w.setDoctorOptions(new ChoiceBox<String>(data));
            /*w.getDoctorOptions().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {

                }
            });*/
        }

        rList.removeAll(rList);
        rList.addAll(reports);
        reportTable.setItems(rList);

    }

    public void setParams(SimpleClient sc, Clinic c) {
        this.chatClient = sc;
        this.thisClinic = c;
    }

}

