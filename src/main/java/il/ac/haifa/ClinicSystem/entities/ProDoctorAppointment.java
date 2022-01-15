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
    @ManyToOne
    private Clinic clinic; // maybe need more
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
}
