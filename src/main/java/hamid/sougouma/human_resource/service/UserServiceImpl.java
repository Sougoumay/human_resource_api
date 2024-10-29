package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.dao.UserRepository;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Employee> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<Employee> searchByFirstName(String firstName) {
        return userRepository.findByFirstNameContaining(firstName);
    }

    @Override
    public Employee findByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public Employee findById(long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Employee addUser(Employee employee) {
        return userRepository.save(employee);
    }

    @Override
    public Employee updateUser(Employee employee) throws UserNotFoundException {

        this.findById(employee.getId());
        return userRepository.save(employee);
    }

    @Override
    public void deleteUser(long id) throws UserNotFoundException {

        Employee employee = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(employee);
    }
}
