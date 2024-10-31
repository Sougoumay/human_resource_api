package hamid.sougouma.human_resource.entity;

import hamid.sougouma.human_resource.enums.SkillLevelEnum;
import jakarta.persistence.*;
import lombok.Data;


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
}
