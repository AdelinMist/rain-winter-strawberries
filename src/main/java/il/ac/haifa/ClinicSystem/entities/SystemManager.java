package il.ac.haifa.ClinicSystem.entities;

import javax.persistence.*;

@Entity
@Table(name = "system_manager")
public final class SystemManager extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="system_manager_id")
    private int id;

    private static SystemManager INSTANCE;
    //private String info = "Initial info class";

    private SystemManager() {
    }

    private SystemManager(String username, String password, String name, String email) {
        super(username, password , email, name);
    }

    public synchronized static SystemManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new SystemManager();
        }

        return INSTANCE;
    }

    public synchronized static SystemManager getInstance(String username, String password, String name, String email) {
        if(INSTANCE == null) {
            INSTANCE = new SystemManager(username, password, name, email);
        }

        return INSTANCE;
    }

    public int getId() {
        return this.id;
    }
}
