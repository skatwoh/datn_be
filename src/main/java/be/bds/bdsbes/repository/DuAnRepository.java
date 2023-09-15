package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.DuAn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DuAnRepository extends JpaRepository<DuAn, UUID> {
}