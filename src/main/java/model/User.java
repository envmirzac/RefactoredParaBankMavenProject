package model;
import javax.persistence.*;

// The @Entity annotation indicates that this class is an entity and is mapped to a database table.
// The @Table annotation specifies the table's name in the database that this entity is mapped to.
@Entity
@Table(name = "users")
public class User {

    // The @Id annotation specifies the primary key of the table.
    @Id
    // The @GeneratedValue annotation indicates that the ID should be generated automatically.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // The @Column annotation specifies the column details.
    @Column(name = "id")
    private Long id;

    // This specifies the column details for the username. It should be unique and not nullable.
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    // This specifies the column details for the password. It should not be nullable.
    @Column(name = "password", nullable = false)
    private String password;

    // Default constructor required by Hibernate.
    public User() {
    }

    // Constructor with parameters for creating a new user object.
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter method for ID.
    public Long getId() {
        return id;
    }

    // Setter method for ID.
    public void setId(Long id) {
        this.id = id;
    }

    // Getter method for username.
    public String getUsername() {
        return username;
    }

    // Setter method for username.
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter method for password.
    public String getPassword() {
        return password;
    }

    // Setter method for password.
    public void setPassword(String password) {
        this.password = password;
    }

    // Override the toString() method to print user details in a formatted manner.
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
