package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.*;
import il.ac.haifa.ClinicSystem.entities.DoctorClinic;
import il.ac.haifa.ClinicSystem.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class PrimaryController {

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
        PrimaryController c =new PrimaryController();
        c.setClient(chatClient);
        String usermane= userButton.getText();
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
            if(user.getUsername().equals(usermane)){

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

                    /*if(user instanceof Doctor){
                        App.setRoot("DoctorClinic");
                    }
                    else{
                        App.setRoot("clientMenu");
                    }*/

                    App.setRoot("clientMenu");
                    chatClient.setUser(user);
                }


            }
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