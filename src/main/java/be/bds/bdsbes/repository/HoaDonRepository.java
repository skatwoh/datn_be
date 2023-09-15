package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HoaDonRepository extends JpaRepository<HoaDon, UUID> {
}