package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.ChiTietPhong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChiTietPhongRepository extends JpaRepository<ChiTietPhong, Long> {


}