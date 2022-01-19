package il.ac.haifa.ClinicSystem.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "patients")
public class Patient extends User implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="patient_id")
    private int id;

    private String clinic_name; // for appointment 3.5 any user need own clinic (sister appointment)

    private LocalDate green_pass = null;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Vaccine_Appointment> vaccine_appointments1;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Corna_cheak_Appointment> Corna_cheak_Appointments1;


    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Sister_Appointment> sister_appointments1;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<labAppointment> lab_appointments1;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<FamilyDoctorAppointment> family_Appointments1;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ProDoctorAppointment> pro_Appointments1;

    public void add_pro_Appointment(ProDoctorAppointment appointment){
        this.pro_Appointments1.add(appointment);

    }


    public void add_Sister_Appointment(Sister_Appointment appointment){
        this.sister_appointments1.add(appointment);

    }

    public void add_lab_Appointment(labAppointment appointment){
        this.lab_appointments1.add(appointment);

    }
    public void add_Family_Appointment(FamilyDoctorAppointment appointment){
        this.family_Appointments1.add(appointment);
    }

    public void add_vaccine_appointment(Vaccine_Appointment vaccine_appointment){
        this.vaccine_appointments1.add(vaccine_appointment);
        System.out.println(vaccine_appointment.getTime() + "insert to the list!");
    }

    public void add_coronaTest_appointment(Corna_cheak_Appointment corna_cheak_appointment){ //It hurts my soul
        this.Corna_cheak_Appointments1.add(corna_cheak_appointment);
    }

    public List<Vaccine_Appointment> getVaccine_appointments1() {
        return vaccine_appointments1;
    }

    public void setVaccine_appointments1(List<Vaccine_Appointment> vaccine_appointments1) {
        this.vaccine_appointments1 = vaccine_appointments1;
    }

    public List<Corna_cheak_Appointment> getCorna_cheak_Appointments1() {
        return Corna_cheak_Appointments1;
    }

    public void setCorna_cheak_Appointments1(List<Corna_cheak_Appointment> corna_cheak_Appointments1) {
        Corna_cheak_Appointments1 = corna_cheak_Appointments1;
    }

    public List<FamilyDoctorAppointment> getFamily_Appointments1() {
        return family_Appointments1;
    }

    public void setFamily_Appointments1(List<FamilyDoctorAppointment> family_Appointments1) {
        this.family_Appointments1 = family_Appointments1;
    }

    public Patient(String clinic_name) {
        this.clinic_name = clinic_name;
    }
    public Patient() {

    }

    public Patient(String username, String password, String email, String clinic_name, String name) {
        super(username, password, email, name);
        this.clinic_name = clinic_name;
    }

    public List<labAppointment> getLab_appointments1() {
        return lab_appointments1;
    }

    public List<ProDoctorAppointment> getPro_Appointments1() {
        return pro_Appointments1;
    }

    public Patient(User u, String clinic_name) {
        super(u);
        this.clinic_name = clinic_name;
    }

    public LocalDate getGreen_pass() {
        return green_pass;
    }

    public void setGreen_pass(LocalDate green_pass) {
        this.green_pass = green_pass;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public List<Sister_Appointment> getSister_appointments1() {
        return sister_appointments1;
    }



    public int getId() {
        return this.id;
    }
}
