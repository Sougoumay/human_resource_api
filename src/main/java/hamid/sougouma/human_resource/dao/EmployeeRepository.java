package hamid.sougouma.human_resource.dao;

import hamid.sougouma.human_resource.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByActive(boolean active);

    List<Employee> findByFirstNameContainingAndActiveTrue(String firstName);



}
