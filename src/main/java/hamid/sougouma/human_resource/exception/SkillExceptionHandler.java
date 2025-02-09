package hamid.sougouma.human_resource.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SkillExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SkillNotFoundException.class)
    public ResponseEntity<String> skillNotFoundExceptionHandler(SkillNotFoundException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SkillAlreadyExistException.class)
    public ResponseEntity<String> skillAlreadyExistExceptionHandler(SkillAlreadyExistException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(SkillNotAuthorizedToDeleteException.class)
    public ResponseEntity<String> SkillNotAuthorizedToDeleteExceptionHandler(SkillNotAuthorizedToDeleteException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(SkillLevelNotFoundException.class)
    public ResponseEntity<String> SkillLevelNotFoundExceptionHandler(SkillLevelNotFoundException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.NOT_ACCEPTABLE);
    }
}
