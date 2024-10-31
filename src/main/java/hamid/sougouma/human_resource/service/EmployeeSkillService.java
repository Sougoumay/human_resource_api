package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.Skill;

import java.util.Set;

public interface EmployeeSkillService {

    Set<Skill> addEmployeeSkills(Employee employee, Set<Skill> skills);

    Set<Skill> addEmployeeSkill(Employee employee, Skill skill);

    Set<Skill> getEmployeeSkills(Employee employee);

    Set<Employee> getSkillEmployeess(Skill skill);

    void removeEmployeeSkills(Employee employee, Skill skill);
}
