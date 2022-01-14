package il.ac.haifa.ClinicSystem.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
@Entity
public class Corna_cheak_Appointment  extends Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vaccine_appointment_id")
    private int id;
    /*@OneToOne
    private  Quiz quiz;*/
    private String time;



    public Corna_cheak_Appointment(LocalDate date, Clinic clinic) {
        super(date, clinic);

    }

    public Corna_cheak_Appointment(Appointment a) {
        super(a);

    }

    public Corna_cheak_Appointment() {

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
