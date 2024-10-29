package hamid.sougouma.human_resource.dao;

import hamid.sougouma.human_resource.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAll();

    List<Employee> findByFirstNameContaining(String firstName);

    Optional<Employee> findByEmail(String email);

}
