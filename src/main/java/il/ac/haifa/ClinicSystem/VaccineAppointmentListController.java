package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Clinic;
import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.layout.BorderPane;

public class VaccineAppointmentListController {
    private Clinic clinic;// all of these 6 parameters i need to get from the previous page with set parameters function
    private String day;
    private String week;
    private String start_time; // from when can vaccine in this clinic
    private String end_time; // to when
    private SimpleClient chatClient;

    private String time;


    @FXML
    private Button addBtn;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<?> clinicTable;

    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private Button returnBtn;

    @FXML
    void next_page(ActionEvent event) {

    }

    @FXML
    void returnToMenu(ActionEvent event) {

    }
    public void setPram(String day, String week,Clinic clinic ,SimpleClient c ){
        this.day = day;
        this.clinic = clinic;
        this.week = week;
        this.chatClient = c;
        this.start_time = clinic.getCurCovidVaccOpenHour();
        this.end_time = clinic.getCurCovidVaccCloseHour();
    }
    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}

