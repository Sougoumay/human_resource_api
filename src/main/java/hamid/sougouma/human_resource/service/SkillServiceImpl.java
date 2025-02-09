package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.dao.SkillRepository;
import hamid.sougouma.human_resource.dto.EmployeeDTO;
import hamid.sougouma.human_resource.dto.SkillDTO;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.EmployeeSkill;
import hamid.sougouma.human_resource.entity.Skill;
import hamid.sougouma.human_resource.enums.SkillLevelEnum;
import hamid.sougouma.human_resource.exception.SkillAlreadyExistException;
import hamid.sougouma.human_resource.exception.SkillLevelNotFoundException;
import hamid.sougouma.human_resource.exception.SkillNotAuthorizedToDeleteException;
import hamid.sougouma.human_resource.exception.SkillNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    private EmployeeService employeeService;

//    @Autowired
    @Override
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PersistenceContext
    private EntityManager em;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public List<SkillDTO> getSkills() {
        List<SkillDTO> skills = new ArrayList<>();
        for (Skill skill : skillRepository.findAll()) {
            SkillDTO skillDTO = new SkillDTO(skill.getId(),skill.getName(),skill.getLevel().name());
            skills.add(skillDTO);
        }

        return skills;
    }

    @Override
    public SkillDTO getSkill(int id) throws SkillNotFoundException {
        Skill skill = skillRepository.findById(id).orElseThrow(() -> new SkillNotFoundException(id));
        Set<EmployeeDTO> employees = new HashSet<>();
        List<Employee> employeesList = em.createQuery("select e From Employee e left join e.skills es left join es.skill s where s.id=:skillId", Employee.class)
                .setParameter("skillId", skill.getId()).getResultList();
        employeesList.forEach(e -> employees.add(employeeService.getEmployeeDTOFromEmployee(e)));

        return new SkillDTO(id,skill.getName(),skill.getLevel().name(), employees);
    }

    @Override
    public Skill getSkillByNameAndLevel(String name, SkillLevelEnum level) throws SkillNotFoundException {
        return skillRepository.findByNameAndLevel(name,level).orElseThrow(() -> new SkillNotFoundException(name));
    }

    @Override
    public Skill addSkill(SkillDTO dto) throws SkillAlreadyExistException, SkillLevelNotFoundException {

        SkillLevelEnum level = this.getLevelEnum(dto.getLevel().toUpperCase());

        Optional<Skill> optionalSkill = skillRepository.findByNameAndLevel(dto.getName(), level);

        if (optionalSkill.isPresent()) {
            throw new SkillAlreadyExistException(dto.getName(), level);
        }

        Skill skill = new Skill(dto.getName(), level);

        return skillRepository.save(skill);
    }


    @Override
    public Skill updateSkill(SkillDTO dto) throws SkillAlreadyExistException, SkillLevelNotFoundException, SkillNotFoundException {

        SkillLevelEnum levelEnum = this.getLevelEnum(dto.getLevel().toUpperCase());

        Optional<Skill> optionalSkill = skillRepository.findByNameAndLevel(dto.getName(), levelEnum);

        if (optionalSkill.isPresent()) {
            throw new SkillAlreadyExistException(dto.getName(), levelEnum);
        }

        Skill skill = skillRepository.findById(dto.getId()).orElseThrow(() -> new SkillNotFoundException(dto.getId()));
        skill.setName(dto.getName());
        skill.setLevel(levelEnum);
        return skillRepository.save(skill);
    }

    @Override
    public void deleteSkill(int id) throws SkillNotFoundException, SkillNotAuthorizedToDeleteException {
        Skill skill = skillRepository.findById(id).orElseThrow(() -> new SkillNotFoundException(id));
        if (skill.getEmployees().size() > 0) {
            System.out.println(skill.getEmployees().size());
            throw new SkillNotAuthorizedToDeleteException(skill.getId());
        }

        skillRepository.delete(skill);
    }

    @Override
    public Set<Skill> addAllSkills(Set<Skill> skills) {
        Set<Skill> skillsToReturn = new HashSet<>();

        for (Skill skill : skills) {
            Optional<Skill> skillFromDB = skill.getId() == 0 ?
                    skillRepository.findByNameAndLevel(skill.getName(), skill.getLevel()) :
                    skillRepository.findByIdOrNameAndLevel(skill.getId(), skill.getName(), skill.getLevel());

            if (skillFromDB.isPresent()) {
                skillsToReturn.add(skillFromDB.get());
            } else {
                skillsToReturn.add(skillRepository.save(skill));
            }
        }

        return skillsToReturn;
    }

    @Override
    public Set<SkillDTO> getSkillDTOs(Set<Skill> skills) {
        Set<SkillDTO> skillDTOs = new HashSet<>();
        for (Skill skill : skills) {
            skillDTOs.add(new SkillDTO(skill.getId(),skill.getName(),skill.getLevel().name()));
        }
        return skillDTOs;
    }

    private SkillLevelEnum getLevelEnum(String level) throws SkillLevelNotFoundException {
        return switch (level) {
            case "JUNIOR" -> SkillLevelEnum.JUNIOR;
            case "SENIOR" -> SkillLevelEnum.SENIOR;
            case "EXPERT" -> SkillLevelEnum.EXPERT;
            default -> throw new SkillLevelNotFoundException(level);
        };
    }

}
