package hamid.sougouma.human_resource.dao;

import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.EmployeeSkill;
import hamid.sougouma.human_resource.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Set;

public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Long> {


    Set<EmployeeSkill> findBySkill(Skill skill);

    Set<EmployeeSkill> findByEmployee(Employee employee);

    EmployeeSkill findBySkillAndEmployee(Skill skill, Employee employee);

}
