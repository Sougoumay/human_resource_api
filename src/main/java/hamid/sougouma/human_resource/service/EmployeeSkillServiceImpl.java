package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.dao.EmployeeSkillRepository;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.EmployeeSkill;
import hamid.sougouma.human_resource.entity.Skill;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class EmployeeSkillServiceImpl implements EmployeeSkillService {

    private final EmployeeSkillRepository employeeSkillRepository;

    public EmployeeSkillServiceImpl(EmployeeSkillRepository employeeSkillRepository) {
        this.employeeSkillRepository = employeeSkillRepository;
    }

    @Override
    public Set<Skill> addEmployeeSkills(Employee employee, Set<Skill> skills) {

        skills.forEach(skill -> {
            EmployeeSkill employeeSkill = employeeSkillRepository.findBySkillAndEmployee(skill, employee);
            if (employeeSkill == null) {
                employeeSkillRepository.save(new EmployeeSkill(employee,skill));
            }
        });

        return getEmployeeSkills(employee);
    }

    @Override
    public Set<Skill> addEmployeeSkill(Employee employee, Skill skill) {
        employeeSkillRepository.save(new EmployeeSkill(employee,skill));

        return getEmployeeSkills(employee);
    }

    @Override
    public Set<Skill> getEmployeeSkills(Employee employee) {
        Set<EmployeeSkill> employeeSkills = employeeSkillRepository.findByEmployee(employee);

        Set<Skill> skills = new HashSet<>();
        employeeSkills.forEach(e -> skills.add(e.getSkill()));

        return skills;
    }
}
