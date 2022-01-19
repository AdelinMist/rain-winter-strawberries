package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Doctor;
import il.ac.haifa.ClinicSystem.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class LoginController {

    //flag is 0 when username or password are incorrect and 1 otherwise
    int flag=0;
    private List<User> users;
    private SimpleClient chatClient;

    @FXML
    private Button ExitButton;

    @FXML
    private Button LoginButton;

    @FXML
    private PasswordField PassButton;

    @FXML
    private TextField userButton;

    @FXML
    void CheckLogin(ActionEvent event) throws IOException{
        LoginController c =new LoginController();
        c.setClient(chatClient);
        String username= userButton.getText();
        String password= PassButton.getText();

        chatClient.setGotList(false);
        try {
            chatClient.sendToServer("#LOGIN");
        } catch (IOException e) {
            e.printStackTrace();
        }

        synchronized(chatClient.getLock()) {
            while(!chatClient.getGotList()) {

                try {
                    chatClient.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        users = chatClient.getUserList();
        for(User user:users){
            if(user.getUsername().equals(username)){

                SecureRandom random = new SecureRandom();
                byte[] salt = new byte[16];
                random.nextBytes(salt);
                //this.salt = salt;

                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA-512");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                md.update(user.getSalt());

                byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

                if(Arrays.equals(hashedPassword, user.getPassword())){
                    flag=1;
                    if(user instanceof Doctor){
                        App.setRoot("doctorScreen");
                    }
                    else{
                        App.setRoot("clientMenu");
                    }

                    chatClient.setUser(user);
                    App.setRoot("clientMenu");

                }

            }
        }
        if (flag==0){
            //should get here only if user/password is incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login denied");
            alert.setHeaderText("Username or password is not correct");
            alert.setContentText("Please try again");
            alert.showAndWait();
        }



    }

    @FXML
    void CloseThePage(ActionEvent event) {
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }

    public void setClient(SimpleClient client) {
        this.chatClient = client;
    }
}