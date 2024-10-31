package hamid.sougouma.human_resource.controller;

import hamid.sougouma.human_resource.dto.EmployeDTO;
import hamid.sougouma.human_resource.dto.SetKillsToEmployeeRecord;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.Skill;
import hamid.sougouma.human_resource.exception.RoleNotFoundException;
import hamid.sougouma.human_resource.exception.UserNotFoundException;
import hamid.sougouma.human_resource.service.EmployeeSkillService;
import hamid.sougouma.human_resource.service.SkillService;
import hamid.sougouma.human_resource.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final EmployeeSkillService employeeSkillService;
    private final SkillService skillService;

    public UserController(UserService userService, EmployeeSkillService employeeSkillService, SkillService skillService) {
        this.userService = userService;
        this.employeeSkillService = employeeSkillService;
        this.skillService = skillService;
    }

    @GetMapping
    public ResponseEntity<Collection<Employee>> users() {

        List<Employee> employees = userService.findAll();

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findUserByid(@PathVariable long id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Transactional
    @PostMapping("")
    public ResponseEntity<EmployeDTO> addUser(@RequestBody EmployeDTO employeDTO, UriComponentsBuilder uriComponentsBuilder) throws RoleNotFoundException {

        Employee employee = userService.getEmployeeFromDTO(employeDTO);

        System.out.println(employee.toString());
        Employee createdEmployee = userService.addUser(employee);

        if (createdEmployee != null) {

            Set<Skill> skills = employeDTO.getSkills();

            Set<Skill> skillsToReturn = new HashSet<>();

            if (skills != null) {
                skillsToReturn = skillService.addAllSkills(skills);
                skillsToReturn = employeeSkillService.addEmployeeSkills(createdEmployee, skillsToReturn);
            }

            employeDTO = userService.getEmployeeDTOFromEmployee(employee);
            employeDTO.setSkills(skillsToReturn);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(
                    uriComponentsBuilder
                            .path("/{id}")
                            .buildAndExpand(createdEmployee.getId())
                            .toUri()
            );

            return new ResponseEntity<>(employeDTO, headers, HttpStatus.CREATED);

        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateUser(@PathVariable(value = "id") long userId, @RequestBody Employee employee) throws UserNotFoundException {

        if (employee != null && employee.getId() == userId) {
            Employee updatedEmployee = userService.updateUser(employee);

            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteUser(@PathVariable long id){

        try {
            if (userService.findById(id) != null) {
                userService.deleteUser(id);
            }
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/{userId}/skills")
    public ResponseEntity<Set<Skill>> getEmployeeSkills(@PathVariable long userId) throws UserNotFoundException {
        Employee employee = userService.findById(userId);
        return ResponseEntity.ok(employeeSkillService.getEmployeeSkills(employee));
    }

    @PostMapping("/{id}/skills")
    public ResponseEntity<Set<Skill>> addSkill(@PathVariable long id, @RequestBody SetKillsToEmployeeRecord skillRecord, UriComponentsBuilder builder)
            throws UserNotFoundException
    {
        Employee employee = userService.findById(id);
        employeeSkillService.addEmployeeSkills(employee, skillRecord.skills());

        Set<Skill> skills = employeeSkillService.getEmployeeSkills(employee);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder
                        .path("/{id}/skills")
                        .buildAndExpand(id)
                        .toUri()
        );

        return new ResponseEntity<>(skills, headers, HttpStatus.CREATED);
    }


}
