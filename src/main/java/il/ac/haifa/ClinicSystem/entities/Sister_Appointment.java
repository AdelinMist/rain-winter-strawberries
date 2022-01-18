package il.ac.haifa.ClinicSystem.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "sister_appointments")
public class Sister_Appointment   implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Sister_Appointment_id")
    private int id;
    // maybe need a lab? what is the difference between lab and clinic ?
    private  String day;
    private  String time; // work like "12:10"
    //private String clinic_name; // for any user have own clinic
    public Sister_Appointment(LocalDate date, String time , Clinic clinic) {
        this.date = date;
        this.clinic = clinic;
        this.time = time;
    }

    private LocalDate date;
    @ManyToOne
    private Clinic clinic; // maybe need more
    public Clinic getClinic() {
        return clinic;
    }
    @ManyToOne
    User user;
    public User getUser(){
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
    public LocalDate getDate(){
        return this.date;
    }

    /*public Sister_Appointment(LocalDate date, String time , Clinic clinic, String clinic_name) {
        super(date, clinic);
        this.time = time;
        this.clinic_name = clinic_name;
    }*/

    public Sister_Appointment( String time,LocalDate date , Clinic clinic) {
        this.date = date;
        this.clinic = clinic;
        this.time = time;
    }

    public Sister_Appointment(){}

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
