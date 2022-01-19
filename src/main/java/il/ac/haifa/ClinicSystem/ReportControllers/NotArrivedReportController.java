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

public class NotArrivedReportController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableColumn<WeeklyClinicReport, Integer> covidTest;

    @FXML
    private TableColumn<WeeklyClinicReport, LocalDate> date;

    @FXML
    private TableColumn<WeeklyClinicReport, Integer> family;

    @FXML
    private TableColumn<WeeklyClinicReport, Integer> lab;

    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<WeeklyClinicReport, Integer> nurse;

    @FXML
    private TableColumn<WeeklyClinicReport, Integer> pediatric;

    @FXML
    private TableColumn<WeeklyClinicReport, Integer> professional;

    @FXML
    private TableView<WeeklyClinicReport> reportTable;

    @FXML
    private Button returnBtn;

    @FXML
    private TableColumn<WeeklyClinicReport, Integer> vaccine;

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
        date.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, LocalDate> cellData) ->
                new SimpleObjectProperty<LocalDate>(cellData.getValue().getDateOfCreation()));
        vaccine.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Integer> cellData) ->
                new SimpleIntegerProperty(cellData.getValue().getVaccineNotArrivedCount()).asObject());
        covidTest.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Integer> cellData) ->
                new SimpleIntegerProperty(cellData.getValue().getCovidTestNotArrivedCount()).asObject());
        family.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Integer> cellData) ->
                new SimpleIntegerProperty(cellData.getValue().getFamilyDocNotArrivedCount()).asObject());
        pediatric.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Integer> cellData) ->
                new SimpleIntegerProperty(cellData.getValue().getPediatricDocNotArrivedCount()).asObject());
        professional.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Integer> cellData) ->
                new SimpleIntegerProperty(cellData.getValue().getProfessionalDocNotArrivedCount()).asObject());
        nurse.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Integer> cellData) ->
                new SimpleIntegerProperty(cellData.getValue().getNurseNotArrivedCount()).asObject());
        lab.setCellValueFactory((TableColumn.CellDataFeatures<WeeklyClinicReport, Integer> cellData) ->
                new SimpleIntegerProperty(cellData.getValue().getLabNotArrivedCount()).asObject());

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

