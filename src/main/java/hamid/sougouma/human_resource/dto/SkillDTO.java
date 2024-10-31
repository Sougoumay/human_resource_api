package hamid.sougouma.human_resource.dto;

import com.fasterxml.jackson.annotation.JsonView;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.enums.SkillLevelEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
public class SkillDTO {

    public SkillDTO(int id, String name, Set<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    @JsonView(Views.Resume.class)
    private int id;

    @JsonView(Views.Resume.class)
    private String name;

    @JsonView(Views.Complet.class)
    private Set<Employee> employees;

}
