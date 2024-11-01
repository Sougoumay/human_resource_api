package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.dao.RoleRepository;
import hamid.sougouma.human_resource.dao.UserRepository;
import hamid.sougouma.human_resource.dto.EmployeDTO;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.Role;
import hamid.sougouma.human_resource.exception.RoleNotFoundException;
import hamid.sougouma.human_resource.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

    @Transactional
    @Override
    public Employee addUser(Employee employee) throws RoleNotFoundException {

        // TODO : we have errors when we try to save a user with a new role that haven't id
        // TODO : we have also an error when we try to add user with an existing role

        if (employee.getRole() != null && employee.getRole().getId() != 0) {
            long roleId = employee.getRole().getId();
            Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException(roleId));
            employee.setRole(role);
        }

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

    @Override
    public Employee getEmployeeFromDTO(@NotNull EmployeDTO employeDTO) {
        Employee employee = new Employee();
        employee.setBirthday(employeDTO.getBirthday());
        employee.setFirstName(employeDTO.getFirstName());
        employee.setLastName(employeDTO.getLastName());
        employee.setEmail(employeDTO.getEmail());
        employee.setId(employeDTO.getId());
        employee.setPassword(employeDTO.getPassword());
        employee.setAddress(employeDTO.getAddress());
        employee.setGender(employeDTO.getGender());
        employee.setPhone(employeDTO.getPhone());
        employee.setRole(employeDTO.getRole());
        return employee;
    }

    @Override
    public EmployeDTO getEmployeeDTOFromEmployee(@NotNull Employee employee) {
        EmployeDTO employeeDTO = new EmployeDTO();
        employeeDTO.setBirthday(employee.getBirthday());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setId(employee.getId());
        employeeDTO.setPassword(employee.getPassword());
        employeeDTO.setAddress(employee.getAddress());
        employeeDTO.setGender(employee.getGender());
        employeeDTO.setPhone(employee.getPhone());
        employeeDTO.setRole(employee.getRole());
        return employeeDTO;
    }
}
