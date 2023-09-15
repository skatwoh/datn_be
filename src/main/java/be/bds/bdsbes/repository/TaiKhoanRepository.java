package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, UUID> {
}