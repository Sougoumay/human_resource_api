package hamid.sougouma.human_resource.controller;

import hamid.sougouma.human_resource.dto.EmployeeDTO;
import hamid.sougouma.human_resource.dto.SetKillsToEmployeeRecord;
import hamid.sougouma.human_resource.dto.SkillDTO;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.Skill;
import hamid.sougouma.human_resource.exception.*;
import hamid.sougouma.human_resource.service.EmployeeSkillService;
import hamid.sougouma.human_resource.service.SkillService;
import hamid.sougouma.human_resource.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.HashSet;
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
    public ResponseEntity<Collection<EmployeeDTO>> getAll() {

        List<EmployeeDTO> employees = employeeService.findAll();

        return ResponseEntity.ok(employees);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findUserByid(@PathVariable long id) throws EmployeeNotFoundException {

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


    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteUser(@PathVariable long id){

        try {
            employeeService.deleteEmployee(id);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }
//
//
//    @GetMapping("/{userId}/skills")
//    public ResponseEntity<Set<Skill>> getEmployeeSkills(@PathVariable long userId) throws UserNotFoundException {
//        Employee employee = employeeService.findById(userId);
//        return ResponseEntity.ok(employeeSkillService.getEmployeeSkills(employee));
//    }


    @PostMapping("/{id}/skills")
    public ResponseEntity<Set<SkillDTO>> addSkill(@PathVariable long id, @RequestBody SetKillsToEmployeeRecord skillRecord, UriComponentsBuilder builder)
            throws SkillAlreadyExistException, EmployeeNotFoundException {

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

//    @DeleteMapping("/{userId}/skills/{skillId}")
//    public ResponseEntity<?> deleteSkill(@PathVariable long userId, @PathVariable int skillId) {
//        try {
//            Employee employee = employeeService.findById(userId);
//            Skill skill = skillService.getSkill(skillId);
//            employeeSkillService.removeEmployeeSkills(employee, skill);
//        } catch (UserNotFoundException e) {
//            System.out.println("Someone tried to delete skill for an Employee who doesn't exist. the Given Id is : " + userId);
//        } catch (SkillNotFoundException e) {
//            System.out.println("Someone tried to delete skill that doesn't exist. the Given Id is : " + skillId);
//        }
//
//        return new ResponseEntity<>(HttpStatus.OK);
//
//    }
//
//    public EmployeeDTO setEmployeeSkills(EmployeeDTO employeeDTO, Employee employee1) {
//
//        Set<SkillDTO> skills = employeeDTO.getSkills();
//
//        Set<Skill> skillsToReturn = new HashSet<>();
//
//        Employee employee = employeeService.getEmployeeFromDTO(employeeDTO);
//
//
//        if (skills != null) {
//            skillsToReturn = skillService.addAllSkills(skills);
//            skillsToReturn = employeeSkillService.addEmployeeSkills(employee1, skillsToReturn);
//        }
//
//        employeeDTO = employeeService.getEmployeeDTOFromEmployee(employee);
//        employeeDTO.setSkills(skillsToReturn);
//
//        return employeeDTO;
//    }


}
