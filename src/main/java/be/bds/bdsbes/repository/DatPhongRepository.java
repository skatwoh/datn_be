package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.DatPhong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DatPhongRepository extends JpaRepository<DatPhong, UUID> {
}