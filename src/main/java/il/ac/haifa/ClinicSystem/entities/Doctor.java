package il.ac.haifa.ClinicSystem.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.swing.*;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String specialization;

    @OneToMany(mappedBy = "doctor")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DoctorClinic> doctorClinics = null;

    public Doctor() {

    }

    public Doctor(String username, String pasword, String name, String specialization) {
        super(username, pasword);
        this.name = name;
        this.specialization = specialization;
    }

    public Doctor(Doctor d){
        super((User)d);
        this.id = d.getId();
        this.name = d.getName();
        this.specialization = d.getSpecialization();
        this.doctorClinics = d.getDoctorClinics();
    }

    public List<DoctorClinic> getDoctorClinics() {
        return doctorClinics;
    }

    public void setDoctorClinics(List<DoctorClinic> doctorClinics) {
        this.doctorClinics = doctorClinics;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String mailAdd) {
        this.specialization = mailAdd;
    }
}
