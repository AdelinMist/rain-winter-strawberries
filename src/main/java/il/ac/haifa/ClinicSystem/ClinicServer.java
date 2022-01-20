package il.ac.haifa.ClinicSystem;

import il.ac.haifa.ClinicSystem.entities.*;
import il.ac.haifa.ClinicSystem.ocsf.server.AbstractServer;
import il.ac.haifa.ClinicSystem.ocsf.server.ConnectionToClient;
import javafx.util.Pair;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;


public class ClinicServer extends AbstractServer{

	private static Session session;
	private static SessionFactory sessionFactory;
	private static Map<Integer, Integer> daily_sum_wait_days= new HashMap<>(), daily_num_of_appointments= new HashMap<>();
	private static Map<Integer, Integer> clinicReportMap= new HashMap<>(); //mapping of clinic to current week report id


	private static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();

		// Add ALL of your entities here. You can also try adding a whole package.
		configuration.addAnnotatedClass(Clinic.class);
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Patient.class);
		configuration.addAnnotatedClass(DoctorClinic.class);
		configuration.addAnnotatedClass(Doctor.class);
		configuration.addAnnotatedClass(Vaccine_Appointment.class);
		configuration.addAnnotatedClass(labAppointment.class);
		configuration.addAnnotatedClass(FamilyDoctorAppointment.class);
		configuration.addAnnotatedClass(ProDoctorAppointment.class);
		configuration.addAnnotatedClass(Corna_cheak_Appointment.class);
		configuration.addAnnotatedClass(Quiz.class);
		configuration.addAnnotatedClass(Sister_Appointment.class);
		configuration.addAnnotatedClass(Secretary.class);

		configuration.addAnnotatedClass(ClinicManager.class);
		configuration.addAnnotatedClass(SystemManager.class);
		configuration.addAnnotatedClass(WeeklyClinicReport.class);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();

		return configuration.buildSessionFactory(serviceRegistry);
	}

	/**
	 * Generating the initial state of the database
	 * creating the Clinics, Doctors and their relationships
	 * @see Clinic
	 * @see Doctor
	 * @throws Exception
	 */
	private static void generateClinics() throws Exception {
		List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
		List<LocalTime> open = new ArrayList<>(), close = new ArrayList<>(), testopen = new ArrayList<>(), testclose = new ArrayList<>(), vaccopen = new ArrayList<>(), vaccclose = new ArrayList<>();
		HashMap<String, Pair<LocalTime,LocalTime>> workingHours = new HashMap<>();
		for(String d : days) {
			open.add(LocalTime.of(8,0));
			close.add(LocalTime.of(22,0));
			testopen.add(LocalTime.of(8,0));
			testclose.add(LocalTime.of(12,0));
			vaccopen.add(LocalTime.of(8,0));
			vaccclose.add(LocalTime.of(12,0));

			if(d == "Monday"){
				workingHours.put(d, null);
			}
			else{
				workingHours.put(d, new Pair<>(LocalTime.of(10,0), LocalTime.of(16,0)));
			}
		}
		Clinic c2 = new Clinic("Meuheded", "Jerusalem", open, close, testopen, testclose, vaccopen, vaccclose, true, true);
		Clinic c = new Clinic("Clalit", "Haifa", open, close, testopen, testclose, vaccopen, vaccclose, true, true);
		//session.saveOrUpdate(temp);
		//adding doctors like this is a security risk but for projects sake it's ok
		Doctor d = new Doctor("Matt123", "123456789", "Matt Matthews", "Neurology","Matt@gmail.com");
		Doctor d1 = new Doctor("Israel", "987654321", "Israel Israeli", "ENT","Israel@gmail.com");
		// I didn't have the heart to change coolDoctor420
		Doctor d2 = new Doctor("coolDoctor420", "password", "Jane Cohen", "Psychiatrist","Jane@gmail.com");
		//!!!!!!!!!!!!!!!!! we should probably add a doctor user with one of our presenters email
		Patient u = new Patient("daniel","123","tkhruirjhnh@gmail.com","Clalit", "Daniel D");

		Secretary s = new Secretary("lina", "123","daniel@gmail.com", "Clalit", "lina", c );

		DoctorClinic dc = new DoctorClinic(c, d, workingHours);
		DoctorClinic dc1 = new DoctorClinic(c, d1, workingHours);
		DoctorClinic dc2 = new DoctorClinic(c2, d1, workingHours);
		DoctorClinic dc3 = new DoctorClinic(c2, d2, workingHours);


		List<DoctorClinic> dcList_forc = new ArrayList<DoctorClinic>();
		List<DoctorClinic> dcList_forc2 = new ArrayList<DoctorClinic>();
		List<DoctorClinic> dcList_ford = new ArrayList<DoctorClinic>();
		List<DoctorClinic> dcList_ford1 = new ArrayList<DoctorClinic>();
		List<DoctorClinic> dcList_ford2 = new ArrayList<DoctorClinic>();


		dcList_forc.add(dc);
		dcList_forc.add(dc1);
		dcList_forc2.add(dc2);
		dcList_forc2.add(dc3);
		dcList_ford.add(dc);
		dcList_ford1.add(dc1);
		dcList_ford1.add(dc2);
		dcList_ford2.add(dc3);

		c.setDoctorClinics(dcList_forc);
		c2.setDoctorClinics(dcList_forc2);
		d.setDoctorClinics(dcList_ford);
		d1.setDoctorClinics(dcList_ford1);
		d2.setDoctorClinics(dcList_ford2);


		SystemManager sm = SystemManager.getInstance("manager", "aSecret", "Min Farshaw", "manager@gmail.com");
		ClinicManager cm1 = new ClinicManager("clinic1", "first", "Adam Bashere", "clinic1@gmail.com", c),
		cm2 = new ClinicManager("clinic2", "second", "Ilan Ituralde", "clinic2@gmail.com", c2);


		session.saveOrUpdate(u);
		session.saveOrUpdate(c);
		session.saveOrUpdate(c2);
		session.saveOrUpdate(d);
		session.saveOrUpdate(d1);
		session.saveOrUpdate(d2);
		session.saveOrUpdate(dc);
		session.saveOrUpdate(cm1);
		session.saveOrUpdate(cm2);
		session.saveOrUpdate(sm);
		session.saveOrUpdate(dc1);
		session.saveOrUpdate(dc2);
		session.saveOrUpdate(dc3);
		session.saveOrUpdate(s);

		 /*
		 * The call to session.flush() updates the DB immediately without ending the transaction.
		 * Recommended to do after an arbitrary unit of work.
		 * MANDATORY to do if you are saving a large amount of data - otherwise you may get
		cache errors.
		 */
		session.flush();
	}

	//this function gives us all instances of a certain class, useful when we need all objects of some entity type
	private static <T> List<T> getAll(Class<T> object) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
		Root<T> rootEntry = criteriaQuery.from(object);
		CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);

		TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
		return allQuery.getResultList();
	}

	public ClinicServer(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		//handling massages from client that ask to add objects to the DB
		if(msg instanceof Clinic)  {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				session.saveOrUpdate((Clinic)msg);
				session.flush();
				List<Clinic> clinics = getAll(Clinic.class);
				client.sendToClient(clinics);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occurred, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(msg instanceof DoctorClinic)  {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				session.saveOrUpdate((DoctorClinic)msg);
				session.flush();
				Clinic c = ((DoctorClinic) msg).getClinic();
				String hql = "FROM DoctorClinic DC WHERE DC.clinic = :clinic";

				@SuppressWarnings("unchecked")
				Query q = session.createQuery(hql);
				q.setParameter("clinic", c);
				List<DoctorClinic> doctorClinics = (List<DoctorClinic>) q.list();
				client.sendToClient(doctorClinics);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(msg instanceof Vaccine_Appointment)  {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();


				//int user_id = u.getId();
				session.saveOrUpdate((Vaccine_Appointment)msg);
				session.saveOrUpdate(((Vaccine_Appointment) msg).getClinic());
				session.saveOrUpdate(((Vaccine_Appointment) msg).getUser());

				LocalDate d = ((Vaccine_Appointment)msg).getDate();
				if(d.get(WeekFields.of(Locale.ITALY).weekOfYear()) == LocalDate.now().get(WeekFields.of(Locale.ITALY).weekOfYear())){ //appointment for this week
					int clinic_id = ((Vaccine_Appointment)msg).getClinic().getId();
					WeeklyClinicReport thisReport = session.get(WeeklyClinicReport.class, clinicReportMap.get(clinic_id));
					thisReport.getVaccineVisits().put(d.getDayOfWeek().toString(),
							thisReport.getVaccineVisits().get(d.getDayOfWeek().toString())+1);

					session.update(thisReport);
				}
				session.flush();

				//int user_id = ((Vaccine_Appointment)msg).getUser().getId();
				//User u = session.get(User.class, user_id);

				//List<Vaccine_Appointment> vaccineAppointments = ((Patient)u).getVaccine_appointments1();
				//client.sendToClient(vaccineAppointments);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(msg instanceof Corna_cheak_Appointment)  {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				//int user_id = u.getId();
				session.saveOrUpdate((Corna_cheak_Appointment)msg);

				LocalDate d = ((Corna_cheak_Appointment)msg).getDate();

				if(d.get(WeekFields.of(Locale.ITALY).weekOfYear()) == LocalDate.now().get(WeekFields.of(Locale.ITALY).weekOfYear())){ //appointment for this week
					int clinic_id = ((Corna_cheak_Appointment)msg).getClinic().getId();
					WeeklyClinicReport thisReport = session.get(WeeklyClinicReport.class, clinicReportMap.get(clinic_id));
					thisReport.getCovidTestVisits().put(d.getDayOfWeek().toString(),
							thisReport.getCovidTestVisits().get(d.getDayOfWeek().toString())+1);

					session.update(thisReport);
				}
				session.flush();

				//int user_id = ((Corna_cheak_Appointment)msg).getUser().getId();
				//User u = session.get(User.class, user_id);

				//List<Corna_cheak_Appointment> covidTestAppointments = ((Patient)u).getCorna_cheak_Appointments1();
				//client.sendToClient(covidTestAppointments);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(msg instanceof ProDoctorAppointment)  {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				//int user_id = u.getId();
				session.saveOrUpdate((ProDoctorAppointment)msg);

				LocalDate d = ((ProDoctorAppointment)msg).getDate();

				if(d.get(WeekFields.of(Locale.ITALY).weekOfYear()) == LocalDate.now().get(WeekFields.of(Locale.ITALY).weekOfYear())){ //appointment for this week
					int clinic_id = ((ProDoctorAppointment)msg).getClinic().getId();
					WeeklyClinicReport thisReport = session.get(WeeklyClinicReport.class, clinicReportMap.get(clinic_id));
					thisReport.getProfessionalDocVisits().put(d.getDayOfWeek().toString(),
							thisReport.getProfessionalDocVisits().get(d.getDayOfWeek().toString())+1);

					session.update(thisReport);
				}
				session.flush();

				//int user_id = ((Corna_cheak_Appointment)msg).getUser().getId();
				//User u = session.get(User.class, user_id);

				//List<Corna_cheak_Appointment> covidTestAppointments = ((Patient)u).getCorna_cheak_Appointments1();
				//client.sendToClient(covidTestAppointments);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(msg instanceof FamilyDoctorAppointment)  {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				//int user_id = u.getId();
				session.saveOrUpdate((FamilyDoctorAppointment)msg);

				LocalDate d = ((FamilyDoctorAppointment)msg).getDate();

				if(d.get(WeekFields.of(Locale.ITALY).weekOfYear()) == LocalDate.now().get(WeekFields.of(Locale.ITALY).weekOfYear())){ //appointment for this week
					int clinic_id = ((FamilyDoctorAppointment)msg).getClinic().getId();
					WeeklyClinicReport thisReport = session.get(WeeklyClinicReport.class, clinicReportMap.get(clinic_id));
					thisReport.getFamilyDocVisits().put(d.getDayOfWeek().toString(),
							thisReport.getFamilyDocVisits().get(d.getDayOfWeek().toString())+1);

					session.update(thisReport);
				}
				session.flush();

				//int user_id = ((Corna_cheak_Appointment)msg).getUser().getId();
				//User u = session.get(User.class, user_id);

				//List<Corna_cheak_Appointment> covidTestAppointments = ((Patient)u).getCorna_cheak_Appointments1();
				//client.sendToClient(covidTestAppointments);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(msg instanceof Patient) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				session.saveOrUpdate(((Patient)msg));
				session.flush();

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}

		//handling requests from client to get certain parts of the database
		else if(((String) msg).equals("#ClinicList")) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				List<Clinic> clinics = getAll(Clinic.class);
				client.sendToClient(clinics);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}

		else if(((String) msg).equals("#LOGIN")) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				List<User> users = getAll(User.class);
				client.sendToClient(users);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}

		}

		else if(((String) msg).equals("#DoctorClinicList")) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				List<DoctorClinic> doctorClinics = getAll(DoctorClinic.class);
				client.sendToClient(doctorClinics);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(((String) msg).equals("#Doctors")) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				List<Doctor> doctors = getAll(Doctor.class);
				client.sendToClient(doctors);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(((String) msg).equals("#username")) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				List<User> doctorClinics = getAll(User.class);
				client.sendToClient(doctorClinics);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}

		else if(((String) msg).split("\\|")[0].equals("#VaccineAppointments")) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				int user_id = Integer.parseInt(((String) msg).split("\\|")[1]);
				User u = session.get(User.class, user_id);
				String hql = "FROM Vaccine_Appointment VA WHERE VA.user = :user";

				@SuppressWarnings("unchecked")
				Query q = session.createQuery(hql);
				q.setParameter("user", u);

				List<Vaccine_Appointment> vaccineAppointments = (List<Vaccine_Appointment>) q.list();
				client.sendToClient(vaccineAppointments);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(((String) msg).split("\\|")[0].equals("#FamilyAppointments")) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				int user_id = Integer.parseInt(((String) msg).split("\\|")[1]);
				User u = session.get(User.class, user_id);
				String hql = "FROM FamilyDoctorAppointment VA WHERE VA.user = :user";

				@SuppressWarnings("unchecked")
				Query q = session.createQuery(hql);
				q.setParameter("user", u);

				List<FamilyDoctorAppointment> familyAppointments = (List<FamilyDoctorAppointment>) q.list();
				client.sendToClient(familyAppointments);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(((String) msg).split("\\|")[0].equals("#ProDoctorAppointments")) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				int user_id = Integer.parseInt(((String) msg).split("\\|")[1]);
				User u = session.get(User.class, user_id);
				String hql = "FROM ProDoctorAppointment VA WHERE VA.user = :user";

				@SuppressWarnings("unchecked")
				Query q = session.createQuery(hql);
				q.setParameter("user", u);

				List<ProDoctorAppointment> proAppointments = (List<ProDoctorAppointment>) q.list();
				client.sendToClient(proAppointments);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(((String) msg).split("\\|")[0].equals("#CovidTestAppointments")) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				int user_id = Integer.parseInt(((String) msg).split("\\|")[1]);
				User u = session.get(User.class, user_id);
				String hql = "FROM Corna_cheak_Appointment CA WHERE CA.user = :user";

				@SuppressWarnings("unchecked")
				Query q = session.createQuery(hql);
				q.setParameter("user", u);

				List<Corna_cheak_Appointment> covidTestAppointments = (List<Corna_cheak_Appointment>) q.list();
				client.sendToClient(covidTestAppointments);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occurred, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(((String) msg).split("\\|")[0].equals("#Delete_Vaccine_Appointment")) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				int app_id = Integer.parseInt(((String) msg).split("\\|")[1]);
				Vaccine_Appointment app = session.get(Vaccine_Appointment.class, app_id);
				int clinic_id = app.getClinic().getId();
				LocalDate d = app.getDate();
				Patient p = (Patient) (app.getUser());
				Clinic c = app.getClinic();
				p.getVaccine_appointments1().remove(app);
				c.getVaccine_appointments().remove(app);


				if(d.get(WeekFields.of(Locale.ITALY).weekOfYear()) == LocalDate.now().get(WeekFields.of(Locale.ITALY).weekOfYear())){ //appointment for this week

					WeeklyClinicReport thisReport = session.get(WeeklyClinicReport.class, clinicReportMap.get(clinic_id));
					thisReport.getVaccineVisits().put(d.getDayOfWeek().toString(),
							thisReport.getVaccineVisits().get(d.getDayOfWeek().toString())-1);

					session.update(thisReport);
				}
				session.flush();

				List<Vaccine_Appointment> vaccineAppointments = p.getVaccine_appointments1();
				client.sendToClient(vaccineAppointments);



				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(((String) msg).split("\\|")[0].equals("#Delete_Covid_Test_Appointment")) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				int app_id = Integer.parseInt(((String) msg).split("\\|")[1]);
				Corna_cheak_Appointment app = session.get(Corna_cheak_Appointment.class, app_id);
				int clinic_id = app.getClinic().getId();
				LocalDate d = app.getDate();
				Patient p = (Patient) (app.getUser());
				Clinic c = app.getClinic();
				p.getCorna_cheak_Appointments1().remove(app);
				c.getCorna_cheak_Appointments1().remove(app);


				if(d.get(WeekFields.of(Locale.ITALY).weekOfYear()) == LocalDate.now().get(WeekFields.of(Locale.ITALY).weekOfYear())){ //appointment for this week

					WeeklyClinicReport thisReport = session.get(WeeklyClinicReport.class, clinicReportMap.get(clinic_id));
					thisReport.getCovidTestVisits().put(d.getDayOfWeek().toString(),
							thisReport.getCovidTestVisits().get(d.getDayOfWeek().toString())-1);

					session.update(thisReport);
				}
				session.flush();

				List<Corna_cheak_Appointment> covidTestAppointments = p.getCorna_cheak_Appointments1();
				client.sendToClient(covidTestAppointments);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(((String) msg).split("\\|")[0].equals("#Delete_Family_Appointment")) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				int app_id = Integer.parseInt(((String) msg).split("\\|")[1]);
				FamilyDoctorAppointment app = session.get(FamilyDoctorAppointment.class, app_id);
				LocalDate d = app.getDate();
				int clinic_id = app.getClinic().getId();
				Patient p = (Patient) (app.getUser());
				Clinic c = app.getClinic();
				p.getFamily_Appointments1().remove(app);
				c.getFamily_appointments1().remove(app);


				if(d.get(WeekFields.of(Locale.ITALY).weekOfYear()) == LocalDate.now().get(WeekFields.of(Locale.ITALY).weekOfYear())){ //appointment for this week

					WeeklyClinicReport thisReport = session.get(WeeklyClinicReport.class, clinicReportMap.get(clinic_id));
					thisReport.getFamilyDocVisits().put(d.getDayOfWeek().toString(),
							thisReport.getFamilyDocVisits().get(d.getDayOfWeek().toString())-1);

					session.update(thisReport);
				}
				session.flush();

				List<FamilyDoctorAppointment> familyAppointments = p.getFamily_Appointments1();
				client.sendToClient(familyAppointments);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(((String) msg).split("\\|")[0].equals("#Clinic")) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				int clinic_id = Integer.parseInt(((String) msg).split("\\|")[1]);
				Clinic c = session.get(Clinic.class, clinic_id);

				client.sendToClient(c);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(((String) msg).split("\\|")[0].equals("#WeeklyReports")) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				int clinic_id = Integer.parseInt(((String) msg).split("\\|")[1]);
				Clinic c = session.get(Clinic.class, clinic_id);
				List<WeeklyClinicReport> reports = c.getWeeklyClinicReportList();

				client.sendToClient(reports);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
		else if(((String) msg).split("\\|")[0].equals("#Delete_Pro_Doctor_Appointment")) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();

				int app_id = Integer.parseInt(((String) msg).split("\\|")[1]);
				ProDoctorAppointment app = session.get(ProDoctorAppointment.class, app_id);
				int clinic_id = app.getClinic().getId();
				LocalDate d = app.getDate();
				Patient p = (Patient) (app.getUser());
				Doctor c = app.getDoctor();
				Clinic clinic = app.getClinic();
				clinic.getProAppointments().remove(app);
				p.getPro_Appointments1().remove(app);
				c.getAppointments().remove(app);

				if(d.get(WeekFields.of(Locale.ITALY).weekOfYear()) == LocalDate.now().get(WeekFields.of(Locale.ITALY).weekOfYear())){ //appointment for this week
					WeeklyClinicReport thisReport = session.get(WeeklyClinicReport.class, clinicReportMap.get(clinic_id));
					thisReport.getProfessionalDocVisits().put(d.getDayOfWeek().toString(),
							thisReport.getProfessionalDocVisits().get(d.getDayOfWeek().toString())-1);

					session.update(thisReport);
				}
				session.flush();

				List<ProDoctorAppointment> proAppointments = p.getPro_Appointments1();
				client.sendToClient(proAppointments);

				session.getTransaction().commit();
			}catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				System.err.println("An error occured, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				if(session != null)
					session.close();
			}
		}
	}



	@Override
	protected synchronized void clientDisconnected(ConnectionToClient client) {
		// TODO Auto-generated method stub

		System.out.println("Client Disconnected.");
		super.clientDisconnected(client);
	}



	@Override
	protected void clientConnected(ConnectionToClient client) {
		super.clientConnected(client);
		System.out.println("Client connected: " + client.getInetAddress());
	}

	public static void main(String[] args) throws IOException {

		try {
			sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

			generateClinics();


			session.getTransaction().commit();
		}catch (Exception exception) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			System.err.println("An error occurred, changes have been rolled back.");
			exception.printStackTrace();
		} finally {
			if(session != null)
				session.close();
		}
		//sessionFactory = getSessionFactory();

		ClinicServer server = new ClinicServer(Integer.parseInt(args[0]));
		// Integer.parseInt(args[0]));
		new Thread(new Runnable() {
			@Override
			public void run() {

				while (true){
					// the time for sends remainder mails
					String now = LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME);
					int value = ("00:00:00").compareTo(now);
					int value1 = ("05:05:30").compareTo(now);

					if (value1==0) {//value == 0
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						LocalDate tomorrow = LocalDate.now().plusDays(1);
						System.out.println(tomorrow);


						List<Corna_cheak_Appointment> covidTestAppointments = new ArrayList<>();
						List<Vaccine_Appointment> vaccine_appointments =  new ArrayList<>();
						List<ProDoctorAppointment> proDoctorAppointments = new ArrayList<>();
						List<FamilyDoctorAppointment> familyDoctorAppointments = new ArrayList<>();

						try {
							session = sessionFactory.openSession();
							session.beginTransaction();
							covidTestAppointments = getAll(Corna_cheak_Appointment.class);
							vaccine_appointments = getAll(Vaccine_Appointment.class);
							proDoctorAppointments = getAll(ProDoctorAppointment.class);
							familyDoctorAppointments = getAll(FamilyDoctorAppointment.class);
							session.getTransaction().commit();
						} catch (Exception exception) {
							if (session != null) {
								session.getTransaction().rollback();
							}
							System.err.println("An error occured, changes have been rolled back.");
							exception.printStackTrace();
						} finally {
							if (session != null)
								session.close();
						}
						System.out.println(" after get list");
						System.out.println(covidTestAppointments.size());
						for (int i = 0; i < covidTestAppointments.size(); i++) {

							if (covidTestAppointments.get(i).getDate().isEqual(tomorrow)) {
								SendMail mail = new SendMail();
								mail.send_remainder_covidtest(covidTestAppointments.get(i).getUser(), covidTestAppointments.get(i));
							}
						}
						System.out.println(vaccine_appointments.size());
						for(int i = 0 ; i<vaccine_appointments.size() ; i++){
							if (vaccine_appointments.get(i).getDate().isEqual(tomorrow)) {
								SendMail mail = new SendMail();
								mail.send_remainder_vaccine(vaccine_appointments.get(i).getUser(), vaccine_appointments.get(i));
							}
						}
						for(int i = 0 ; i<proDoctorAppointments.size() ; i++){
							if (proDoctorAppointments.get(i).getDate().isEqual(tomorrow)) {
								SendMail mail = new SendMail();
								mail.send_remainder_pro(proDoctorAppointments.get(i).getUser(), proDoctorAppointments.get(i));
							}
						}
						for(int i = 0 ; i<familyDoctorAppointments.size() ; i++){
							if (familyDoctorAppointments.get(i).getDate().isEqual(tomorrow)) {
								SendMail mail = new SendMail();
								mail.send_remainder_family(familyDoctorAppointments.get(i).getUser(), familyDoctorAppointments.get(i));
							}
						}
					}

					if((LocalDate.now().getDayOfWeek().equals("SUNDAY") && value == 0) || clinicReportMap.isEmpty()){ //initializing reports each week
						//System.out.println("In Create Report!!!!!!!!!!!!!!!!");
						List<Clinic> clinicsInDB = new ArrayList<>();
						List<Vaccine_Appointment> vacAppInDB = new ArrayList<>();
						List<Corna_cheak_Appointment> covidTestAppInDB = new ArrayList<>();
						List<DoctorClinic> doctorClinicsInDB = new ArrayList<>();
						try {
							session = sessionFactory.openSession();
							session.beginTransaction();

							clinicsInDB = getAll(Clinic.class);
							vacAppInDB = getAll(Vaccine_Appointment.class);
							covidTestAppInDB = getAll(Corna_cheak_Appointment.class);
							doctorClinicsInDB = getAll(DoctorClinic.class);

							for(Clinic c: clinicsInDB){ //initializing the reports for each clinic
								Integer clinic_id = Integer.valueOf(c.getId());
								List<Vaccine_Appointment> thisVacApps = vacAppInDB.stream().filter(app -> c.equals(app.getClinic())).collect(Collectors.toList());//lists of relevant
								List<Corna_cheak_Appointment> thisCovidTestApps = covidTestAppInDB.stream().filter(app -> c.equals(app.getClinic())).collect(Collectors.toList());
								List<DoctorClinic> thisDoctorClinics = doctorClinicsInDB.stream().filter(dc -> c.equals(dc.getClinic())).collect(Collectors.toList());
								WeeklyClinicReport newReport = new WeeklyClinicReport(LocalDate.now(), c, thisDoctorClinics);
								c.getWeeklyClinicReportList().add(newReport);

								for(Vaccine_Appointment app: thisVacApps){
									if(app.getDate().get(WeekFields.of(Locale.ITALY).weekOfYear()) == LocalDate.now().get(WeekFields.of(Locale.ITALY).weekOfYear())) { //Locale.italy for proximity to israel.
										String dayOfWeek = app.getDate().getDayOfWeek().toString();
										newReport.getVaccineVisits().put(dayOfWeek, newReport.getVaccineVisits().get(dayOfWeek)+1);
									}
								}

								for(Corna_cheak_Appointment app: thisCovidTestApps){
									if(app.getDate().get(WeekFields.of(Locale.ITALY).weekOfYear()) == LocalDate.now().get(WeekFields.of(Locale.ITALY).weekOfYear())) { //Locale.italy for proximity to israel.
										String dayOfWeek = app.getDate().getDayOfWeek().toString();
										newReport.getVaccineVisits().put(dayOfWeek, newReport.getVaccineVisits().get(dayOfWeek)+1);
									}
								}
								session.saveOrUpdate(newReport);
								clinicReportMap.put(clinic_id, newReport.getId());
								session.saveOrUpdate(c);
							}
							session.getTransaction().commit();
						}catch (Exception exception) {
							if (session != null) {
								session.getTransaction().rollback();
							}
							System.err.println("An error occurred, changes have been rolled back.");
							exception.printStackTrace();
						} finally {
							if(session != null)
								session.close();
						}
					}



				}
			}
		}).start();


		server.listen();

	}

}