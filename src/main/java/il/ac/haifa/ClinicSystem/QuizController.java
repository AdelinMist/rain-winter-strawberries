package il.ac.haifa.ClinicSystem;


import il.ac.haifa.ClinicSystem.entities.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class QuizController {


    @FXML
    private Button next;

    @FXML
    private TextField set1;

    @FXML
    private TextField set2;

    @FXML
    private Text text1;

    @FXML
    private Text text2;

    @FXML
    private TitledPane title;
    private SimpleClient chatClient;
    public void setClient(SimpleClient c) {
        this.chatClient = c;
    }

    @FXML
    void gonext(ActionEvent event) throws IOException {
        String answer1 = text1.getText();
        String answer2 = text2.getText();
        Quiz quiz= new Quiz(answer1,answer2);
        App.setRoot("coronaTestAppointment");
    }

}

