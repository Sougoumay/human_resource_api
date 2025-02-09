package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.dto.EmployeeDTO;
import hamid.sougouma.human_resource.dto.SkillDTO;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.Skill;
import hamid.sougouma.human_resource.exception.EmployeeNotFoundException;
import hamid.sougouma.human_resource.exception.SkillNotFoundException;

import java.util.Set;

public interface EmployeeSkillService {

    Set<SkillDTO> addEmployeeSkills(EmployeeDTO employee, Set<SkillDTO> skills) throws EmployeeNotFoundException, SkillNotFoundException;

//    Set<SkillDTO> addEmployeeSkill(EmployeeDTO employee, SkillDTO skill) throws EmployeeNotFoundException, SkillNotFoundException;

    Set<SkillDTO> getEmployeeSkills(long employeeId) throws EmployeeNotFoundException;

//    Set<EmployeeDTO> getSkillEmployees(Skill skill);

    void removeEmployeeSkills(long employeeId, int skillId) throws SkillNotFoundException, EmployeeNotFoundException;
}
