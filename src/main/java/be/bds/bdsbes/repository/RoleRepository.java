package be.bds.bdsbes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import be.bds.bdsbes.domain.Role;
import be.bds.bdsbes.domain.RoleName;

import java.util.Optional;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);

    Boolean existsByName(RoleName roleName);
}