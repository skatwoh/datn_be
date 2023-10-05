package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.LoaiPhong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoaiPhongRepository extends JpaRepository<LoaiPhong, Long> {
}