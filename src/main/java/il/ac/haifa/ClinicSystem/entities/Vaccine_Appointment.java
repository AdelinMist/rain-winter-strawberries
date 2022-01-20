package il.ac.haifa.ClinicSystem.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "vaccine_appointments")

public class Vaccine_Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vaccine_appointment_id")
    private int id;
    //private transient DatePicker dayPicker;
    private  String day;
    private  String time; // work like "12:10"
    private LocalDate date;

    @ManyToOne
    private Clinic clinic; // maybe need more
    public Clinic getClinic() {
        return clinic;
    }

    @ManyToOne
    private User user;

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

    //private transient ChoiceBox<String> timeOptions; // work like "12:10"
    public Vaccine_Appointment(LocalDate date, String time , Clinic clinic){
        this.date = date;
        this.clinic = clinic;
        this.time = time;
    }

    public Vaccine_Appointment(){


    }
    public Vaccine_Appointment( String time, boolean taken){
        this.time = time;
       // this.taken = taken;

    }
    public Vaccine_Appointment(String day, String time, boolean taken){
        this.day = day;
        this.time = time;
       // this.taken = taken;

    }
    public Vaccine_Appointment(Vaccine_Appointment d){
       // this.id = d.getId();
        this.day = d.getDay();
        this.time = d.getTime();
//
    }
   public String getDayOfWeek() {
        return day;
   }

    /*public int getId() {
        return id;
    }*/

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return this.time;
    }
   /* public ChoiceBox<String> getTimeOptions() {
        return timeOptions;
    }

    public void setTimeOptions(ChoiceBox<String> timeOptions) {
        this.timeOptions = timeOptions;
    }*/

    /*public DatePicker getDayPicker() {
        return dayPicker;
    }

    public void setDayPicker(DatePicker dayPicker) {
        this.dayPicker = dayPicker;
    }*/

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    //public boolean isTaken() {
       // return taken;
    //}

   // public void setTaken(boolean taken) {
     //   this.taken = taken;
    //}


}
