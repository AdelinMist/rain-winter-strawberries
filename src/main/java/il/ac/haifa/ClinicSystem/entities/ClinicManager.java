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


}
