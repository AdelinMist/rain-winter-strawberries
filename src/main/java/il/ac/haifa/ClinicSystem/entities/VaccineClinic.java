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
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.util.Pair;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "vaccineclinics")
public class VaccineClinic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable=true)
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "vaccine_id", nullable=true)
    private Vaccine_Appointment vaccine;




    private transient ChoiceBox<String> timeOptions;
    private transient DatePicker dayPicker;
    private transient StringProperty day;
    private transient StringProperty time;
    private transient StringProperty name;
    private transient StringProperty place;

    public VaccineClinic() {
    }

    public VaccineClinic(Clinic clinic, Vaccine_Appointment vaccine) {
        this.clinic = clinic;
        this.vaccine = vaccine;


    }

    public VaccineClinic(VaccineClinic dc){
        this.id = dc.getId();
        this.clinic = new Clinic(dc.getClinic());
        this.vaccine = new Vaccine_Appointment(dc.getVaccine());

    }

    public StringProperty nameProperty() { return name; }
    public void setNameProperty(SimpleStringProperty s){ this.name = s; }
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }

    public StringProperty placeProperty() { return place; }
    public void setPlaceProperty(SimpleStringProperty s){ this.place = s; }
    public String getPlace() { return place.get(); }
    public void setPlace(String name) {
        place.set(name);
    }

    public StringProperty timeProperty() { return time; }
    public void setTimeProperty(SimpleStringProperty s){ this.time = s; }
    public String getTime() { return time.get(); }
    public void setTime(String name) {
        time.set(name);
    }

    public StringProperty dayProperty() { return day; }
    public void setDayProperty(SimpleStringProperty s){ this.day = s; }
    public String getDay() { return day.get(); }
    public void setDay(String name) {
        day.set(name);
    }


    public ChoiceBox<String> getTimeOptions() {
        return timeOptions;
    }

    public void setTimeOptions(ChoiceBox<String> timeOptions) {
        this.timeOptions = timeOptions;
    }

    public DatePicker getDayPicker() { return dayPicker; }
    public void setDayPicker(DatePicker name) { dayPicker = name; }

    public int getId() {
        return id;
    }

    public Clinic getClinic() {
        return clinic;
    }


    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Vaccine_Appointment getVaccine() {
        return vaccine;
    }

    public void setDoctor(Vaccine_Appointment vaccine) {
        this.vaccine = vaccine;
    }


}
