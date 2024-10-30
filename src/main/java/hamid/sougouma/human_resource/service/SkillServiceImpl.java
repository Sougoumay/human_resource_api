package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.dao.SkillRepository;
import hamid.sougouma.human_resource.entity.Skill;
import hamid.sougouma.human_resource.exception.SkillNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Transactional
    @Override
    public Skill addSkill(Skill skill) {
        return skillRepository.save(skill);
    }


    @Transactional
    @Override
    public Skill updateSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    @Transactional
    @Override
    public void deleteSkill(int id) throws SkillNotFoundException {
        Skill skill = this.getSkill(id);
        skillRepository.delete(skill);
    }
}
