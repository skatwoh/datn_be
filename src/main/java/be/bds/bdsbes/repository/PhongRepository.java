package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.Phong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhongRepository extends JpaRepository<Phong, UUID> {
}