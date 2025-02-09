package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.dao.EmployeeRepository;
import hamid.sougouma.human_resource.dao.EmployeeSkillRepository;
import hamid.sougouma.human_resource.dao.SkillRepository;
import hamid.sougouma.human_resource.dto.EmployeeDTO;
import hamid.sougouma.human_resource.dto.SkillDTO;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.EmployeeSkill;
import hamid.sougouma.human_resource.entity.Skill;
import hamid.sougouma.human_resource.exception.EmployeeNotFoundException;
import hamid.sougouma.human_resource.exception.SkillNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeSkillServiceImpl implements EmployeeSkillService {

    private final EmployeeSkillRepository employeeSkillRepository;
    private final EmployeeRepository employeeRepository;
    private final SkillRepository skillRepository;

    @PersistenceContext
    private EntityManager em;

    public EmployeeSkillServiceImpl(EmployeeSkillRepository employeeSkillRepository, EmployeeRepository employeeRepository, SkillRepository skillRepository) {
        this.employeeSkillRepository = employeeSkillRepository;
        this.employeeRepository = employeeRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    @Transactional
    public Set<SkillDTO> addEmployeeSkills(EmployeeDTO employee, Set<SkillDTO> skills) throws EmployeeNotFoundException, SkillNotFoundException {

        for (SkillDTO skillDTO : skills) {
            addEmployeeSkill(employee,skillDTO);
        }

        return getEmployeeSkills(employee.getId());
    }

    public void addEmployeeSkill(EmployeeDTO employee, SkillDTO skill) throws EmployeeNotFoundException, SkillNotFoundException {

        Employee employeeEntity = employeeRepository.findById(employee.getId()).orElseThrow(() -> new EmployeeNotFoundException(employee.getId()));
        Skill skillEntity = skillRepository.findById(skill.getId()).orElseThrow(() -> new SkillNotFoundException(skill.getId()));
        EmployeeSkill es = employeeSkillRepository.findBySkillAndEmployee(skillEntity, employeeEntity);
        if (es == null) {
            employeeSkillRepository.save(new EmployeeSkill(employeeEntity,skillEntity));
        }
    }

    @Override
    public Set<SkillDTO> getEmployeeSkills(long employeeId) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        List<Skill> skills = em.createQuery("select es.skill from EmployeeSkill es where es.employee.id=:id ", Skill.class)
                .setParameter("id", employee.getId()).getResultList();

        Set<SkillDTO> skillDTOs = new HashSet<>();
        for (Skill skill : skills) {
            skillDTOs.add(new SkillDTO(skill.getId(),skill.getName(),skill.getLevel().name()));
        }

        return skillDTOs;
    }

//    @Override
//    public Set<EmployeeDTO> getSkillEmployees(Skill skill) {
//        Set<EmployeeSkill> employeeSkills = employeeSkillRepository.findBySkill(skill);
//        Set<Employee> employees = new HashSet<>();
//        employeeSkills.forEach(e -> employees.add(e.getEmployee()));
//        return null;
//    }

    @Override
    public void removeEmployeeSkills(long employeeId, int skillId) throws SkillNotFoundException, EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new SkillNotFoundException(skillId));
        EmployeeSkill employeeSkill = employeeSkillRepository.findBySkillAndEmployee(skill, employee);
        if (employeeSkill != null) employeeSkillRepository.delete(employeeSkill);
    }
}
