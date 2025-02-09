package hamid.sougouma.human_resource.controller;

import hamid.sougouma.human_resource.dao.SkillRepository;
import hamid.sougouma.human_resource.dto.SkillDTO;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.Skill;
import hamid.sougouma.human_resource.exception.SkillAlreadyExistException;
import hamid.sougouma.human_resource.exception.SkillLevelNotFoundException;
import hamid.sougouma.human_resource.exception.SkillNotAuthorizedToDeleteException;
import hamid.sougouma.human_resource.exception.SkillNotFoundException;
import hamid.sougouma.human_resource.service.EmployeeService;
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
    private final EmployeeService employeeService;

    public SkillController(SkillService skillService, EmployeeService employeeService) {
        this.skillService = skillService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<SkillDTO>> getSkills() {

        List<SkillDTO> skills = skillService.getSkills();

        return ResponseEntity.ok(skills);

    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillDTO> getSkill(@PathVariable int id) throws SkillNotFoundException {
        skillService.setEmployeeService(employeeService);
        SkillDTO skill = skillService.getSkill(id);
        return new ResponseEntity<>(skill, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SkillDTO> createSkill(@RequestBody SkillDTO skill, UriComponentsBuilder ucBuilder) throws SkillAlreadyExistException, SkillLevelNotFoundException {
        Skill skill1 = skillService.addSkill(skill);
        SkillDTO dto = new SkillDTO(skill1.getId(), skill1.getName(), skill1.getLevel().name());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                ucBuilder
                        .path("/skills/{id}")
                        .buildAndExpand(dto.getId())
                        .toUri()
        );

        return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillDTO> updateSkill(@RequestBody SkillDTO dto, @PathVariable int id) throws SkillAlreadyExistException, SkillNotFoundException, SkillLevelNotFoundException {

        if (dto.getId() == id) {
            skillService.getSkill(id);
            Skill updatedSkill = skillService.updateSkill(dto);
            SkillDTO skillDTO = new SkillDTO(updatedSkill.getId(), updatedSkill.getName(), updatedSkill.getLevel().name());
            return new ResponseEntity<>(skillDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable int id) throws SkillNotAuthorizedToDeleteException {

        try {
            skillService.deleteSkill(id);
        } catch (SkillNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
