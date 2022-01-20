package il.ac.haifa.ClinicSystem.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String specialization;

    @OneToMany(mappedBy = "doctor")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DoctorClinic> doctorClinics = null; //each doctor may work in several clinic and for each clinic he needs info about work times and etc.
    @OneToMany(mappedBy = "doctor")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ProDoctorAppointment> appointments;


    public Doctor() {

    }

    public Doctor(String username, String pasword, String name, String specialization , String email) {
        super(username, pasword , email, name);
        this.specialization = specialization;
    }

    public Doctor(Doctor d){
        super((User)d);
        this.id = d.getId();
        this.specialization = d.getSpecialization();
        this.doctorClinics = d.getDoctorClinics();
    }
    private  Clinic clinic;

    public List<DoctorClinic> getDoctorClinics() {
        return doctorClinics;
    }

    public void setDoctorClinics(List<DoctorClinic> doctorClinics) {
        this.doctorClinics = doctorClinics;
    }

    public int getId() {
        return this.id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String mailAdd) {
        this.specialization = mailAdd;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public List<ProDoctorAppointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<ProDoctorAppointment> appointments) {
        this.appointments = appointments;
    }
    public void add(ProDoctorAppointment appointment){
        this.appointments.add(appointment);
    }

}
