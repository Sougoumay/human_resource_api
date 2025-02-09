package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.dto.*;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.exception.*;

import java.util.List;
import java.util.Set;

public interface EmployeeService {

    List<EmployeeDTO> findAll();

    List<EmployeeDTO> findAllFilteredByActive(boolean active);

    List<Employee> searchByFirstName(String firstName);

    EmployeeDTO findById(long id) throws EmployeeNotFoundException;

    EmployeeDTO addEmployee(EmployeeDTO employeeDTO) throws RoleNotFoundException;

    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) throws EmployeeNotFoundException;

    void deleteEmployee(long id) throws EmployeeNotFoundException;

    EmployeeDTO getEmployeeDTOFromEmployee(Employee employee);

    List<EmployeeDTO> getEmployeeDTOsFromEmployees(List<Employee> employees);

    Set<SkillDTO> addEmployeeSkills(long id, SetKillsToEmployeeRecord record)
            throws EmployeeNotFoundException, SkillAlreadyExistException,
            SkillLevelNotFoundException, SkillNotFoundException;

    EmployeeDTO changePassword(PasswordRecord record, long id) throws
            EmployeeNotFoundException, InvalidPasswordException;
}
