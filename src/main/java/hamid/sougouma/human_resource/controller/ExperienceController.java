package hamid.sougouma.human_resource.controller;

import hamid.sougouma.human_resource.dto.ExperienceDTO;
import hamid.sougouma.human_resource.exception.EmployeeNotFoundException;
import hamid.sougouma.human_resource.exception.ExperienceNotFoundException;
import hamid.sougouma.human_resource.service.ExperienceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users/{employeeId}/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping
    public ResponseEntity<Collection<ExperienceDTO>> getEmployeeExperiences(@PathVariable long employeeId) {

        List<ExperienceDTO> experiences = experienceService.getUserExperiences(employeeId);

        return new ResponseEntity<>(experiences, HttpStatus.OK);
    }

    @GetMapping("/{experienceId}")
    public ResponseEntity<ExperienceDTO> getEmployeeExperience(@PathVariable long employeeId, @PathVariable long experienceId) throws ExperienceNotFoundException, EmployeeNotFoundException {

        ExperienceDTO experience = experienceService.getExperience(employeeId,experienceId);
        return new ResponseEntity<>(experience, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ExperienceDTO> addUserExperience(@PathVariable long employeeId, @RequestBody ExperienceDTO experienceDTO, UriComponentsBuilder uriComponentsBuilder)
            throws EmployeeNotFoundException {
        if (experienceDTO != null) {
            ExperienceDTO dto = experienceService.addExperience(employeeId, experienceDTO);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(
                    uriComponentsBuilder
                            .path("/users/{employeeId}/experiences/{experienceId}")
                            .buildAndExpand(employeeId, dto.getId())
                            .toUri()
            );

            return new ResponseEntity<>(experienceDTO, headers, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity<ExperienceDTO> updateUserExperience(@PathVariable long employeeId, @PathVariable long experienceId, @RequestBody ExperienceDTO experienceDTO) throws ExperienceNotFoundException, EmployeeNotFoundException {
        if (experienceDTO != null && experienceDTO.getId() == experienceId) {
            ExperienceDTO updatedExperience = experienceService.updateExperience(employeeId,experienceDTO);
            return new ResponseEntity<>(updatedExperience, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<?> deleteUserExperience(@PathVariable long employeeId, @PathVariable long experienceId) throws ExperienceNotFoundException, EmployeeNotFoundException {
        experienceService.deleteExperience(employeeId, experienceId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
