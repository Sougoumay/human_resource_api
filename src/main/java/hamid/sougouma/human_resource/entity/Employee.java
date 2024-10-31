package hamid.sougouma.human_resource.entity;

import com.fasterxml.jackson.annotation.JsonView;
import hamid.sougouma.human_resource.dto.Views;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Employee {

    @JsonView(Views.Resume.class)
    @Id
    @GeneratedValue
    private long id;

    @JsonView(Views.Resume.class)
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 15)
    @Column(nullable = false)
    private String firstName;

    @JsonView(Views.Resume.class)
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 15)
    @Column(nullable = false)
    private String lastName;

    @JsonView(Views.Resume.class)
    @NotNull
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @JsonView(Views.Resume.class)
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(nullable = false)
    private String password;

    @JsonView(Views.Resume.class)
    private String phone;

    @JsonView(Views.Resume.class)
    private String address;

    @JsonView(Views.Resume.class)
    private String gender;

    @JsonView(Views.Resume.class)
    private LocalDate birthday;

    @JsonView(Views.Resume.class)
    @OneToOne(cascade = CascadeType.ALL)
    private Role role;

    @JsonView(Views.Complet.class)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Set<EmployeeSkill> skills;


    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", Skills=" + skills +
                '}';
    }
}
