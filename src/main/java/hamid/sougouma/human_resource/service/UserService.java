package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.entity.User;
import hamid.sougouma.human_resource.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<User> findAll();

    List<User> searchByFirstName(String firstName);

    User findByEmail(String email) throws UserNotFoundException;

    User findById(long id) throws UserNotFoundException;

    User addUser(User user);

    User updateUser(User user) throws UserNotFoundException;

    void deleteUser(long id) throws UserNotFoundException;



}
