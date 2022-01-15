package il.ac.haifa.ClinicSystem.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int id;

    @Column(name = "username")
    protected String username;
    private String clinic_name; // for appointment 3.5 any user need own clinic (sister appointment)
    protected byte[] password;
    protected byte[] salt;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Vaccine_Appointment> vaccine_appointments1;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Corna_cheak_Appointment> Corna_cheak_Appointments1;


    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Sister_Appointment> sister_appointments1;

    public void add_Sister_Appointment(Sister_Appointment appointment){
        this.sister_appointments1.add(appointment);

    }

    public void add_vaccine_appointment(Vaccine_Appointment vaccine_appointment){
        this.vaccine_appointments1.add(vaccine_appointment);
        System.out.println(vaccine_appointment.getTime() + "insert to the list!");

    }

    public void add_coronaTest_appointment(Corna_cheak_Appointment corna_cheak_appointment){
        this.Corna_cheak_Appointments1.add(corna_cheak_appointment);

    }

    public User() {

    }

    public User(String username, String password) {
        this.username = username;

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        this.salt = salt;

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(salt);

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        this.password = hashedPassword;
    }

    public User(User u){
        this.username = u.getUsername();
        this.salt = u.getSalt().clone();
        this.password = u.getPassword().clone();
        this.id = u.getId();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getSalt() {
        return salt;
    }

    public int getId() {
        return id;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        this.salt = salt;

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(salt);

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        this.password = hashedPassword;
    }
}
