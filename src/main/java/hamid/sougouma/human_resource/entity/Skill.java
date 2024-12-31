package hamid.sougouma.human_resource.entity;

import hamid.sougouma.human_resource.enums.SkillLevelEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"level","name"}, name = "unique_level_name_couple")})
public class Skill {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(name = "level", nullable = false)
    @Enumerated(EnumType.STRING)
    private SkillLevelEnum level;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
    private Set<EmployeeSkill> employees;

    public Skill() {}

    public Skill(String name, SkillLevelEnum level) {
        this.name = name;
        this.level = level;
    }
}
