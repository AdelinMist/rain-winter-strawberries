package il.ac.haifa.ClinicSystem.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity

public class ProDoctorAppointment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ProDoctorAppointment_id")
    private int id;
    private LocalDate date;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ManyToOne
    private Clinic clinic;
    private String time;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProDoctorAppointment(LocalDate date, String time, Clinic clinic, Doctor doctor) {
        this.date = date;
        this.clinic = clinic;
        this.time = time;
        this.doctor = doctor;
    }
    public ProDoctorAppointment(){}
    @ManyToOne
    private User user;
    public User getUser(){
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
    public LocalDate getDate(){
        return this.date;
    }

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable=true) //am not sure about doctor_id being correct here
    private Doctor doctor;

    private String day;
    public void setDay(String day){
        this.day = day;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
