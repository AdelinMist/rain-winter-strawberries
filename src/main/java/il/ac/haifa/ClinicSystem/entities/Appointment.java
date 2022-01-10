package il.ac.haifa.ClinicSystem.entities;

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
    private Date date;
    private String time; // work like "12:10"
    private String week; // means week many weeks forward the appointment , 0 mean this week

    @ManyToOne
    private Clinic clinic; // maybe need more





}
