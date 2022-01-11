package il.ac.haifa.ClinicSystem;


import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.TextField;
        import javafx.scene.control.TitledPane;
        import javafx.scene.text.Text;

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
    void gonext(ActionEvent event) {

    }

}

