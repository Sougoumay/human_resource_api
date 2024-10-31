package hamid.sougouma.human_resource.controller;

import com.fasterxml.jackson.annotation.JsonView;
import hamid.sougouma.human_resource.dao.SkillRepository;
import hamid.sougouma.human_resource.dto.SkillDTO;
import hamid.sougouma.human_resource.dto.Views;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.Skill;
import hamid.sougouma.human_resource.exception.SkillAlreadyExistException;
import hamid.sougouma.human_resource.exception.SkillNotFoundException;
import hamid.sougouma.human_resource.service.EmployeeSkillService;
import hamid.sougouma.human_resource.service.SkillService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/skills")
public class SkillController {

    private final SkillService skillService;
    private final EmployeeSkillService employeeSkillService;

    public SkillController(SkillService skillService, EmployeeSkillService employeeSkillService) {
        this.skillService = skillService;
        this.employeeSkillService = employeeSkillService;
    }

    @JsonView(Views.Resume.class)
    @GetMapping
    public ResponseEntity<List<Skill>> getSkills() {

        List<Skill> skills = skillService.getSkills();

        return ResponseEntity.ok(skills);

    }

    @JsonView(Views.Complet.class)
    @GetMapping("/{id}")
    public ResponseEntity<SkillDTO> getSkill(@PathVariable int id) throws SkillNotFoundException {
        Skill skill = skillService.getSkill(id);
        Set<Employee> employees = employeeSkillService.getSkillEmployeess(skill);
        return new ResponseEntity<>(new SkillDTO(skill.getId(), skill.getName(), employees), HttpStatus.OK);
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
