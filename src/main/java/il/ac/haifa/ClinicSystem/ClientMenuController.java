package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Patient;
import il.ac.haifa.ClinicSystem.entities.User;
import il.ac.haifa.ClinicSystem.entities.Vaccine_Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import javafx.scene.image.ImageView ;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ClientMenuController {

    private SimpleClient chatClient;
    private List<Vaccine_Appointment> appointments;
    private Alert alert = new Alert(Alert.AlertType.ERROR);

    @FXML
    private AnchorPane dwon;

    @FXML
    private Button exitBtn;

    @FXML
    private AnchorPane midlle;

    @FXML
    private Button greenBtn;

    @FXML
    private Button orderBtn;

    @FXML
    private ImageView picture;

    @FXML
    private Text txet;

    @FXML
    private AnchorPane up;

    @FXML
    private Button viewBtn;

    @FXML
    void exit(ActionEvent event) throws IOException {
        chatClient.closeConnection();
    }

    @FXML
    void showOrderMenu(ActionEvent event) throws IOException {
        App.setRoot("orderAppointmentMenu");
    }

    @FXML
    void showViewMenu(ActionEvent event) throws IOException {
        App.setRoot("appointmentsMenu");
    }

    @FXML
    void makeGreenPass(ActionEvent event) throws InterruptedException {
        User user = this.chatClient.getUser();
        if(((Patient)user).getGreen_pass() == null || LocalDate.now().isAfter(((Patient) user).getGreen_pass())){ //if we are after the exiration date or green pass is null, continue
            int user_id = user.getId();
            chatClient.setGotList(false);

            try {
                chatClient.sendToServer("#VaccineAppointments|"+user_id);
            } catch (IOException e) {
                e.printStackTrace();
            }

            synchronized(chatClient.getLock()) {
                while(!chatClient.getGotList()) {

                    chatClient.getLock().wait();

                }
            }

            appointments = chatClient.getVacList();
            if(appointments.size() >= 2){
                LocalDate expire = appointments.get(1).getDate().plusMonths(6);
                if(LocalDate.now().isAfter(appointments.get(1).getDate().plusWeeks(1))){ //a week has passed from the last vaccine
                    ((Patient)user).setGreen_pass(expire);

                    try {
                        chatClient.sendToServer(((Patient)user));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    chatClient.setUser(user); //instead of getting user from the server, I already made the changes needed here so i update user here
                }

            }
            else{
                alert.setContentText("Not Enough Vaccines Were Taken!! Need At Least 2!");
                alert.showAndWait();
                return;
            }
        }
        else{
            alert.setContentText("You Already Have a Green Pass!");
            alert.showAndWait();
        }
    }

    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

}
