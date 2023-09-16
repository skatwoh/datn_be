package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.ChiTietDichVu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChiTietDichVuRepository extends JpaRepository<ChiTietDichVu, UUID> {
}