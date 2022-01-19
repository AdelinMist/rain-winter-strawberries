package il.ac.haifa.ClinicSystem.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "secretaries")
public class Secretary extends User{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @OneToOne
    private Clinic clinic;

    private String clinic_name;

    public Secretary(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public Secretary() {

    }

    public Clinic getClinic() {
        return clinic;
    }

    public Secretary(String username, String password, String email, String clinic_name, String name, Clinic clinic) {
        super(username, password, email, name);
        this.clinic_name = clinic_name;
        this.clinic=clinic;
    }

    public Secretary(User u, String clinic_name) {
        super(u);
        this.clinic_name = clinic_name;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }


}
