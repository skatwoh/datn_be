package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.entities.QuanLyDoiTac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Repository
public interface QuanLyDoiTacRepository extends JpaRepository<QuanLyDoiTac, Long> {
    @Query("select p from QuanLyDoiTac p where p.ma like CONCAT('%', ?1, '%') or p.tenCongTy like CONCAT('%',?1, '%') order by p.ma desc")
    Page<QuanLyDoiTac> searchPartner(Pageable pageable, String searchInput);
    @Transactional
    @Modifying
    @Query("UPDATE QuanLyDoiTac p SET p.trangThai = :trangThai WHERE p.id = :id")
    Integer updateTrangThaiById(int trangThai, Long id);
}