package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.DatPhong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("datPhongRepository")
public interface DatPhongRepository extends JpaRepository<DatPhong, UUID> {
}