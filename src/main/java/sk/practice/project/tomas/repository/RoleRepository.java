package sk.practice.project.tomas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.practice.project.tomas.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
