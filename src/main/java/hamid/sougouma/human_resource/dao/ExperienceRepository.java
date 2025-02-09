package hamid.sougouma.human_resource.dao;

import hamid.sougouma.human_resource.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    List<Experience> findByEmployeeId(Long id);

    Experience findByEmployeeIdAndExperienceId(Long employeeId, Long experienceId);

}
