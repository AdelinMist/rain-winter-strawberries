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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ClinicServer extends AbstractServer{

	private static Session session;
	private static SessionFactory sessionFactory;

	private static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();

		// Add ALL of your entities here. You can also try adding a whole package.
		configuration.addAnnotatedClass(Clinic.class);
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Patient.class);
		configuration.addAnnotatedClass(DoctorClinic.class);
		configuration.addAnnotatedClass(Doctor.class);
		configuration.addAnnotatedClass(Vaccine_Appointment.class);
		configuration.addAnnotatedClass(Appointment.class);
		configuration.addAnnotatedClass(Corna_cheak_Appointment.class);
		configuration.addAnnotatedClass(Quiz.class);





		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();

		return configuration.buildSessionFactory(serviceRegistry);
	}

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
		Clinic c = new Clinic("The White Tower", "Tar Valon", open, close, testopen, testclose, vaccopen, vaccclose, true, true);
		//session.saveOrUpdate(temp);
		Doctor d = new Doctor("coolDoctor420", "password", "Mat Matthews", "Neurology");


		DoctorClinic dc = new DoctorClinic(c, d, workingHours);


		List<DoctorClinic> dcList = new ArrayList<DoctorClinic>();


		dcList.add(dc);

		c.setDoctorClinics(dcList);
		d.setDoctorClinics(dcList);



		session.saveOrUpdate(c);
		session.saveOrUpdate(d);
		session.saveOrUpdate(dc);

		 /*
		 * The call to session.flush() updates the DB immediately without ending the transaction.
		 * Recommended to do after an arbitrary unit of work.
		 * MANDATORY to do if you are saving a large amount of data - otherwise you may get
		cache errors.
		 */
		session.flush();
	}

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
		if(msg instanceof DoctorClinic)  {
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
			System.err.println("An error occured, changes have been rolled back.");
			exception.printStackTrace();
		} finally {
			if(session != null)
				session.close();
		}
		//sessionFactory = getSessionFactory();

		ClinicServer server = new ClinicServer(Integer.parseInt(args[0]));
		// Integer.parseInt(args[0]));
		server.listen();

	}

}