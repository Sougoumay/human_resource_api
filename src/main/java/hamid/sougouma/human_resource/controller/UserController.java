package hamid.sougouma.human_resource.controller;

import hamid.sougouma.human_resource.dto.ExperienceDTO;
import hamid.sougouma.human_resource.dto.UserDTO;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.Experience;
import hamid.sougouma.human_resource.exception.ExperienceNotFoundException;
import hamid.sougouma.human_resource.exception.RoleNotFoundException;
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

    public UserController(UserService userService) {
        this.userService = userService;
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
    public ResponseEntity<Employee> addUser(@RequestBody Employee employee, UriComponentsBuilder uriComponentsBuilder) throws RoleNotFoundException {

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
}
