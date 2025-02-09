package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.dto.ExperienceDTO;
import hamid.sougouma.human_resource.entity.Experience;
import hamid.sougouma.human_resource.exception.EmployeeNotFoundException;
import hamid.sougouma.human_resource.exception.ExperienceNotFoundException;

import java.util.List;

public interface ExperienceService {

    List<ExperienceDTO> getUserExperiences(long id);

    ExperienceDTO getExperience(long employeeId, long experienceId) throws ExperienceNotFoundException, EmployeeNotFoundException;

    ExperienceDTO addExperience(long employeeId ,ExperienceDTO dto) throws EmployeeNotFoundException;

    ExperienceDTO updateExperience(long employeeId, ExperienceDTO experience) throws ExperienceNotFoundException, EmployeeNotFoundException;

    void deleteExperience(long employeeId, long experienceId) throws ExperienceNotFoundException, EmployeeNotFoundException;

}
