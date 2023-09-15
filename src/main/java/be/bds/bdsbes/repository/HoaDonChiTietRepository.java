package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, UUID> {
}