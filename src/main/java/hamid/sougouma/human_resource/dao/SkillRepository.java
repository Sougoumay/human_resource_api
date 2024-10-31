package hamid.sougouma.human_resource.dao;

import hamid.sougouma.human_resource.entity.Skill;
import hamid.sougouma.human_resource.enums.SkillLevelEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    Optional<Skill> findByName(String name);

    Optional<Skill> findByNameAndLevel(String name, SkillLevelEnum level);

    Optional<Skill> findByIdOrNameAndLevel(int id, String name, SkillLevelEnum level);
}
