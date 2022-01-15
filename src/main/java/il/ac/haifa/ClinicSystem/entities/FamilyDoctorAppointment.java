package il.ac.haifa.ClinicSystem.entities;

import javax.persistence.*;
import java.io.Serializable;

public class FamilyDoctorAppointment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FamilyDoctorAppointment_id")

    private int id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable=true) //am not sure about doctor_id being correct here
    private Doctor doctor;
}