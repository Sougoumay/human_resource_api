package hamid.sougouma.human_resource.controller;

import hamid.sougouma.human_resource.dto.EmployeeDTO;
import hamid.sougouma.human_resource.dto.ExperienceDTO;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.Experience;
import hamid.sougouma.human_resource.exception.EmployeeNotFoundException;
import hamid.sougouma.human_resource.exception.ExperienceNotFoundException;
import hamid.sougouma.human_resource.exception.UserNotFoundException;
import hamid.sougouma.human_resource.service.ExperienceService;
import hamid.sougouma.human_resource.service.EmployeeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;
    private final EmployeeService employeeService;

    public ExperienceController(ExperienceService experienceService, EmployeeService employeeService) {
        this.experienceService = experienceService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<Collection<Experience>> getUserExperiences(@PathVariable long userId) throws EmployeeNotFoundException {

        EmployeeDTO employee = employeeService.findById(userId);
        List<Experience> experiences = experienceService.getUserExperiences(employee.getId());

        return new ResponseEntity<>(experiences, HttpStatus.OK);
    }

    @GetMapping("/{experienceId}")
    public ResponseEntity<Experience> getUserExperience(@PathVariable long userId, @PathVariable long experienceId) throws ExperienceNotFoundException, EmployeeNotFoundException {
        // TODO : don't retrieve the users et theirs roles with the experiences

        EmployeeDTO employee = employeeService.findById(userId);
        Experience experience = experienceService.getExperience(experienceId);
        return new ResponseEntity<>(experience, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Experience> addUserExperience(@PathVariable long userId, @RequestBody ExperienceDTO experienceDTO, UriComponentsBuilder uriComponentsBuilder)
            throws UserNotFoundException, EmployeeNotFoundException {
        EmployeeDTO employee = employeeService.findById(userId);
        if (experienceDTO != null && employee != null) {
            Experience experience = experienceService.getExperienceFromDTO(experienceDTO);
//            experience.setEmployee(employee);
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

    @PutMapping("/{experienceId}")
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

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<?> deleteUserExperience(@PathVariable long userId, @PathVariable long experienceId) throws ExperienceNotFoundException {
        Experience experience = experienceService.getExperience(experienceId);

        if (experience != null && experience.getEmployee().getId() == userId) {
            experienceService.deleteExperience(experience);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }

}
