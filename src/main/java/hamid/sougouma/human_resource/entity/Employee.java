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

    @OneToOne(cascade = CascadeType.ALL)
    private Role role;


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

    public Role getRole() {
        return role;
    }

    public void setFirstName(@NotNull @NotBlank @NotEmpty @Size(min = 2, max = 15) String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@NotNull @NotBlank @NotEmpty @Size(min = 2, max = 15) String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public void setPassword(@NotNull @NotBlank @NotEmpty String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
