package il.ac.haifa.ClinicSystem.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "clinic_managers")
public class ClinicManager extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="clinic_manager_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "clinic_id", nullable=true)
    private Clinic clinic;

    public ClinicManager() {

    }

    public ClinicManager(String username, String pasword, String name, String email, Clinic c) {
        super(username, pasword , email, name);
        this.clinic = c;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public int getId() {
        return this.id;
    }
}
