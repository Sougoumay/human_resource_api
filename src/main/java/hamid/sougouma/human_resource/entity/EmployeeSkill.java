package hamid.sougouma.human_resource.entity;

import com.fasterxml.jackson.annotation.JsonView;
import hamid.sougouma.human_resource.dto.Views;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employee_skill", uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id","skill_id"}, name = "unique_employee_skill")})
public class EmployeeSkill {

    @JsonView(Views.Resume.class)
    @Id @GeneratedValue
    private long id;

    @JsonView(Views.Resume.class)
    @ManyToOne
    private Employee employee;

    @JsonView(Views.Resume.class)
    @ManyToOne
    private Skill skill;

    public EmployeeSkill() {}

    public EmployeeSkill(Employee employee, Skill skill) {
        this.employee = employee;
        this.skill = skill;
    }


}
