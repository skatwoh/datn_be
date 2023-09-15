package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.TaiSan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaiSanRepository extends JpaRepository<TaiSan, UUID> {
}