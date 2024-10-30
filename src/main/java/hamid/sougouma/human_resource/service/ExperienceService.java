package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.dto.ExperienceDTO;
import hamid.sougouma.human_resource.entity.Experience;
import hamid.sougouma.human_resource.exception.ExperienceNotFoundException;

import java.util.List;

public interface ExperienceService {

    List<Experience> getUserExperiences(Long id);

    Experience getExperience(Long id) throws ExperienceNotFoundException;

    Experience addExperience(Experience experience);

    Experience updateExperience(Experience experience);

    void deleteExperience(Experience experience) throws ExperienceNotFoundException;

    Experience getExperienceFromDTO(ExperienceDTO dto);

}
