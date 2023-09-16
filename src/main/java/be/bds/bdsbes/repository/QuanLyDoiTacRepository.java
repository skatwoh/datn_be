package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.QuanLyDoiTac;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuanLyDoiTacRepository extends JpaRepository<QuanLyDoiTac, UUID> {
}