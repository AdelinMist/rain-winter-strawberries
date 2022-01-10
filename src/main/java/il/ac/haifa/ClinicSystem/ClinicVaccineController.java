package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Clinic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;

public class ClinicVaccineController {


    @FXML
    private Button addBtn;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<?> clinicTable;

    @FXML
    private TableColumn<Clinic, DatePicker> date;

    @FXML
    private Label listLbl;

    @FXML
    private TableColumn<Clinic, String> name;

    @FXML
    private TableColumn<Clinic, String> place;

    @FXML
    private Button returnBtn;

    private List<Clinic> clinics;
    private SimpleClient chatClient;

    @FXML
    void next_page(ActionEvent event) {

    }

    @FXML
    void returnToMenu(ActionEvent event) {

    }

    void initialize() throws IOException, InterruptedException {
        name.setCellValueFactory(new PropertyValueFactory<Clinic, String>("name"));
        place.setCellValueFactory(new PropertyValueFactory<Clinic, String>("location"));
        date.setCellValueFactory(new PropertyValueFactory<Clinic,DatePicker>("date"));


    }
    public void loadData() throws InterruptedException {
        chatClient.setGotList(false);

        try {
            chatClient.sendToServer("#ClinicList"); //ask from server the clinic list
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized(chatClient.getLock()) {
            while(!chatClient.getGotList()) {

                chatClient.getLock().wait();

            }
        }
        clinics = chatClient.getClinicList();


    }


    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}
