package hamid.sougouma.human_resource.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Set<EmployeeSkill> skills;


    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
