package hamid.sougouma.human_resource.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employee_skill", uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id","skill_id"}, name = "unique_employee_skill")})
public class EmployeeSkill {

    @Id @GeneratedValue
    private long id;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Skill skill;

    public EmployeeSkill() {}

    public EmployeeSkill(Employee employee, Skill skill) {
        this.employee = employee;
        this.skill = skill;
    }


}
