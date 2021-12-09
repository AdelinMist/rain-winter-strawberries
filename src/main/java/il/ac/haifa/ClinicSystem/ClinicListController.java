package il.ac.haifa.ClinicSystem;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import il.ac.haifa.ClinicSystem.entities.Clinic;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

/**
 * Sample Skeleton for 'deleteMovie.fxml' Controller Class
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ClinicListController {

	@FXML
	private Button addBtn;

	@FXML
	private BorderPane borderPane;

	@FXML
	private TableView<Clinic> clinicTable;

	@FXML
	private TableColumn<Clinic, String> closing;

	@FXML
	private TableColumn<Clinic, ChoiceBox<String>> day;

	@FXML
	private Label listLbl;

	@FXML
	private TableColumn<Clinic, String> place;

	@FXML
	private TableColumn<Clinic, String> name;

	@FXML
	private TableColumn<Clinic, String> opening;

	@FXML
	private Button returnBtn;

    private SimpleClient chatClient;
    private List<Clinic> clinics;
    private ObservableList<Clinic> cList = FXCollections.observableArrayList();

    @FXML
    void showChangeHours(ActionEvent event) throws InterruptedException, IOException {
    	/*Stage stage = new Stage();
    	Scene scene;
    	FXMLLoader fxmlLoader = new FXMLLoader(ClinicListController.class.getResource("addMovie.fxml"));
    	AddMovieController controller = new AddMovieController();
        controller.setClient(chatClient);
        fxmlLoader.setController(controller);
        scene = new Scene(fxmlLoader.load(), 1031, 419);
    	stage.setScene(scene);
    	chatClient.setGotList(false);
    	stage.showAndWait();
    	
	    //loadData();
    	synchronized(chatClient.getLock()) {
			 while(!chatClient.getGotList()) {
				
				 chatClient.getLock().wait();

			 }
		 }		
    	 movies = chatClient.getMovieList();       	
      	 for(Movie m : movies) {
      		 if(m.getCinema() == null) {
	      	 	 ObservableList<ScreeningCinema> data = FXCollections.observableArrayList();
	   	         data.addAll(m.getCinemas());
	   	         m.setCinema(new ChoiceBox<ScreeningCinema>(data));
      		 }
      	 }
		 
		 mList.removeAll(mList);
		 mList.addAll(movies);
		 movieTable.setItems(mList);*/
    }

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
    	App.setRoot("contentMenu");
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, InterruptedException {
    	chatClient.setGotList(false);

		 try {
			 chatClient.sendToServer("#ClinicList");
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
		 
		 synchronized(chatClient.getLock()) {
			 while(!chatClient.getGotList()) {
				
				 chatClient.getLock().wait();

			 }
		 }	
		 clinics = chatClient.getClinicList();
		 System.out.println(clinics);
		List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
		for(Clinic c : clinics) {
       	 	 ObservableList<String> data = FXCollections.observableArrayList();
    	     data.addAll(days);
    	     c.setDayOfWeek(new ChoiceBox<String>(data));
    	     c.getDayOfWeek().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				 if (newSelection != null) {
					 switch (newSelection) {
						 case "Sunday":
							 c.setCurOpenHour(c.getOpenHours().get(0).toString());
							 c.setCurCloseHour(c.getCloseHours().get(0).toString());
							 break;
						 case "Monday":
							 c.setCurOpenHour(c.getOpenHours().get(1).toString());
							 c.setCurCloseHour(c.getCloseHours().get(1).toString());
							 break;
						 case "Tuesday":
							 c.setCurOpenHour(c.getOpenHours().get(2).toString());
							 c.setCurCloseHour(c.getCloseHours().get(2).toString());
							 break;
						 case "Wednesday":
							 c.setCurOpenHour(c.getOpenHours().get(3).toString());
							 c.setCurCloseHour(c.getCloseHours().get(3).toString());
							 break;
						 case "Thursday":
							 c.setCurOpenHour(c.getOpenHours().get(4).toString());
							 c.setCurCloseHour(c.getCloseHours().get(4).toString());
							 break;
						 case "Friday":
							 c.setCurOpenHour(c.getOpenHours().get(5).toString());
							 c.setCurCloseHour(c.getCloseHours().get(5).toString());
							 break;
						 default:

					 }
				 }
			 });
    	     c.setCurOpenHourProperty(new SimpleStringProperty());
			 c.setCurCloseHourProperty(new SimpleStringProperty());
		}

   		name.setCellValueFactory(new PropertyValueFactory<Clinic, String>("name"));
   		place.setCellValueFactory(new PropertyValueFactory<Clinic, String>("location"));
   		day.setCellValueFactory(new PropertyValueFactory<Clinic, ChoiceBox<String>>("dayOfWeek"));
   		opening.setCellValueFactory(new PropertyValueFactory<Clinic, String>("curOpenHour"));
   		closing.setCellValueFactory(new PropertyValueFactory<Clinic, String>("curCloseHour"));



		cList.removeAll(cList);
   	    cList.addAll(clinics);
	    clinicTable.setItems(cList);
    }
  
    public void loadData() throws InterruptedException {
    	chatClient.setGotList(false);

		 try {
			 chatClient.sendToServer("#MovieList");
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
		 
		 synchronized(chatClient.getLock()) {
			 while(!chatClient.getGotList()) {
				
				 chatClient.getLock().wait();

			 }
		 }	
		 clinics = chatClient.getClinicList();
		 
		 cList.removeAll(cList);
		 cList.addAll(cList);
		 clinicTable.setItems(cList);

    }
   
    public void setClient(SimpleClient c) {
   	 this.chatClient = c;
    } 
    

}


