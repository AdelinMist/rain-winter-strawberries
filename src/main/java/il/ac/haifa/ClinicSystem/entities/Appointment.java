package il.ac.haifa.ClinicSystem.entities;

import javafx.scene.control.ChoiceBox;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "appointments")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="appointment_id")

    private int id;
    private Date day;
    private String time; // work like "12:10"

    @ManyToOne
    private Clinic clinic; // maybe need more

    public Appointment(int id, Date day, String time, Clinic clinic) {
        this.id = id;
        this.day = day;
        this.time = time;
        this.clinic = clinic;
    }

    public Appointment(Appointment a) {
        this.id = a.id;
        this.day = a.day;
        this.time = a.time;
        this.clinic = a.clinic;
    }

    public Appointment() {

    }
}
