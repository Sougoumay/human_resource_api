package hamid.sougouma.human_resource.controller;

import hamid.sougouma.human_resource.entity.Skill;
import hamid.sougouma.human_resource.exception.SkillNotFoundException;
import hamid.sougouma.human_resource.service.SkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public ResponseEntity<List<Skill>> getSkills() {

        List<Skill> skills = skillService.getSkills();

        return ResponseEntity.ok(skills);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkill(@PathVariable int id) throws SkillNotFoundException {
        Skill skill = skillService.getSkill(id);
        return new ResponseEntity<>(skill, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        return null;
    }

    @PutMapping
    public ResponseEntity<Skill> updateSkill(@RequestBody Skill skill) {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSkill(@RequestBody Skill skill) {
        return null;
    }


}
