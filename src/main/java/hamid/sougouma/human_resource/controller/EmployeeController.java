package hamid.sougouma.human_resource.controller;

import hamid.sougouma.human_resource.dto.*;
import hamid.sougouma.human_resource.exception.*;
import hamid.sougouma.human_resource.service.EmployeeSkillService;
import hamid.sougouma.human_resource.service.SkillService;
import hamid.sougouma.human_resource.service.EmployeeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeSkillService employeeSkillService;
    private final SkillService skillService;

    public EmployeeController(EmployeeService employeeService, EmployeeSkillService employeeSkillService, SkillService skillService) {
        this.employeeService = employeeService;
        this.employeeSkillService = employeeSkillService;
        this.skillService = skillService;
    }


    @GetMapping
    public ResponseEntity<Collection<EmployeeDTO>> findAll() {

        List<EmployeeDTO> employees = employeeService.findAll();

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/active")
    public ResponseEntity<Collection<EmployeeDTO>> findAllFilteredByActive(@RequestParam(name = "value") boolean active) {

        List<EmployeeDTO> employees = employeeService.findAllFilteredByActive(active);

        return ResponseEntity.ok(employees);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findEmployeeById(@PathVariable long id) throws EmployeeNotFoundException {

        EmployeeDTO employeeDTO = employeeService.findById(id);

        return ResponseEntity.ok(employeeDTO);
    }


    @PostMapping("")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO, UriComponentsBuilder uriComponentsBuilder) throws RoleNotFoundException {

        System.out.println(employeeDTO.getHireDate());
        System.out.println(employeeDTO.getBirthday());
        EmployeeDTO createdEmployee = employeeService.addEmployee(employeeDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                uriComponentsBuilder
                        .path("/employees/{id}")
                        .buildAndExpand(createdEmployee.getId())
                        .toUri()
        );

        return new ResponseEntity<>(employeeDTO, headers, HttpStatus.CREATED);

    }


    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable(value = "id") long employeeId, @RequestBody EmployeeDTO employeeDTO) throws EmployeeNotFoundException {

        if (employeeDTO != null && employeeDTO.getId() == employeeId) {

            EmployeeDTO updatedEmployee = employeeService.updateEmployee(employeeDTO);

            System.out.println(updatedEmployee.toString());
            return ResponseEntity.ok(employeeDTO);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/{id}/credentials")
    public ResponseEntity<EmployeeDTO> changeCredentials(@PathVariable(value = "id") long employeeId, @RequestBody PasswordRecord record) throws EmployeeNotFoundException, InvalidPasswordException {
        EmployeeDTO employeeDTO = employeeService.changePassword(record, employeeId);
        return ResponseEntity.ok(employeeDTO);

    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteEmployee(@PathVariable long id){

        try {
            employeeService.deleteEmployee(id);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }


    @GetMapping("/{userId}/skills")
    public ResponseEntity<Set<SkillDTO>> getEmployeeSkills(@PathVariable long userId) throws EmployeeNotFoundException {
        Set<SkillDTO> skills = employeeSkillService.getEmployeeSkills(userId);
        return ResponseEntity.ok(skills);
    }


    @PostMapping("/{id}/skills")
    public ResponseEntity<Set<SkillDTO>> addSkill(@PathVariable long id, @RequestBody SetKillsToEmployeeRecord skillRecord, UriComponentsBuilder builder)
            throws SkillAlreadyExistException, EmployeeNotFoundException, SkillLevelNotFoundException, SkillNotFoundException {

        if (skillRecord.skills().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        Set<SkillDTO> skills = employeeService.addEmployeeSkills(id, skillRecord);


        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder
                        .path("/users/{id}/skills")
                        .buildAndExpand(id)
                        .toUri()
        );

        return new ResponseEntity<>(skills, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{employeeId}/skills/{skillId}")
    public ResponseEntity<?> deleteSkill(@PathVariable long employeeId, @PathVariable int skillId) {

        try {
            employeeSkillService.removeEmployeeSkills(employeeId, skillId);
        } catch (SkillNotFoundException | EmployeeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
