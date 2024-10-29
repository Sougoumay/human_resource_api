package hamid.sougouma.human_resource.controller;

import hamid.sougouma.human_resource.entity.User;
import hamid.sougouma.human_resource.exception.UserNotFoundException;
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
    public ResponseEntity<Collection<User>> users() {

        List<User> users = userService.findAll();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserByid(@PathVariable long id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {

        User createdUser = userService.addUser(user);

        if (createdUser != null) {

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(
                    uriComponentsBuilder
                            .path("/users/{id}")
                            .buildAndExpand(createdUser.getId())
                            .toUri()
            );

            return new ResponseEntity<>(headers, HttpStatus.CREATED);

        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") long userId, @RequestBody User user) throws UserNotFoundException {

        if (user != null && user.getId() == userId) {
            User updatedUser = userService.updateUser(user);

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
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
