package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.LichSuDatPhong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LichSuDatPhongRepository extends JpaRepository<LichSuDatPhong, UUID> {
}