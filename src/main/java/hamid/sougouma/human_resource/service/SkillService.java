package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.entity.Skill;
import hamid.sougouma.human_resource.exception.SkillAlreadyExistException;
import hamid.sougouma.human_resource.exception.SkillNotFoundException;

import java.util.List;

public interface SkillService {

    Skill getSkill(int id) throws SkillNotFoundException;

    Skill getSkillByName(String name) throws SkillNotFoundException;

    Skill addSkill(Skill skill) throws SkillAlreadyExistException;

    List<Skill> getSkills();

    Skill updateSkill(Skill skill) throws SkillAlreadyExistException;

    void deleteSkill(int id) throws SkillNotFoundException;
}
