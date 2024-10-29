package hamid.sougouma.human_resource.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 15)
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 15)
    @Column(nullable = false)
    private String lastName;

    @NotNull
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    @Column(nullable = false)
    private String password;

    private String phone;

    private String address;

    private String gender;

    private LocalDate birthday;


    public long getId() {

        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}
