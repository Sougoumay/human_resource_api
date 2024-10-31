package hamid.sougouma.human_resource.controller;

import com.fasterxml.jackson.annotation.JsonView;
import hamid.sougouma.human_resource.dto.EmployeDTO;
import hamid.sougouma.human_resource.dto.SetKillsToEmployeeRecord;
import hamid.sougouma.human_resource.dto.Views;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.Skill;
import hamid.sougouma.human_resource.exception.RoleNotFoundException;
import hamid.sougouma.human_resource.exception.SkillNotFoundException;
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

    @JsonView(Views.Resume.class)
    @GetMapping
    public ResponseEntity<Collection<Employee>> users() {

        List<Employee> employees = userService.findAll();

        return ResponseEntity.ok(employees);
    }

    @JsonView(Views.Complet.class)
    @GetMapping("/{id}")
    public ResponseEntity<EmployeDTO> findUserByid(@PathVariable long id) throws UserNotFoundException {

        Employee employee = userService.findById(id);
        EmployeDTO employeDTO = userService.getEmployeeDTOFromEmployee(employee);
        employeDTO.setSkills(employeeSkillService.getEmployeeSkills(employee));

        return ResponseEntity.ok(employeDTO);
    }

    @JsonView(Views.Complet.class)
    @Transactional
    @PostMapping("")
    public ResponseEntity<EmployeDTO> addUser(@RequestBody EmployeDTO employeDTO, UriComponentsBuilder uriComponentsBuilder) throws RoleNotFoundException {

        Employee employee = userService.getEmployeeFromDTO(employeDTO);

        System.out.println(employee.toString());
        Employee createdEmployee = userService.addUser(employee);

        if (createdEmployee != null) {

           employeDTO = setEmployeeSkills(employeDTO, createdEmployee);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(
                    uriComponentsBuilder
                            .path("/users/{id}")
                            .buildAndExpand(createdEmployee.getId())
                            .toUri()
            );

            return new ResponseEntity<>(employeDTO, headers, HttpStatus.CREATED);

        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }


    @JsonView(Views.Complet.class)
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<EmployeDTO> updateUser(@PathVariable(value = "id") long userId, @RequestBody EmployeDTO employeDTO) throws UserNotFoundException {

        if (employeDTO != null && employeDTO.getId() == userId) {
            Employee employee = userService.getEmployeeFromDTO(employeDTO);

            Employee updatedEmployee = userService.updateUser(employee);

            employeDTO = setEmployeeSkills(employeDTO, updatedEmployee);
            System.out.println(employeDTO.toString());
//            return new ResponseEntity<>(employeDTO, HttpStatus.OK);
            return ResponseEntity.ok(employeDTO);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @JsonView(Views.Resume.class)
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

    @JsonView(Views.Complet.class)
    @GetMapping("/{userId}/skills")
    public ResponseEntity<Set<Skill>> getEmployeeSkills(@PathVariable long userId) throws UserNotFoundException {
        Employee employee = userService.findById(userId);
        return ResponseEntity.ok(employeeSkillService.getEmployeeSkills(employee));
    }

    @JsonView(Views.Complet.class)
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
                        .path("/users/{id}/skills")
                        .buildAndExpand(id)
                        .toUri()
        );

        return new ResponseEntity<>(skills, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/skills/{skillId}")
    public ResponseEntity<?> deleteSkill(@PathVariable long userId, @PathVariable int skillId) {
        try {
            Employee employee = userService.findById(userId);
            Skill skill = skillService.getSkill(skillId);
            employeeSkillService.removeEmployeeSkills(employee, skill);
        } catch (UserNotFoundException e) {
            System.out.println("Someone tried to delete skill for an Employee who doesn't exist. the Given Id is : " + userId);
        } catch (SkillNotFoundException e) {
            System.out.println("Someone tried to delete skill that doesn't exist. the Given Id is : " + skillId);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    public EmployeDTO setEmployeeSkills(EmployeDTO employeDTO, Employee employee1) {

        Set<Skill> skills = employeDTO.getSkills();

        Set<Skill> skillsToReturn = new HashSet<>();

        Employee employee = userService.getEmployeeFromDTO(employeDTO);


        if (skills != null) {
            skillsToReturn = skillService.addAllSkills(skills);
            skillsToReturn = employeeSkillService.addEmployeeSkills(employee1, skillsToReturn);
        }

        employeDTO = userService.getEmployeeDTOFromEmployee(employee);
        employeDTO.setSkills(skillsToReturn);

        return employeDTO;
    }


}
