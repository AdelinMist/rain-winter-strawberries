package il.ac.haifa.ClinicSystem.entities;

import javax.persistence.*;
import java.io.Serializable;

public class ProDoctorAppointment extends Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ProDoctorAppointment_id")

    private int id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable=true) //am not sure about doctor_id being correct here
    private Doctor doctor;
}
