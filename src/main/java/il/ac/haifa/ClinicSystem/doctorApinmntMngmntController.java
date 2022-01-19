package il.ac.haifa.ClinicSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class doctorApinmntMngmntController {

    @FXML
    private TableView<?> apointmentsTable;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableColumn<?, ?> dayPicker;

    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private Button nextInline;

    @FXML
    private Button returnBtn;

    @FXML
    private TableColumn<?, ?> timeOptions;

    @FXML
    void nextInline(ActionEvent event) {

    }

    @FXML
    void returnToMenu(ActionEvent event) {

    }


}
