package il.ac.haifa.ClinicSystem.entities;

import javax.persistence.*;
import javax.print.Doc;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ChoiceBox;
import javafx.util.Pair;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "doctorclinics")
public class DoctorClinic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable=true)
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable=true)
    private Doctor doctor;


    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "doctor_clinic_hours_mapping",
            joinColumns = {@JoinColumn(name = "doctor_clinic_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "working_day")
    @Column(name = "working_hours")
    private Map<String, Pair<LocalTime, LocalTime>> workingHours;

    private transient ChoiceBox<String> dayOfWeek;
    private transient StringProperty curStartTime;
    private transient StringProperty curStopTime;
    private transient StringProperty name;
    private transient StringProperty specialization;

    public DoctorClinic() {
    }

    public DoctorClinic(Clinic clinic, Doctor doctor, Map<String, Pair<LocalTime, LocalTime>> workingHours) {
        this.clinic = clinic;
        this.doctor = doctor;
        this.workingHours = workingHours;

    }

    public DoctorClinic(DoctorClinic dc){
        this.id = dc.getId();
        this.clinic = new Clinic(dc.getClinic());
        this.doctor = new Doctor(dc.getDoctor());
        this.workingHours = new HashMap<String, Pair<LocalTime, LocalTime>>(dc.getWorkingHours());
    }

    public StringProperty nameProperty() { return name; }
    public void setNameProperty(SimpleStringProperty s){ this.name = s; }
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }

    public StringProperty specializationProperty() { return specialization; }
    public void setSpecializationProperty(SimpleStringProperty s){ this.specialization = s; }
    public String getSpecialization() { return specialization.get(); }
    public void setSpecialization(String name) { specialization.set(name); }

    public StringProperty curStartTimeProperty() { return curStartTime; }
    public void setCurStartTimeProperty(SimpleStringProperty s){ this.curStartTime = s; }
    public String getCurStartTime() { return curStartTime.get(); }
    public void setCurStartTime(String name) { curStartTime.set(name); }

    public StringProperty curStopTimeProperty() { return curStopTime; }
    public void setCurStopTimeProperty(SimpleStringProperty s){ this.curStopTime = s; }
    public String getCurStopTime() { return curStopTime.get(); }
    public void setCurStopTime(String name) { curStopTime.set(name); }

    public ChoiceBox<String> getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(ChoiceBox<String> dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getId() {
        return id;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Map<String, Pair<LocalTime, LocalTime>> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Map<String, Pair<LocalTime, LocalTime>> workingHours) {
        this.workingHours = workingHours;
    }
}
