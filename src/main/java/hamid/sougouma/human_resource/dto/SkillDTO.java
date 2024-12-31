package hamid.sougouma.human_resource.dto;

import com.fasterxml.jackson.annotation.JsonView;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.enums.SkillLevelEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
public class SkillDTO {

    public SkillDTO() {}

    public SkillDTO(int id, String name, String level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }

    public SkillDTO(int id, String name, String level, Set<EmployeeDTO> employees) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.employees = employees;
    }

    private int id;

    private String name;

    private String level;

    private Set<EmployeeDTO> employees;

}
