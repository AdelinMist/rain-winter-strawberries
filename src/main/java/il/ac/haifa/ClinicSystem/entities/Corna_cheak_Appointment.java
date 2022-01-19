package il.ac.haifa.ClinicSystem.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "corna_cheak_appointments")

public class Corna_cheak_Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="corona_cheak_appointment_id")
    private int id;
    @OneToOne
    private  Quiz quiz;
   private LocalDate date;
    @ManyToOne
    private Clinic clinic; // maybe need more
    @ManyToOne
    User user;
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
    public  void setUser(User user){
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


    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    private String time;
    private String day;


    public Corna_cheak_Appointment(LocalDate date, String time , Clinic clinic){
        this.date = date;
        this.time = time;
        this.clinic = clinic;
    }
    public Corna_cheak_Appointment(LocalDate date, Clinic clinic) {
        this.date = date;
        this.clinic = clinic;

    }



    public Corna_cheak_Appointment() {

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public void setDay(String day) {
        this.day = day;
    }


}
