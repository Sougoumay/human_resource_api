package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.dao.SkillRepository;
import hamid.sougouma.human_resource.entity.Skill;
import hamid.sougouma.human_resource.exception.SkillAlreadyExistException;
import hamid.sougouma.human_resource.exception.SkillNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }

    @Override
    public Skill getSkill(int id) throws SkillNotFoundException {
        return skillRepository.findById(id).orElseThrow(() -> new SkillNotFoundException(id));
    }

    @Override
    public Skill getSkillByName(String name) throws SkillNotFoundException {
        return skillRepository.findByName(name).orElseThrow(() -> new SkillNotFoundException(name));
    }

    @Override
    public Skill addSkill(Skill skill) throws SkillAlreadyExistException {

        Optional<Skill> optionalSkill = skillRepository.findByNameAndLevel(skill.getName(), skill.getLevel());

        if (optionalSkill.isPresent()) {
            throw new SkillAlreadyExistException(skill.getName(), skill.getLevel());
        }

        return skillRepository.save(skill);
    }


    @Override
    public Skill updateSkill(Skill skill) throws SkillAlreadyExistException {

        Optional<Skill> optionalSkill = skillRepository.findByNameAndLevel(skill.getName(), skill.getLevel());

        if (optionalSkill.isPresent()) {
            throw new SkillAlreadyExistException(skill.getName(), skill.getLevel());
        }

        return skillRepository.save(skill);
    }

    @Override
    public void deleteSkill(int id) throws SkillNotFoundException {
        Skill skill = this.getSkill(id);
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
}
