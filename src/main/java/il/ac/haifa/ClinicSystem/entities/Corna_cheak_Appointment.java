package il.ac.haifa.ClinicSystem.entities;

import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "corna_cheak_appointments")
@Polymorphism(type = PolymorphismType.EXPLICIT)

public class Corna_cheak_Appointment  extends Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id

    @Column(name="corona_cheak_appointment_id")
    private int id;
    @OneToOne
    private  Quiz quiz;



    private String time;
    private String day;


    public Corna_cheak_Appointment(LocalDate date, String time , Clinic clinic){
        super(date,clinic);
        this.time = time;
    }
    public Corna_cheak_Appointment(LocalDate date, Clinic clinic) {
        super(date, clinic);

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
