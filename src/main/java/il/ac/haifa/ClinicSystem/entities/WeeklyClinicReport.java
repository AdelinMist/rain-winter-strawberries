package il.ac.haifa.ClinicSystem.entities;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ChoiceBox;
import javafx.util.Pair;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Entity
@Table(name = "weekly_clinic_reports")
public class WeeklyClinicReport implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable=true)
    private Clinic clinic;

    private LocalDate dateOfCreation;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "family_doctor_visits_mapping",
            joinColumns = {@JoinColumn(name = "familyDocVisits_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "day")
    @Column(name = "family_doc_visits")
    private Map<String, Integer> familyDocVisits = new HashMap<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "pediatric_doctor_visits_mapping",
            joinColumns = {@JoinColumn(name = "pediatricDocVisits_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "day")
    @Column(name = "pediatric_doc_visits")
    private Map<String, Integer> pediatricDocVisits= new HashMap<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "professional_doctor_visits_mapping",
            joinColumns = {@JoinColumn(name = "professionalDocVisits_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "day")
    @Column(name = "professional_doc_visits")
    private Map<String, Integer> professionalDocVisits= new HashMap<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "vaccine_visits_mapping",
            joinColumns = {@JoinColumn(name = "vaccineVisits_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "day")
    @Column(name = "vaccine_visits")
    private Map<String, Integer> vaccineVisits= new HashMap<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "covid_test_visits_mapping",
            joinColumns = {@JoinColumn(name = "covidTestVisits_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "day")
    @Column(name = "covid_test_visits")
    private Map<String, Integer> covidTestVisits= new HashMap<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "nurse_visits_mapping",
            joinColumns = {@JoinColumn(name = "nurseVisits_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "day")
    @Column(name = "nurse_visits")
    private Map<String, Integer> nurseVisits= new HashMap<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "lab_visits_mapping",
            joinColumns = {@JoinColumn(name = "labVisits_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "day")
    @Column(name = "lab_visits")
    private Map<String, Integer> labVisits= new HashMap<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "doctor_visits_sunday_mapping",
            joinColumns = {@JoinColumn(name = "doctorVisits_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "day")
    @Column(name = "doctor_visits_sunday")
    private Map<String, Double> doctorVisitsSunday= new HashMap<>();//the integer is the doctor id, need to set up local variables for num of appointments and sum of days to create average

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "doctor_visits_monday_mapping",
            joinColumns = {@JoinColumn(name = "doctorVisits_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "day")
    @Column(name = "doctor_visits_monday")
    private Map<String, Double> doctorVisitsMonday= new HashMap<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "doctor_visits_tuesday_mapping",
            joinColumns = {@JoinColumn(name = "doctorVisits_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "day")
    @Column(name = "doctor_visits_tuesday")
    private Map<String, Double> doctorVisitsTuesday= new HashMap<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "doctor_visits_wednesday_mapping",
            joinColumns = {@JoinColumn(name = "doctorVisits_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "day")
    @Column(name = "doctor_visits_wednesday")
    private Map<String, Double> doctorVisitsWednesday= new HashMap<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "doctor_visits_thursday_mapping",
            joinColumns = {@JoinColumn(name = "doctorVisits_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "day")
    @Column(name = "doctor_visits_thursday")
    private Map<String, Double> doctorVisitsThursday= new HashMap<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "doctor_visits_friday_mapping",
            joinColumns = {@JoinColumn(name = "doctorVisits_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "day")
    @Column(name = "doctor_visits_friday")
    private Map<String, Double> doctorVisitsFriday= new HashMap<>();

    private int vaccineNotArrivedCount = 0;
    private int covidTestNotArrivedCount = 0;
    private int pediatricDocNotArrivedCount = 0;
    private int familyDocNotArrivedCount = 0;
    private int professionalDocNotArrivedCount = 0;
    private int nurseNotArrivedCount = 0;
    private int labNotArrivedCount = 0;

    private transient DoubleProperty sunday; //stringproperties for one of the reports displays
    private transient DoubleProperty monday;
    private transient DoubleProperty tuesday;
    private transient DoubleProperty wednesday;
    private transient DoubleProperty thursday;
    private transient DoubleProperty friday;

    private transient ChoiceBox<String> doctorOptions;

    public WeeklyClinicReport() {
    }

    public WeeklyClinicReport(LocalDate dateOfCreation, Clinic c, List<DoctorClinic> doctorClinicss) {
        this.dateOfCreation = dateOfCreation;
        this.clinic = c;
        List<String> days = Arrays.asList("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY");
        for(String s: days){
            familyDocVisits.put(s,Integer.valueOf(0));
            pediatricDocVisits.put(s,Integer.valueOf(0));
            professionalDocVisits.put(s,Integer.valueOf(0));
            labVisits.put(s,Integer.valueOf(0));
            nurseVisits.put(s,Integer.valueOf(0));
            covidTestVisits.put(s,Integer.valueOf(0));
            vaccineVisits.put(s,Integer.valueOf(0));
        }
        for(DoctorClinic dc: doctorClinicss){ //init doctorvisits map
            doctorVisitsSunday.put(dc.getDoctor().getName(), Double.valueOf(0.0));
            doctorVisitsMonday.put(dc.getDoctor().getName(), Double.valueOf(0.0));
            doctorVisitsTuesday.put(dc.getDoctor().getName(), Double.valueOf(0.0));
            doctorVisitsWednesday.put(dc.getDoctor().getName(), Double.valueOf(0.0));
            doctorVisitsThursday.put(dc.getDoctor().getName(), Double.valueOf(0.0));
            doctorVisitsFriday.put(dc.getDoctor().getName(), Double.valueOf(0.0));
        }
    }

    public ChoiceBox<String> getDoctorOptions() {
        return doctorOptions;
    }

    public void setDoctorOptions(ChoiceBox<String> doctorOptions) {
        this.doctorOptions = doctorOptions;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public int getId() {
        return id;
    }

    public int getPediatricDocNotArrivedCount() {
        return pediatricDocNotArrivedCount;
    }

    public void setPediatricDocNotArrivedCount(int pediatricDocNotArrivedCount) {
        this.pediatricDocNotArrivedCount = pediatricDocNotArrivedCount;
    }

    public int getFamilyDocNotArrivedCount() {
        return familyDocNotArrivedCount;
    }

    public void setFamilyDocNotArrivedCount(int familyDocNotArrivedCount) {
        this.familyDocNotArrivedCount = familyDocNotArrivedCount;
    }

    public int getProfessionalDocNotArrivedCount() {
        return professionalDocNotArrivedCount;
    }

    public void setProfessionalDocNotArrivedCount(int professionalDocNotArrivedCount) {
        this.professionalDocNotArrivedCount = professionalDocNotArrivedCount;
    }

    public int getNurseNotArrivedCount() {
        return nurseNotArrivedCount;
    }

    public void setNurseNotArrivedCount(int nurseNotArrivedCount) {
        this.nurseNotArrivedCount = nurseNotArrivedCount;
    }

    public int getLabNotArrivedCount() {
        return labNotArrivedCount;
    }

    public void setLabNotArrivedCount(int labNotArrivedCount) {
        this.labNotArrivedCount = labNotArrivedCount;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Map<String, Integer> getFamilyDocVisits() {
        return familyDocVisits;
    }

    public void setFamilyDocVisits(Map<String, Integer> familyDocVisits) {
        this.familyDocVisits = familyDocVisits;
    }

    public Map<String, Integer> getPediatricDocVisits() {
        return pediatricDocVisits;
    }

    public void setPediatricDocVisits(Map<String, Integer> pediatricDocVisits) {
        this.pediatricDocVisits = pediatricDocVisits;
    }

    public Map<String, Integer> getProfessionalDocVisits() {
        return professionalDocVisits;
    }

    public void setProfessionalDocVisits(Map<String, Integer> professionalDocVisits) {
        this.professionalDocVisits = professionalDocVisits;
    }

    public Map<String, Integer> getVaccineVisits() {
        return vaccineVisits;
    }

    public void setVaccineVisits(Map<String, Integer> vaccineVisits) {
        this.vaccineVisits = vaccineVisits;
    }

    public Map<String, Integer> getCovidTestVisits() {
        return covidTestVisits;
    }

    public void setCovidTestVisits(Map<String, Integer> covidTestVisits) {
        this.covidTestVisits = covidTestVisits;
    }

    public Map<String, Integer> getNurseVisits() {
        return nurseVisits;
    }

    public void setNurseVisits(Map<String, Integer> nurseVisits) {
        this.nurseVisits = nurseVisits;
    }

    public Map<String, Integer> getLabVisits() {
        return labVisits;
    }

    public void setLabVisits(Map<String, Integer> labVisits) {
        this.labVisits = labVisits;
    }

    public Map<String, Double> getDoctorVisitsSunday() {
        return doctorVisitsSunday;
    }

    public void setDoctorVisitsSunday(Map<String, Double> doctorVisitsSunday) {
        this.doctorVisitsSunday = doctorVisitsSunday;
    }

    public Map<String, Double> getDoctorVisitsMonday() {
        return doctorVisitsMonday;
    }

    public void setDoctorVisitsMonday(Map<String, Double> doctorVisitsMonday) {
        this.doctorVisitsMonday = doctorVisitsMonday;
    }

    public Map<String, Double> getDoctorVisitsTuesday() {
        return doctorVisitsTuesday;
    }

    public void setDoctorVisitsTuesday(Map<String, Double> doctorVisitsTuesday) {
        this.doctorVisitsTuesday = doctorVisitsTuesday;
    }

    public Map<String, Double> getDoctorVisitsWednesday() {
        return doctorVisitsWednesday;
    }

    public void setDoctorVisitsWednesday(Map<String, Double> doctorVisitsWednesday) {
        this.doctorVisitsWednesday = doctorVisitsWednesday;
    }

    public Map<String, Double> getDoctorVisitsThursday() {
        return doctorVisitsThursday;
    }

    public void setDoctorVisitsThursday(Map<String, Double> doctorVisitsThursday) {
        this.doctorVisitsThursday = doctorVisitsThursday;
    }

    public Map<String, Double> getDoctorVisitsFriday() {
        return doctorVisitsFriday;
    }

    public void setDoctorVisitsFriday(Map<String, Double> doctorVisitsFriday) {
        this.doctorVisitsFriday = doctorVisitsFriday;
    }

    public int getVaccineNotArrivedCount() {
        return vaccineNotArrivedCount;
    }

    public void setVaccineNotArrivedCount(int vaccineNotArrivedCount) {
        this.vaccineNotArrivedCount = vaccineNotArrivedCount;
    }

    public int getCovidTestNotArrivedCount() {
        return covidTestNotArrivedCount;
    }

    public void setCovidTestNotArrivedCount(int covidTestNotArrivedCount) {
        this.covidTestNotArrivedCount = covidTestNotArrivedCount;
    }

    public DoubleProperty sundayProperty() { return sunday; } //properties setters and getters
    public void setSundayProperty(SimpleDoubleProperty s){ this.sunday = s; }
    public Double getSunday() { return sunday.get(); }
    public void setSunday(Double name) { sunday.set(name); }

    public DoubleProperty mondayProperty() { return monday; }
    public void setMondayProperty(SimpleDoubleProperty s){ this.monday = s; }
    public Double getMonday() { return monday.get(); }
    public void setMonday(Double name) { monday.set(name); }

    public DoubleProperty tuesdayProperty() { return tuesday; }
    public void setTuesdayProperty(SimpleDoubleProperty s){ this.tuesday = s; }
    public Double getTuesday() { return tuesday.get(); }
    public void setTuesday(Double name) { tuesday.set(name); }

    public DoubleProperty wednesdayProperty() { return wednesday; }
    public void setWednesdayProperty(SimpleDoubleProperty s){ this.wednesday = s; }
    public Double getWednesday() { return wednesday.get(); }
    public void setWednesday(Double name) { wednesday.set(name); }

    public DoubleProperty thursdayProperty() { return thursday; }
    public void setThursdayProperty(SimpleDoubleProperty s){ this.thursday = s; }
    public Double getThursday() { return thursday.get(); }
    public void setThursday(Double name) { thursday.set(name); }

    public DoubleProperty fridayProperty() { return friday; }
    public void setFridayProperty(SimpleDoubleProperty s){ this.friday = s; }
    public Double getFriday() { return friday.get(); }
    public void setFriday(Double name) { friday.set(name); }
}
