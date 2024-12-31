package hamid.sougouma.human_resource.dto;

import hamid.sougouma.human_resource.entity.Skill;

import java.util.Set;

public record SetKillsToEmployeeRecord(Set<SkillDTO> skills) {
}
