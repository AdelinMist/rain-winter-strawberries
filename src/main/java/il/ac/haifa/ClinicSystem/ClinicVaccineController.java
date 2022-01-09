package il.ac.haifa.ClinicSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class ClinicVaccineController {

    @FXML
    private Button addBtn;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<?> clinicTable;

    @FXML
    private TableColumn<?, ?> date;

    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> place;

    @FXML
    private Button returnBtn;

    @FXML
    void next_page(ActionEvent event) {

    }

    @FXML
    void returnToMenu(ActionEvent event) {

    }
    private SimpleClient chatClient;
    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}
