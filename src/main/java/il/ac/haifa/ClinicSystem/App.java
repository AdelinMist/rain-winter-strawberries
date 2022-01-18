package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.Quiz;
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
        scene = new Scene(loadFXML("Login"), 1214, 703);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        if(fxml.equals("clinics_of_doctor_type")) {
            clinics_of_doctor_typeController controller = new clinics_of_doctor_typeController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }
        if(fxml.equals("doctorAppointment")) {
            DoctorAppointmentController controller = new DoctorAppointmentController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }
        if(fxml.equals("clientMenu")) {
            ClientMenuController controller = new ClientMenuController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }
        else if(fxml.equals("SisterAppointment")) {
            SisterAppointmentController controller = new SisterAppointmentController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }
        else if(fxml.equals("FamilyAppointment")) {
            FamilyAppointmentController controller = new FamilyAppointmentController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }
        else if(fxml.equals("Login")) {
            LoginController controller = new LoginController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }
        else if(fxml.equals("coronaTestAppointment")) {
            CoronaTestAppointmentController controller = new CoronaTestAppointmentController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }
        else if(fxml.equals("quiz_corona_test")) {
            QuizController controller = new QuizController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }

        else if(fxml.equals("ClinicVaccine")) {
            VaccineClinicController controller = new VaccineClinicController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }
        else if(fxml.equals("doctorClinicList")) {
            DoctorClinicListController controller = new DoctorClinicListController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }

        else if(fxml.equals("doctorList")) {
            DoctorListController controller = new DoctorListController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }

        else if(fxml.equals("orderAppointmentMenu")) {
            OrderAppoinmentMenuController controller = new OrderAppoinmentMenuController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }

        else if(fxml.equals("contentMenu")) {
            ContentMenuController controller = new ContentMenuController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }
        else if(fxml.equals("clinicList")) {
            ClinicListController controller = new ClinicListController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }
        else if(fxml.equals("covidServicesList")) {
            CovidServicesListController controller = new CovidServicesListController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }
        else if(fxml.equals("appointmentsMenu")) {
            AppointmentsMenuController controller = new AppointmentsMenuController();
            controller.setClient(client);
            fxmlLoader.setController(controller);
        }

        return fxmlLoader.load();
    }
    public static void setRoot1(String fxml, Quiz quiz) throws IOException { // for quiz
        scene.setRoot(loadFXML1(fxml , quiz));
    }

    private static Parent loadFXML1(String fxml , Quiz quiz) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));

        if(fxml.equals("coronaTestAppointment")) {
            CoronaTestAppointmentController controller = new CoronaTestAppointmentController();
            controller.setClient(client);
            controller.setQuiz(quiz);
            fxmlLoader.setController(controller);
        }
        return fxmlLoader.load();
    }

    public static void main(SimpleClient c) {
    	client = c;
        launch();
    }

}