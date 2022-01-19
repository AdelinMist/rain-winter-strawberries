package il.ac.haifa.ClinicSystem;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import il.ac.haifa.ClinicSystem.entities.*;
import il.ac.haifa.ClinicSystem.ocsf.client.AbstractClient;

public class SimpleClient extends AbstractClient{
	private static final Logger LOGGER =
			Logger.getLogger(SimpleClient.class.getName());
    
    private final Object lock = new Object();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private  User user;
    
    private List<?> curList;
    private Clinic curClinic;
    private boolean gotList = false;
	private boolean gotClinic = false;
	private Thread loopThread;
	private String userType;
	public SimpleClient(String host, int port, String userType) {
		super(host, port);
		this.userType=userType;
	}
	
	public SimpleClient() {
		super("127.0.0.1", 50001);
	}
	
	
	@Override
	protected void connectionEstablished() {
		// TODO Auto-generated method stub
		super.connectionEstablished();
		LOGGER.info("Connected to server.");
		SimpleClient chatClient = this;
		if(this.userType.equals("user")) {
			loopThread = new Thread(new Runnable() {
				@Override
				public void run() {
					App.main(chatClient);
				}
			});
		}
		
		loopThread.start();  
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromServer(Object msg) {
		if(msg instanceof List<?>) {
			curList = (List<?>)msg;
			gotList = true;
			synchronized (lock) {
				lock.notifyAll();
			}
		}
		else if(msg instanceof Clinic){
			curClinic = (Clinic)msg;
			gotClinic = true;
			synchronized (lock) {
				lock.notifyAll();
			}
		}
	}
	
	@Override
	protected void connectionClosed() {
		// TODO Auto-generated method stub
		super.connectionClosed();
		System.out.println("Connection closed.");
		System.exit(0);
	}

	public boolean getGotClinic() {
		return gotClinic;
	}

	public void setGotClinic(boolean gotClinic) {
		this.gotClinic = gotClinic;
	}

	public void setGotList(boolean g) {
		 this.gotList=g;
	 }
	 
	 public boolean getGotList() {
		 return gotList;
	 }
	 
	 public Object getLock() {
		 return lock;
	 }
	 
	 public List<Clinic> getClinicList(){
		 return (List<Clinic>)curList;
	 }

	 public List<User> getUserList(){
		return (List<User>)curList;
	}

	public List<WeeklyClinicReport> getWeeklyClinicReportList(){
		return (List<WeeklyClinicReport>)curList;
	}

	 public List<Vaccine_Appointment> getVacList(){
		return (List<Vaccine_Appointment>)curList;
	}
	 public List<DoctorClinic> getDoctorClinicList(){
		return (List<DoctorClinic>)curList;
	}
	//public List<VaccineClinic> getVaccineClinicList(){return (List<VaccineClinic>)curList; }
	public List<Corna_cheak_Appointment> getCovidTestAppList(){
		return (List<Corna_cheak_Appointment>)curList;
	}

	public Clinic getClinic() {
		return curClinic;
	}

	public static void main(String[] args) throws IOException {
			
		
	}
}
