package il.ac.haifa.ClinicSystem.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vaccine_appointments")
public class Vaccine_Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vaccine_appointment_id")
    private int id;
    private String day;
    private String time; // work like "12:10"
    private boolean taken;
    @OneToMany(mappedBy = "vaccine")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VaccineClinic> vaccineClinics = null;

    public Vaccine_Appointment(){


    }
    public Vaccine_Appointment(String day, String time, boolean taken){
        this.day = day;
        this.time = time;
        this.taken = taken;

    }
    public Vaccine_Appointment(Vaccine_Appointment d){
        this.id = d.getId();
        this.day = d.getDay();
        this.time = d.getTime();
        this.taken = d.isTaken();

    }
    public String getDayOfWeek() {
        return day;
    }

    public int getId() {
        return id;
    }

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

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public void setVaccineClinic(List<VaccineClinic> vaccineClinics) {
        this.vaccineClinics = vaccineClinics;
    }

}
