package hamid.sougouma.human_resource.dao;

import hamid.sougouma.human_resource.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    List<User> findByFirstNameContaining(String firstName);

    Optional<User> findByEmail(String email);

}
