package il.ac.haifa.ClinicSystem.ReportControllers;

import il.ac.haifa.ClinicSystem.ClinicManagerMenuController;
import il.ac.haifa.ClinicSystem.SimpleClient;
import il.ac.haifa.ClinicSystem.entities.Clinic;
import il.ac.haifa.ClinicSystem.entities.WeeklyClinicReport;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class PediatricDocVisitsReportController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<WeeklyClinicReport> reportTable;

    @FXML
    private TableColumn<WeeklyClinicReport, LocalDate> date;

    @FXML
    private TableColumn<WeeklyClinicReport, Integer> friday;

    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<WeeklyClinicReport, Integer> monday;

    @FXML
    private Button returnBtn;

    @FXML
    private TableColumn<WeeklyClinicReport, Integer> sunday;

    @FXML
    private TableColumn<WeeklyClinicReport, Integer> thursday;

    @FXML
    private TableColumn<WeeklyClinicReport, Integer> tuesday;

    @FXML
    private TableColumn<WeeklyClinicReport, Integer> wednesday;

    private SimpleClient chatClient;
    private Clinic thisClinic;
    private List<WeeklyClinicReport> reports;
    private ObservableList<WeeklyClinicReport> rList = FXCollections.observableArrayList();

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        Node n = (Node)event.getSource();
        Scene scene = (n.getScene());
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMenuController.class.getResource("reportVisitsMenu.fxml"));
        ReportVisitsMenuController controller = new ReportVisitsMenuController();
        controller.setParams(chatClient, thisClinic);
        fxmlLoader.setController(controller);
        scene.setRoot(fxmlLoader.load());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
        date.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, LocalDate> cellData) ->
                new SimpleObjectProperty<LocalDate>(cellData.getValue().getDateOfCreation()));
        sunday.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Integer> cellData) ->
                new SimpleIntegerProperty(cellData.getValue().getPediatricDocVisits().get("SUNDAY")).asObject());
        monday.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Integer> cellData) ->
                new SimpleIntegerProperty(cellData.getValue().getPediatricDocVisits().get("MONDAY")).asObject());
        tuesday.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Integer> cellData) ->
                new SimpleIntegerProperty(cellData.getValue().getPediatricDocVisits().get("TUESDAY")).asObject());
        wednesday.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Integer> cellData) ->
                new SimpleIntegerProperty(cellData.getValue().getPediatricDocVisits().get("WEDNESDAY")).asObject());
        thursday.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Integer> cellData) ->
                new SimpleIntegerProperty(cellData.getValue().getPediatricDocVisits().get("THURSDAY")).asObject());
        friday.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Integer> cellData) ->
                new SimpleIntegerProperty(cellData.getValue().getPediatricDocVisits().get("FRIDAY")).asObject());

        loadData();
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

        rList.removeAll(rList);
        rList.addAll(reports);
        reportTable.setItems(rList);

    }

    public void setParams(SimpleClient sc, Clinic c) {
        this.chatClient = sc;
        this.thisClinic = c;
    }

}

