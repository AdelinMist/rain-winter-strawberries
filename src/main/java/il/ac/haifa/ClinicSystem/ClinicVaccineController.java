package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Clinic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ClinicVaccineController {

    @FXML
    private Button addBtn;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<?> clinicTable;

    @FXML
    private TableColumn<Clinic, String> date;

    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<Clinic, String> name;

    @FXML
    private TableColumn<Clinic, String> place;

    @FXML
    private Button returnBtn;

    @FXML
    void next_page(ActionEvent event) {

    }

    @FXML
    void returnToMenu(ActionEvent event) {

    }

    void initialize() throws IOException, InterruptedException {
        name.setCellValueFactory(new PropertyValueFactory<Clinic, String>("name"));
        place.setCellValueFactory(new PropertyValueFactory<Clinic, String>("location"));
        date.cellFactoryProperty(new PropertyValueFactory<Clinic,String>("date"));


    }

    private SimpleClient chatClient;
    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}
