package entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.UUID;



@Entity
@XmlRootElement

@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),

})
@Table(name = "User_")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="email", nullable=false, unique = true)
    private String email;

    @Column(name="name", nullable=false, length=30)
    private String name;

    @Column(name="lastName", nullable=false, length=30)
    private String lastName;

    @Column(name="password", nullable=false, length=64)
    private String password;

    @Column(name="authToken", unique = true)
    private String authToken;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public User() {

    }

    public User(String name, String lastName, String password, String email) {
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.authToken = null;
    }

    public static String reallyBadTokenGen() {
        return UUID.randomUUID().toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}