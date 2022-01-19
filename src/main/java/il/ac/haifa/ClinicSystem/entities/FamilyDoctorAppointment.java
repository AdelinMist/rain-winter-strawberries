package il.ac.haifa.ClinicSystem.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "FamilyDoctor_appointments")
public class FamilyDoctorAppointment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FamilyDoctorAppointment_id")
    private int id;
    private  String day;
    private  String time; // saved as "hh:mm" like "12:10"
    public FamilyDoctorAppointment(LocalDate date, String time , Clinic clinic) {
        this.date = date;
        this.clinic = clinic;
        this.time = time;
    }


    private boolean is_family;
    private LocalDate date;
    @ManyToOne
    private Clinic clinic; // maybe need more
    public Clinic getClinic() {
        return this.clinic;
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

    public FamilyDoctorAppointment( String time,LocalDate date , Clinic clinic) {
        this.date = date;
        this.clinic = clinic;
        this.time = time;
    }

    public FamilyDoctorAppointment(){}

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

    public boolean isIs_family() {
        return is_family;
    }

    public void setIs_family(boolean is_family) {
        this.is_family = is_family;
    }
}