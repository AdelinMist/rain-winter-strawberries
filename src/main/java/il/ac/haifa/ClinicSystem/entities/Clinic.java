package il.ac.haifa.ClinicSystem.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clinics")
public class Clinic implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "clinic_id")
	private int id;

	@Column(name = "clinic_name")
	private String name;

	@Column(name = "location")
	private String location;

	@OneToOne(mappedBy = "clinic")
	private ClinicManager clinicManager;

	@Column(name = "openHours")
	@LazyCollection(LazyCollectionOption.FALSE)
	@ElementCollection(targetClass = LocalTime.class)
	private List<LocalTime> openHours;

	@Column(name = "closeHours")
	@LazyCollection(LazyCollectionOption.FALSE)
	@ElementCollection(targetClass = LocalTime.class)
	private List<LocalTime> closeHours;

	@Column(name = "covidTestOpenHours")
	@LazyCollection(LazyCollectionOption.FALSE)
	@ElementCollection(targetClass = LocalTime.class)
	private List<LocalTime> covidTestOpenHours;

	@Column(name = "covidTestCloseHours")
	@LazyCollection(LazyCollectionOption.FALSE)
	@ElementCollection(targetClass = LocalTime.class)
	private List<LocalTime> covidTestCloseHours;

	@Column(name = "covidVaccOpenHours")
	@LazyCollection(LazyCollectionOption.FALSE)
	@ElementCollection(targetClass = LocalTime.class)
	private List<LocalTime> covidVaccOpenHours;

	@Column(name = "covidVaccCloseHours")
	@LazyCollection(LazyCollectionOption.FALSE)
	@ElementCollection(targetClass = LocalTime.class)
	private List<LocalTime> covidVaccCloseHours;

	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Vaccine_Appointment> vaccine_appointments1;

	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Corna_cheak_Appointment> Corna_cheak_Appointments1;


	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Sister_Appointment> sister_appointments1;

	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<labAppointment> lab_appointments1;

	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ProDoctorAppointment> pro_appointments1;

	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<FamilyDoctorAppointment> family_appointments1;

	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<WeeklyClinicReport> weeklyClinicReportList;
	@OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<FamilyDoctorAppointment> familyDoctorAppointments;

	@OneToOne
	private Secretary secretary;

	//private  transient  ChoiceBox<String> family ;


	public void add_pro_Appointment(ProDoctorAppointment appointment) {
		this.pro_appointments1.add(appointment);

	}

	public List<ProDoctorAppointment> getProAppointments() {
		return pro_appointments1;
	}



	public void add_lab_Appointment(labAppointment appointment) {
		this.lab_appointments1.add(appointment);

	}



		@OneToMany(mappedBy = "clinic")
		@LazyCollection(LazyCollectionOption.FALSE)
		private List<DoctorClinic> doctorClinics = null;

		private transient DatePicker dayPicker;
		private transient ChoiceBox<String> timeOptions; // work like "12:10"

		private transient ChoiceBox<String> family; // saved as "hh:mm", for example "12:10"
		public void setFamily (ChoiceBox < String > fiamly) {
			this.family = fiamly;
		}
		public ChoiceBox<String> getFamily () {
			return this.family;
		}


		private boolean hasCovidTests;
		private boolean hasCovidVaccinations;

		private transient StringProperty curOpenHour;
		private transient StringProperty curCloseHour;
		private transient StringProperty curCovidTestOpenHour;
		private transient StringProperty curCovidTestCloseHour;
		private transient StringProperty curCovidVaccOpenHour;
		private transient StringProperty curCovidVaccCloseHour;
		//private transient LocalTime curOpenHour;
		//private transient LocalTime curCloseHour;
		private transient ChoiceBox<String> dayOfWeek;
		private transient ChoiceBox<String> weekofvacciene; //Eliron added that

		//regular constructor
	public Clinic(String name, String location, List < LocalTime > openHours, List < LocalTime > closeHours,
				List < LocalTime > covidTestOpenHours, List < LocalTime > covidTestCloseHours, List < LocalTime > covidVaccOpenHours, List < LocalTime > covidVaccCloseHours,
		boolean test, boolean vac){
			this.name = name;
			this.location = location;
			this.openHours = openHours;
			this.closeHours = closeHours;
			this.covidTestCloseHours = covidTestCloseHours;
			this.covidTestOpenHours = covidTestOpenHours;
			this.covidVaccOpenHours = covidVaccOpenHours;
			this.covidVaccCloseHours = covidVaccCloseHours;
			this.hasCovidTests = test;
			this.hasCovidVaccinations = vac;
		}

	public Clinic() {
		}

		//deep copy constructor
	 public Clinic(Clinic m) {
			this.name = m.getName();
			this.location = m.getLocation();

			this.openHours = (new ArrayList<LocalTime>());
			for (LocalTime t : m.getOpenHours()) {
				this.openHours.add(t);
			}
			this.closeHours = (new ArrayList<LocalTime>());
			for (LocalTime t : m.getCloseHours()) {
				this.closeHours.add(t);
			}

			this.covidTestOpenHours = (new ArrayList<LocalTime>());
			for (LocalTime t : m.getCovidTestOpenHours()) {
				this.covidTestOpenHours.add(t);
			}
			this.covidTestCloseHours = (new ArrayList<LocalTime>());
			for (LocalTime t : m.getCovidTestCloseHours()) {
				this.covidTestCloseHours.add(t);
			}

			this.covidVaccOpenHours = (new ArrayList<LocalTime>());
			for (LocalTime t : m.getCovidVaccOpenHours()) {
				this.covidVaccOpenHours.add(t);
			}
			this.covidVaccCloseHours = (new ArrayList<LocalTime>());
			for (LocalTime t : m.getCovidVaccCloseHours()) {
				this.covidVaccCloseHours.add(t);
			}

			this.doctorClinics = new ArrayList<DoctorClinic>();
			for (DoctorClinic dc : m.getDoctorClinics()) {
				this.doctorClinics.add(dc);
			}

			this.clinicManager = m.getClinicManager();

			this.id = m.getId();
			this.hasCovidVaccinations = m.getHasCovidVaccinations();
			this.hasCovidTests = m.getHasCovidTests();
		}

		public List<FamilyDoctorAppointment> getFamily_appointments1 () {
			return family_appointments1;
		}

		public void setFamily_appointments1 (List < FamilyDoctorAppointment > family_appointments1) {
			this.family_appointments1 = family_appointments1;
		}

		@Override
		public String toString () {
			return String.format(name + " | " + location + "\n");
		}

		public void add_Sister_Appointment (Sister_Appointment appointment){
			this.sister_appointments1.add(appointment);

		}
		public void add_Family_Appointment (FamilyDoctorAppointment appointment){
			this.family_appointments1.add(appointment);

		}

		public void add_vaccine_appointment (Vaccine_Appointment vaccine_appointment){
			this.vaccine_appointments1.add(vaccine_appointment);
			System.out.println(vaccine_appointment.getTime() + "insert to the list!");

		}

		public void add_coronaTest_appointment (Corna_cheak_Appointment corna_cheak_appointment){
			this.Corna_cheak_Appointments1.add(corna_cheak_appointment);

		}

		public List<WeeklyClinicReport> getWeeklyClinicReportList () {
			return weeklyClinicReportList;
		}

		public void setWeeklyClinicReportList (List < WeeklyClinicReport > weeklyClinicReportList) {
			this.weeklyClinicReportList = weeklyClinicReportList;
		}

		public int getId () {
			return this.id;
		}

		public List<DoctorClinic> getDoctorClinics () {
			return doctorClinics;
		}


		public void setDoctorClinics (List < DoctorClinic > doctorClinics) {
			this.doctorClinics = doctorClinics;
		}


		public boolean getHasCovidTests () {
			return hasCovidTests;
		}

		public void setHasCovidTests ( boolean hasCovidTests){
			this.hasCovidTests = hasCovidTests;
		}

		public boolean getHasCovidVaccinations () {
			return hasCovidVaccinations;
		}

		public void setHasCovidVaccinations ( boolean hasCovidVaccinations){
			this.hasCovidVaccinations = hasCovidVaccinations;
		}

		public List<LocalTime> getCovidTestOpenHours () {
			return covidTestOpenHours;
		}

		public void setCovidTestOpenHours (List < LocalTime > covidTestOpenHours) {
			this.covidTestOpenHours = covidTestOpenHours;
		}

		public List<LocalTime> getCovidTestCloseHours () {
			return covidTestCloseHours;
		}

		public void setCovidTestCloseHours (List < LocalTime > covidTestCloseHours) {
			this.covidTestCloseHours = covidTestCloseHours;
		}

		public List<LocalTime> getCovidVaccOpenHours () {
			return covidVaccOpenHours;
		}

		public void setCovidVaccOpenHours (List < LocalTime > covidVaccOpenHours) {
			this.covidVaccOpenHours = covidVaccOpenHours;
		}

		public List<LocalTime> getCovidVaccCloseHours () {
			return covidVaccCloseHours;
		}

		public void setCovidVaccCloseHours (List < LocalTime > covidVaccCloseHours) {
			this.covidVaccCloseHours = covidVaccCloseHours;
		}

		public StringProperty curCovidTestOpenHourProperty () {
			return curCovidTestOpenHour;
		}
		public void setCurCovidTestOpenHourProperty (SimpleStringProperty s){
			this.curCovidTestOpenHour = s;
		}
		public String getCurCovidTestOpenHour () {
			return curCovidTestOpenHour.get();
		}
		public void setCurCovidTestOpenHour (String name){
			curCovidTestOpenHour.set(name);
		}

		public StringProperty curCovidTestCloseHourProperty () {
			return curCovidTestCloseHour;
		}
		public void setCurCovidTestCloseHourProperty (SimpleStringProperty s){
			this.curCovidTestCloseHour = s;
		}
		public String getCurCovidTestCloseHour () {
			return curCovidTestCloseHour.get();
		}
		public void setCurCovidTestCloseHour (String name){
			curCovidTestCloseHour.set(name);
		}

		public StringProperty curCovidVaccOpenHourProperty () {
			return curCovidVaccOpenHour;
		}
		public void setCurCovidVaccOpenHourProperty (SimpleStringProperty s){
			this.curCovidVaccOpenHour = s;
		}
		public String getCurCovidVaccOpenHour () {
			return curCovidVaccOpenHour.get();
		}
		public void setCurCovidVaccOpenHour (String name){
			curCovidVaccOpenHour.set(name);
		}

		public StringProperty curCovidVaccCloseHourProperty () {
			return curCovidVaccCloseHour;
		}
		public void setCurCovidVaccCloseHourProperty (SimpleStringProperty s){
			this.curCovidVaccCloseHour = s;
		}
		public String getCurCovidVaccCloseHour () {
			return curCovidVaccCloseHour.get();
		}
		public void setCurCovidVaccCloseHour (String name){
			curCovidVaccCloseHour.set(name);
		}

		public StringProperty curOpenHourProperty () {
			return curOpenHour;
		}
		public void setCurOpenHourProperty (SimpleStringProperty s){
			this.curOpenHour = s;
		}
		public String getCurOpenHour () {
			return curOpenHour.get();
		}
		public void setCurOpenHour (String name){
			curOpenHour.set(name);
		}

		public StringProperty curCloseHourProperty () {
			return curCloseHour;
		}
		public void setCurCloseHourProperty (SimpleStringProperty s){
			this.curCloseHour = s;
		}
		public String getCurCloseHour () {
			return curCloseHour.get();
		}
		public void setCurCloseHour (String name){
			curCloseHour.set(name);
		}

		public List<LocalTime> getCloseHours () {
			return closeHours;
		}

		public void setCloseHours (List < LocalTime > closeHours) {
			this.closeHours = closeHours;
		}

		public String getName () {
			return name;
		}

		public void setName (String name){
			this.name = name;
		}

		public String getLocation () {
			return location;
		}

		public void setLocation (String location){
			this.location = location;
		}

		public List<LocalTime> getOpenHours () {
			return openHours;
		}

		public void setOpenHours (List < LocalTime > openHours) {
			this.openHours = openHours;
		}

		public ChoiceBox<String> getDayOfWeek () {
			return dayOfWeek;
		}

		public void setDayOfWeek (ChoiceBox < String > dayOfWeek) {
			this.dayOfWeek = dayOfWeek;
		}

		@Override
		public boolean equals (Object obj){
			if (obj != null) {
				if (this.id == ((Clinic) obj).getId()) {
					return true;
				}
			}
			return false;
		}

		public ChoiceBox<String> getWeekofvacciene () {
			return weekofvacciene;
		}

		public void setWeekofvacciene (ChoiceBox < String > weekofvacciene) {
			this.weekofvacciene = weekofvacciene;
		}

		public void setVaccine_appointments (List < Vaccine_Appointment > vaccine_appointments) {
			this.vaccine_appointments1 = vaccine_appointments;
		}
		public List<Vaccine_Appointment> getVaccine_appointments () {
			return this.vaccine_appointments1;
		}

		public DatePicker getDayPicker () {
			return dayPicker;
		}

		public void setDayPicker (DatePicker next_vaccine_appointments1){
			this.dayPicker = next_vaccine_appointments1;
		}

		public ChoiceBox<String> getTimeOptions () {
			return timeOptions;
		}

		public void setTimeOptions (ChoiceBox < String > timeOptions) {
			this.timeOptions = timeOptions;
		}
		public void setCorna_cheak_Appointments1 (List < Corna_cheak_Appointment > corna_cheak_Appointments1) {
			Corna_cheak_Appointments1 = corna_cheak_Appointments1;
		}
		public List<Corna_cheak_Appointment> getCorna_cheak_Appointments1 () {
			return Corna_cheak_Appointments1;
		}

		public void setSister_appointments1 (List < Sister_Appointment > sister_appointments1) {
			this.sister_appointments1 = sister_appointments1;
		}

		public List<Sister_Appointment> getSister_appointments1 () {
			return this.sister_appointments1;
		}

		public List<labAppointment> getLab_appointments1 () {
			return lab_appointments1;
		}

		public void setLab_appointments1 (List < labAppointment > lab_appointments1) {
			this.lab_appointments1 = lab_appointments1;
		}

		public ClinicManager getClinicManager () {
			return clinicManager;
		}

		public void setClinicManager(ClinicManager clinicManager){
			this.clinicManager = clinicManager;
		}
	}

