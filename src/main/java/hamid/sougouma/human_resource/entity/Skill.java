package hamid.sougouma.human_resource.entity;

import com.fasterxml.jackson.annotation.JsonView;
import hamid.sougouma.human_resource.dto.Views;
import hamid.sougouma.human_resource.enums.SkillLevelEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"level","name"}, name = "unique_level_name_couple")})
public class Skill {

    @JsonView(Views.Resume.class)
    @Id
    @GeneratedValue
    private int id;

    @JsonView(Views.Resume.class)
    @Column(nullable = false)
    private String name;

    @JsonView(Views.Resume.class)
    @Column(name = "level", nullable = false)
    @Enumerated(EnumType.STRING)
    private SkillLevelEnum level;

    @JsonView(Views.Complet.class)
    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
    private Set<EmployeeSkill> employees;
}
