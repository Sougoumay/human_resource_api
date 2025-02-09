package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.dto.SkillDTO;
import hamid.sougouma.human_resource.entity.Skill;
import hamid.sougouma.human_resource.enums.SkillLevelEnum;
import hamid.sougouma.human_resource.exception.SkillAlreadyExistException;
import hamid.sougouma.human_resource.exception.SkillLevelNotFoundException;
import hamid.sougouma.human_resource.exception.SkillNotAuthorizedToDeleteException;
import hamid.sougouma.human_resource.exception.SkillNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public interface SkillService {

    SkillDTO getSkill(int id) throws SkillNotFoundException;

    Skill getSkillByNameAndLevel(String name, SkillLevelEnum level) throws SkillNotFoundException;

    Skill addSkill(SkillDTO skill) throws SkillAlreadyExistException, SkillLevelNotFoundException;

    @Autowired
    void setEmployeeService(EmployeeService employeeService);

    List<SkillDTO> getSkills();

    Skill updateSkill(SkillDTO skill) throws SkillAlreadyExistException, SkillLevelNotFoundException, SkillNotFoundException;

    void deleteSkill(int id) throws SkillNotFoundException, SkillNotAuthorizedToDeleteException;

    Set<Skill> addAllSkills(Set<Skill> skills);

    Set<SkillDTO> getSkillDTOs(Set<Skill> skills);
}
