package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.BaoTri;
import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.payload.BaoTriResponse1;
import be.bds.bdsbes.payload.ChiTietPhongResponse1;
import be.bds.bdsbes.payload.PhongResponse1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
@Repository
public interface BaoTriRepository extends JpaRepository<BaoTri, Long> {
    @Query("select new be.bds.bdsbes.payload.ChiTietPhongResponse1(p.id,p.tang,p.dichVu,p.dienTich, p.trangThai, ph.id, ph.ma, ph.giaPhong, ph.image) from ChiTietPhong p inner join p.phong ph")
    List<ChiTietPhongResponse1> singleListRoom();
    @Query("select new be.bds.bdsbes.payload.BaoTriResponse1(p.id, p.ngayBatDau, p.ngayKetThuc, p.chiPhiBaoTri,p.ghiChu,p.trangThai, l.id) from BaoTri p inner join p.chiTietPhong l where p.id = ?1")
    BaoTriResponse1 get(Long id);
    @Query("select p from BaoTri p where p.ghiChu like CONCAT('%', ?1, '%') ")
    Page<BaoTri> searchMaintenance(Pageable pageable, String searchInput);
    @Transactional
    @Modifying
    @Query("UPDATE BaoTri p SET p.trangThai = :trangThai WHERE p.id = :id")
    Integer updateTrangThaiById(int trangThai, Long id);

    @Query("select b from BaoTri b inner join ChiTietPhong ct on b.chiTietPhong.id = ct.id where (ct.id = :idCT or :idCT is null or :idCT = 0) and (b.ghiChu like :ghiChu or :ghiChu is null or :ghiChu = '')")
    Page<BaoTri> getListByChiTiet(Pageable pageable, Long idCT, String ghiChu);
}