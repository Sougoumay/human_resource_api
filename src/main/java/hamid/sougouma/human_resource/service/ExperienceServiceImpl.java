package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.dao.ExperienceRepository;
import hamid.sougouma.human_resource.entity.Experience;
import hamid.sougouma.human_resource.exception.ExperienceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    @Override
    public List<Experience> getUserExperiences(Long id) {
        return experienceRepository.findByEmployeeId(id);
    }

    @Override
    public Experience getExperience(Long id) throws ExperienceNotFoundException {
        return experienceRepository.findById(id).orElseThrow(() -> new ExperienceNotFoundException(id));
    }

    @Override
    public Experience addExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    @Override
    public Experience updateExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    @Override
    public void deleteExperience(Experience experience) {
        experienceRepository.delete(experience);
    }
}
