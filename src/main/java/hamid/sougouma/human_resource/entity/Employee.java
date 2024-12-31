package hamid.sougouma.human_resource.entity;

import hamid.sougouma.human_resource.dto.EmployeeDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Employee {

    public Employee() {}

    public Employee(EmployeeDTO dto, User user) {
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.phone = dto.getPhone();
        this.address = dto.getAddress();
        this.gender = dto.getGender();
        this.birthday = LocalDate.parse(dto.getBirthday());
        this.hireDate = LocalDate.parse(dto.getHireDate());
        this.user = user;
    }

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

    private String phone;

    private String address;

    private String gender;

    private LocalDate birthday;

    private LocalDate hireDate;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "employee")
    private Set<EmployeeSkill> skills;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Poste poste;

    @OneToMany(mappedBy = "employee")
    private List<Experience> experiences;



    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", Skills=" + skills +
                '}';
    }
}
