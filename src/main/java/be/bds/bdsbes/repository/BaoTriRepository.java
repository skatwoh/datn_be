package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.BaoTri;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BaoTriRepository extends JpaRepository<BaoTri, UUID> {
}