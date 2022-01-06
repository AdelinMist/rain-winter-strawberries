package il.ac.haifa.ClinicSystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
	private static SimpleClient client;
    
    public App() {
	}
    

	public void closeConnection() {
		System.out.println("Connection closed.");
		System.exit(0);
	}

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("clientMenu"), 1214, 703);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));

        if(fxml.equals("clientMenu")) {

            ClientMenuController controller = new ClientMenuController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }


        if(fxml.equals("doctorList")) {

            DoctorListController controller = new DoctorListController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }

        if(fxml.equals("contentMenu")) {
        	ContentMenuController controller = new ContentMenuController();
	        controller.setClient(client);
	        fxmlLoader.setController(controller);
        }
        if(fxml.equals("clinicList")) {
        	ClinicListController controller = new ClinicListController();
	        controller.setClient(client);
	        fxmlLoader.setController(controller);
        }
        if(fxml.equals("covidServicesList")) {
            CovidServicesListController controller = new CovidServicesListController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }
        
        return fxmlLoader.load();
    }

    public static void main(SimpleClient c) {
    	client = c;
        launch();
    }

}