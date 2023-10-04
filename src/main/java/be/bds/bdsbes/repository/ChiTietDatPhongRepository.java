package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.ChiTietDatPhong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChiTietDatPhongRepository extends JpaRepository<ChiTietDatPhong, Long> {
}