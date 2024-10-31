package hamid.sougouma.human_resource.controller;

import hamid.sougouma.human_resource.entity.Skill;
import hamid.sougouma.human_resource.exception.SkillAlreadyExistException;
import hamid.sougouma.human_resource.exception.SkillNotFoundException;
import hamid.sougouma.human_resource.service.SkillService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill, UriComponentsBuilder ucBuilder) throws SkillAlreadyExistException {
        skill = skillService.addSkill(skill);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                ucBuilder
                        .path("/skills/{id}")
                        .buildAndExpand(skill.getId())
                        .toUri()
        );

        return new ResponseEntity<>(skill, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@RequestBody Skill skill, @PathVariable int id) throws SkillAlreadyExistException, SkillNotFoundException {

        if (skill.getId() == id) {
            skillService.getSkill(id);
            Skill updatedSkill = skillService.updateSkill(skill);

            return new ResponseEntity<>(updatedSkill, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);




    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable int id) {

        try {
            skillService.deleteSkill(id);
        } catch (SkillNotFoundException e) {
            System.out.println("Someone tried to delete a skill that doesn't exist. The given Id is " + id);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
