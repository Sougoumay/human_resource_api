package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<Employee> findAll();

    List<Employee> searchByFirstName(String firstName);

    Employee findByEmail(String email) throws UserNotFoundException;

    Employee findById(long id) throws UserNotFoundException;

    Employee addUser(Employee employee);

    Employee updateUser(Employee employee) throws UserNotFoundException;

    void deleteUser(long id) throws UserNotFoundException;



}
