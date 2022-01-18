package il.ac.haifa.ClinicSystem.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int id;

    @Column(name = "username")
    protected String username;
    private String name;
    protected byte[] password;
    protected byte[] salt;


    private String email;
    public  String getEmail(){
        return this.email;
    }




    public User() {

    }

    public User(String username, String password , String email, String name) {
        this.username = username;
        this.email = email;
        this.name = name;

        //generating SecureRandom object in order to create salt for a good password hashing
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        this.salt = salt;

        //using SHA-512 in order to hash the password
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(salt);

        //hashing the password and updating it
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        this.password = hashedPassword;
    }

    public User(User u){
        this.username = u.getUsername();
        this.salt = u.getSalt().clone();
        this.name = u.getName();
        this.password = u.getPassword().clone();
        this.id = u.getId();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getSalt() {
        return salt;
    }

    public int getId() {
        return id;
    }

    public byte[] getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setPassword(String password) {
        //same as in constructor function
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        this.salt = salt;

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(salt);

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        this.password = hashedPassword;
    }
}
