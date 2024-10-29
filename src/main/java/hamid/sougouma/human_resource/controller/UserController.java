package hamid.sougouma.human_resource.controller;

import hamid.sougouma.human_resource.dto.UserDTO;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.Experience;
import hamid.sougouma.human_resource.exception.ExperienceNotFoundException;
import hamid.sougouma.human_resource.exception.UserNotFoundException;
import hamid.sougouma.human_resource.service.ExperienceService;
import hamid.sougouma.human_resource.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ExperienceService experienceService;

    public UserController(UserService userService, ExperienceService experienceService) {
        this.userService = userService;
        this.experienceService = experienceService;
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

    @PostMapping("")
    public ResponseEntity<Employee> addUser(@RequestBody Employee employee, UriComponentsBuilder uriComponentsBuilder) {

        System.out.println(employee.toString());
        Employee createdEmployee = userService.addUser(employee);

        if (createdEmployee != null) {

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(
                    uriComponentsBuilder
                            .path("/users/{id}")
                            .buildAndExpand(createdEmployee.getId())
                            .toUri()
            );

            return new ResponseEntity<>(headers, HttpStatus.CREATED);

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


    @GetMapping("/{id}/experiences")
    public ResponseEntity<Collection<Experience>> getUserExperiences(@PathVariable long id) throws UserNotFoundException {

        Employee employee = userService.findById(id);
        List<Experience> experiences = experienceService.getUserExperiences(employee.getId());

        return new ResponseEntity<>(experiences, HttpStatus.OK);
    }

    @GetMapping("/{userId}/experiences/{experienceId}")
    public ResponseEntity<Experience> getUserExperience(@PathVariable long userId, @PathVariable long experienceId) throws UserNotFoundException, ExperienceNotFoundException {
        Employee employee = userService.findById(userId);
        Experience experience = experienceService.getExperience(experienceId);
        return new ResponseEntity<>(experience, HttpStatus.OK);
    }


    @PostMapping("/{userId}/experiences")
    public ResponseEntity<Experience> addUserExperience(@PathVariable long userId, @RequestBody Experience experience, UriComponentsBuilder uriComponentsBuilder)
            throws UserNotFoundException
    {
        Employee employee = userService.findById(userId);
        if (experience != null && experience.getEmployee().getId() == employee.getId()) {
            Experience createdExperience = experienceService.addExperience(experience);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(
                    uriComponentsBuilder
                            .path("/users/{userId}/experiences/{experienceId}")
                            .buildAndExpand(employee.getId(), createdExperience.getId())
                            .toUri()
                    );

            return new ResponseEntity<>(createdExperience, headers, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/{userId}/experiences/{experienceId}")
    public ResponseEntity<Experience> updateUserExperience(@PathVariable long userId, @PathVariable long experienceId, @RequestBody Experience experience) throws ExperienceNotFoundException {

        if (experience != null) {
            Experience experienceInDB = experienceService.getExperience(experienceId);

            if (experienceInDB != null && experienceInDB.getEmployee().getId() == userId) {
                Experience updatedExperience = experienceService.updateExperience(experience);
                return new ResponseEntity<>(updatedExperience, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/{userId}/experiences/{experienceId}")
    public ResponseEntity<?> deleteUserExperience(@PathVariable long userId, @PathVariable long experienceId) throws ExperienceNotFoundException {
        Experience experience = experienceService.getExperience(experienceId);

        if (experience != null && experience.getEmployee().getId() == userId) {
            experienceService.deleteExperience(experience);
            return new ResponseEntity<>(HttpStatus.OK);
        }

       return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }




}
