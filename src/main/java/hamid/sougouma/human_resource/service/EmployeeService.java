package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.dto.EmployeeDTO;
import hamid.sougouma.human_resource.dto.SetKillsToEmployeeRecord;
import hamid.sougouma.human_resource.dto.SkillDTO;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.exception.EmployeeNotFoundException;
import hamid.sougouma.human_resource.exception.RoleNotFoundException;
import hamid.sougouma.human_resource.exception.SkillAlreadyExistException;
import hamid.sougouma.human_resource.exception.UserNotFoundException;

import java.util.List;
import java.util.Set;

public interface EmployeeService {

    List<EmployeeDTO> findAll();

    List<Employee> searchByFirstName(String firstName);

    EmployeeDTO findByEmail(String email) throws UserNotFoundException;

    EmployeeDTO findById(long id) throws EmployeeNotFoundException;

    EmployeeDTO addEmployee(EmployeeDTO employeeDTO) throws RoleNotFoundException;

    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) throws EmployeeNotFoundException;

    void deleteEmployee(long id) throws EmployeeNotFoundException;

    EmployeeDTO getEmployeeDTOFromEmployee(Employee employee);

    List<EmployeeDTO> getEmployeeDTOsFromEmployees(List<Employee> employees);

    Set<SkillDTO> addEmployeeSkills(long id, SetKillsToEmployeeRecord record) throws EmployeeNotFoundException, SkillAlreadyExistException;
}
