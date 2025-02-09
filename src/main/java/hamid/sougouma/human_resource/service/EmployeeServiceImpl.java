package hamid.sougouma.human_resource.service;

import hamid.sougouma.human_resource.dao.RoleRepository;
import hamid.sougouma.human_resource.dao.EmployeeRepository;
import hamid.sougouma.human_resource.dao.UserRepository;
import hamid.sougouma.human_resource.dto.*;
import hamid.sougouma.human_resource.entity.Employee;
import hamid.sougouma.human_resource.entity.Role;
import hamid.sougouma.human_resource.entity.Skill;
import hamid.sougouma.human_resource.entity.User;
import hamid.sougouma.human_resource.enums.SkillLevelEnum;
import hamid.sougouma.human_resource.exception.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final SkillService skillService;
    private final EmployeeSkillService employeeSkillService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, RoleRepository roleRepository, UserRepository userRepository, SkillService skillService, EmployeeSkillService employeeSkillService) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.skillService = skillService;
        this.employeeSkillService = employeeSkillService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        return getEmployeeDTOsFromEmployees(employees);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findAllFilteredByActive(boolean active) {
        List<Employee> employees = employeeRepository.findByActive(active);
        return getEmployeeDTOsFromEmployees(employees);
    }

    @Override
    public List<Employee> searchByFirstName(String firstName) {
        return employeeRepository.findByFirstNameContainingAndActiveTrue(firstName);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDTO findById(long id) throws EmployeeNotFoundException {

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

        return getEmployeeDTOFromEmployee(employee);
    }

    @Transactional
    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) throws RoleNotFoundException {

        String roleName = employeeDTO.getRole();
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RoleNotFoundException(roleName));

        User user = new User();
        user.setRole(role);
        user.setPassword(employeeDTO.getPassword());
        user.setEmail(employeeDTO.getEmail());

        Employee employee = new Employee(employeeDTO, user);
        employee.setActive(true);
        employee = employeeRepository.save(employee);

        return getEmployeeDTOFromEmployee(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(employeeDTO.getId()).orElseThrow(() -> new EmployeeNotFoundException(employeeDTO.getId()));
        employee = employeeRepository.save(updateEmployee(employeeDTO, employee));
        return getEmployeeDTOFromEmployee(employee);
    }

    @Override
    @Transactional
    public void deleteEmployee(long id) throws EmployeeNotFoundException {

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        employee.setActive(false);
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public Set<SkillDTO> addEmployeeSkills(long id, SetKillsToEmployeeRecord record) throws EmployeeNotFoundException, SkillAlreadyExistException, SkillLevelNotFoundException, SkillNotFoundException {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        Set<Skill> skills = new HashSet<>();
        for (SkillDTO dto : record.skills()) {
            Skill skill;

            try {
                skill = skillService.getSkillByNameAndLevel(dto.getName(), getLevelEnum(dto.getLevel().toUpperCase()));
            } catch (SkillNotFoundException e) {
                skill = skillService.addSkill(dto);
            }

            skills.add(skill);
        }
        EmployeeDTO employeeDTO = getEmployeeDTOFromEmployee(employee);
        Set<SkillDTO> skillDTOs = skillService.getSkillDTOs(skills);

        return employeeSkillService.addEmployeeSkills(employeeDTO, skillDTOs);
    }

    @Override
    public EmployeeDTO changePassword(PasswordRecord record, long id) throws EmployeeNotFoundException, InvalidPasswordException {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

        if (!record.password().equals(record.confirmation())) {
            throw new InvalidPasswordException();
        }

        User user = employee.getUser();
        user.setPassword(record.password());
        userRepository.save(user);
        EmployeeDTO employeeDTO = getEmployeeDTOFromEmployee(employee);
        employeeDTO.setPassword(record.password());

        return employeeDTO;
    }

    @Override
    public EmployeeDTO getEmployeeDTOFromEmployee(@NotNull Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getUser().getEmail());
        employeeDTO.setPhone(employee.getPhone());
        employeeDTO.setActive(employee.isActive());
        employeeDTO.setAddress(employee.getAddress());
        employeeDTO.setGender(employee.getGender());
        employeeDTO.setRole(employee.getUser().getRole().getName());
        employeeDTO.setHireDate(employee.getHireDate().toString());
        employeeDTO.setBirthday(employee.getBirthday().toString());
        return employeeDTO;
    }

    @Override
    public List<EmployeeDTO> getEmployeeDTOsFromEmployees(List<Employee> employees) {
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDTOs.add(getEmployeeDTOFromEmployee(employee));
        }
        return employeeDTOs;
    }

    private Employee updateEmployee(EmployeeDTO dto, Employee employee) {
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setPhone(dto.getPhone());
        employee.setAddress(dto.getAddress());
        employee.setGender(dto.getGender());
        employee.setActive(dto.isActive());
        LocalDate birthday = LocalDate.parse(dto.getBirthday());
        LocalDate hireDate= LocalDate.parse(dto.getHireDate());
        employee.setBirthday(birthday);
        employee.setHireDate(hireDate);

        return employee;

    }

    private SkillLevelEnum getLevelEnum(String level) throws SkillLevelNotFoundException {
        return switch (level) {
            case "JUNIOR" -> SkillLevelEnum.JUNIOR;
            case "SENIOR" -> SkillLevelEnum.SENIOR;
            case "EXPERT" -> SkillLevelEnum.EXPERT;
            default -> throw new SkillLevelNotFoundException(level);
        };
    }
}
