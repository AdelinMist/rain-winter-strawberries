package il.ac.haifa.ClinicSystem.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "appointments")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @Column(name="appointment_id")

    private int id;
    private LocalDate date;
    //private String time; the time will be in the sub class like vaccine appointment
    private String week; // means week many weeks forward the appointment , 0 mean this week
    @ManyToOne
    private Clinic clinic; // maybe need more
    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }


    public Appointment(LocalDate day, String time, Clinic clinic) {

        this.date = day;
        //this.time = time;
        this.clinic = clinic;
    }
    public Appointment(LocalDate date, Clinic clinic){
        this.date = date;
        this.clinic = clinic;
    }
    public LocalDate getDate(){
        return this.date;
    }



    public Appointment(Appointment a) {
        this.id = a.id;
        this.date = a.date;
       // this.time = a.time;
        this.clinic = a.clinic;
    }

    public Appointment() {

    }


}
