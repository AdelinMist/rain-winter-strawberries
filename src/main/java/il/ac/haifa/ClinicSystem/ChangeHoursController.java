package il.ac.haifa.ClinicSystem;


import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import il.ac.haifa.ClinicSystem.entities.Clinic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ChangeHoursController {

    @FXML
    private TextField closeTime;

    @FXML
    private Button commitBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private TextField openTime;

	@FXML
	private ChoiceBox<String> dayChoice;


    private SimpleClient chatClient;
    private String chosenDay;
    private Clinic newClinic;
    private Clinic curClinic;
    private boolean isValid = false;
    private LocalTime nTime = null;
    private Alert alert = new Alert(AlertType.ERROR);
    private Alert confirmAlert = new Alert(AlertType.CONFIRMATION);

    @FXML
    void commitChanges(ActionEvent event) throws IOException {
    	    	confirmAlert.setContentText("Are you sure you wish to change this clinic entity?");
    	    	confirmAlert.showAndWait().ifPresent(response->{
    	    		if(response == ButtonType.CANCEL) {
    	    			return;
    	    		}
    	    	});
    	    	newClinic =  new Clinic(curClinic);
    	    	//if the user has chosen the desired time change and a day of the week we change the opening and closing hours as needed
				if (dayChoice.getSelectionModel().getSelectedItem() != null && !openTime.getText().isEmpty()) {
					switch (dayChoice.getSelectionModel().getSelectedItem()) {
						case "Sunday":
							newClinic.getOpenHours().set(0, LocalTime.parse(openTime.getText()));
							break;
						case "Monday":
							newClinic.getOpenHours().set(1, LocalTime.parse(openTime.getText()));
							break;
						case "Tuesday":
							newClinic.getOpenHours().set(2, LocalTime.parse(openTime.getText()));
							break;
						case "Wednesday":
							newClinic.getOpenHours().set(3, LocalTime.parse(openTime.getText()));
							break;
						case "Thursday":
							newClinic.getOpenHours().set(4, LocalTime.parse(openTime.getText()));
							break;
						case "Friday":
							newClinic.getOpenHours().set(5, LocalTime.parse(openTime.getText()));
							break;
						default:

					}
				}
				if (dayChoice.getSelectionModel().getSelectedItem() != null && !closeTime.getText().isEmpty()) {
					switch (dayChoice.getSelectionModel().getSelectedItem()) {
						case "Sunday":
							newClinic.getCloseHours().set(0, LocalTime.parse(closeTime.getText()));
							break;
						case "Monday":
							newClinic.getCloseHours().set(1, LocalTime.parse(closeTime.getText()));
							break;
						case "Tuesday":
							newClinic.getCloseHours().set(2, LocalTime.parse(closeTime.getText()));
							break;
						case "Wednesday":
							newClinic.getCloseHours().set(3, LocalTime.parse(closeTime.getText()));
							break;
						case "Thursday":
							newClinic.getCloseHours().set(4, LocalTime.parse(closeTime.getText()));
							break;
						case "Friday":
							newClinic.getCloseHours().set(5, LocalTime.parse(closeTime.getText()));
							break;
						default:

					}
				}
				chatClient.sendToServer(newClinic);
				curClinic = newClinic;

    }
    
    @FXML
    void exitFromChangeHours(ActionEvent event) throws IOException {
    	Node n = (Node)event.getSource();
    	((Stage)n.getScene().getWindow()).close();
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws InterruptedException {
		List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
		ObservableList<String> data = FXCollections.observableArrayList();
		data.addAll(days);
		dayChoice.setItems(data);
		if(chosenDay != null){
			dayChoice.setValue(chosenDay);
		}

		dayChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				switch (newSelection) {
					case "Sunday":
						openTime.setText(curClinic.getOpenHours().get(0).toString());
						closeTime.setText(curClinic.getCloseHours().get(0).toString());
						break;
					case "Monday":
						openTime.setText(curClinic.getOpenHours().get(1).toString());
						closeTime.setText(curClinic.getCloseHours().get(1).toString());
						break;
					case "Tuesday":
						openTime.setText(curClinic.getOpenHours().get(2).toString());
						closeTime.setText(curClinic.getCloseHours().get(2).toString());
						break;
					case "Wednesday":
						openTime.setText(curClinic.getOpenHours().get(3).toString());
						closeTime.setText(curClinic.getCloseHours().get(3).toString());
						break;
					case "Thursday":
						openTime.setText(curClinic.getOpenHours().get(4).toString());
						closeTime.setText(curClinic.getCloseHours().get(4).toString());
						break;
					case "Friday":
						openTime.setText(curClinic.getOpenHours().get(5).toString());
						closeTime.setText(curClinic.getCloseHours().get(5).toString());
						break;
					default:

				}
			}
		});
    }

    public void setParams(SimpleClient sc, Clinic c, String chosenDay) {
		this.chatClient = sc;
    	this.curClinic = c;
    	this.chosenDay = chosenDay;
    }

}
