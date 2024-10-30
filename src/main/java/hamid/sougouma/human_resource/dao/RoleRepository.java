package hamid.sougouma.human_resource.dao;

import hamid.sougouma.human_resource.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
