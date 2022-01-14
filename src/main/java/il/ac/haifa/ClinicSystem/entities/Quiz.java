package il.ac.haifa.ClinicSystem.entities;

import javax.persistence.*;

@Entity
//@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="quiz_id")

    private int id;

    private String answer1;
    private String answer2;

    @OneToOne
    private Corna_cheak_Appointment appointment;
    public Quiz(String answer1,String answer2){
        this.answer1= answer1;
        this.answer2 = answer2;
    }




}
