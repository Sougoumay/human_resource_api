package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.dao.ExperienceRepository;
import hamid.sougouma.human_resource.dto.ExperienceDTO;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.Experience;
import hamid.sougouma.human_resource.exception.EmployeeNotFoundException;
import hamid.sougouma.human_resource.exception.ExperienceNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    @PersistenceContext
    private EntityManager em;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    @Override
    public List<ExperienceDTO> getUserExperiences(long id) {
        List<Experience> experiences = experienceRepository.findByEmployeeId(id);
        return getExperienceDTOsFromExperiences(experiences);
    }

    @Override
    @Transactional(readOnly = true)
    public ExperienceDTO getExperience(long employeeId, long experienceId) throws ExperienceNotFoundException, EmployeeNotFoundException {
        Experience experience = em.find(Experience.class, experienceId);
        Employee employee = em.find(Employee.class, employeeId);
        if (employee == null ) {
            throw new EmployeeNotFoundException(employeeId);
        } else if (experience == null) {
            throw new ExperienceNotFoundException(experienceId);
        } else if (experience.getEmployee() != null && experience.getEmployee().getId() != employee.getId()) {
            throw new ExperienceNotFoundException(experienceId);
        }
        return getExperienceDTOFromExperience(experience);
    }

    @Override
    @Transactional
    public ExperienceDTO addExperience(long employeeId ,ExperienceDTO dto) throws EmployeeNotFoundException {
        Employee employee = em.find(Employee.class, employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException(employeeId);
        }
        Experience experience = getExperienceFromDTO(dto);
        experience.setEmployee(employee);
        experience = experienceRepository.save(experience);
        dto.setId(experience.getId());
        return dto;
    }

    @Override
    @Transactional
    public ExperienceDTO updateExperience(long employeeId ,ExperienceDTO experienceDTO) throws ExperienceNotFoundException, EmployeeNotFoundException {

        Experience experience = experienceRepository.findById(experienceDTO.getId())
                .orElseThrow(() -> new ExperienceNotFoundException(experienceDTO.getId()));

        if (experience.getEmployee() == null) {
            throw new EmployeeNotFoundException(employeeId);
        } else if (experience.getEmployee().getId() != employeeId) {
            throw new EmployeeNotFoundException(employeeId);
        }
        experience = getExperienceToUpdateFromExperienceDTO(experienceDTO,experience);
        experienceRepository.save(experience);
        return experienceDTO;
    }

    @Override
    @Transactional
    public void deleteExperience(long employeeId, long experienceId) throws ExperienceNotFoundException, EmployeeNotFoundException {
        Experience experience = experienceRepository.findById(experienceId).orElseThrow(() -> new ExperienceNotFoundException(experienceId));
        Employee employee = em.find(Employee.class, employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException(employeeId);
        } else if (experience.getEmployee() == null) {
            throw new EmployeeNotFoundException(employeeId);
        } else if (experience.getEmployee().getId() != employeeId) {
            throw new EmployeeNotFoundException(employeeId);
        }
        experienceRepository.delete(experience);
    }

    public Experience getExperienceFromDTO(ExperienceDTO dto) {
        Experience experience = new Experience();
        experience.setId(dto.getId());
        experience.setCompany(dto.getCompany());
        experience.setDescription(dto.getDescription());
        experience.setTitle(dto.getTitle());
        experience.setEndDate(LocalDate.parse(dto.getEndDate()));
        experience.setStartDate(LocalDate.parse(dto.getStartDate()));
        return experience;
    }

    public ExperienceDTO getExperienceDTOFromExperience(Experience experience) {
        ExperienceDTO experienceDTO = new ExperienceDTO();
        experienceDTO.setId(experience.getId());
        experienceDTO.setCompany(experience.getCompany());
        experienceDTO.setDescription(experience.getDescription());
        experienceDTO.setTitle(experience.getTitle());
        experienceDTO.setEndDate(experience.getEndDate().toString());
        experienceDTO.setStartDate(experience.getStartDate().toString());
        return experienceDTO;
    }

    public List<ExperienceDTO> getExperienceDTOsFromExperiences(List<Experience> experiences) {
        List<ExperienceDTO> experienceDTOs = new ArrayList<>();
        for (Experience e : experiences) {
            experienceDTOs.add(getExperienceDTOFromExperience(e));
        }
        return experienceDTOs;
    }

    public Experience getExperienceToUpdateFromExperienceDTO(ExperienceDTO dto, Experience experience) {
        experience.setCompany(dto.getCompany());
        experience.setDescription(dto.getDescription());
        experience.setTitle(dto.getTitle());
        experience.setEndDate(LocalDate.parse(dto.getEndDate()));
        experience.setStartDate(LocalDate.parse(dto.getStartDate()));
        return experience;
    }
}
