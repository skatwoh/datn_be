package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.TheThanhVien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TheThanhVienRepository extends JpaRepository<TheThanhVien, Long> {
}