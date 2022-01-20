package il.ac.haifa.ClinicSystem.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "lab_appointments")
public class labAppointment   implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Sister_Appointment_id")
    private int id;
    private  String day;
    private  String time; // work like "12:10"
    //private String clinic_name; // for any user have own clinic
    public labAppointment(LocalDate date, String time , Clinic clinic) {
        this.date = date;
        this.clinic = clinic;
        this.time = time;
    }

    private String username;

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    private boolean visited=false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public labAppointment( String time,LocalDate date , Clinic clinic) {
        this.date = date;
        this.clinic = clinic;
        this.time = time;
    }

    public labAppointment(){}

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