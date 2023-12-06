package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.Sale;
import be.bds.bdsbes.payload.SaleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Sale s SET s.trangThai = :trangThai WHERE s.id = :id")
    Integer updateTrangThaiById(int trangThai, Long id);

    @Query("select new be.bds.bdsbes.payload.SaleResponse(s.id, s.ma, s.ten, s.giaTri, s.ngayBatDau, s.ngayKetThuc, s.trangThai) from Sale s where s.id = ?1")
    SaleResponse get(Long id);

    @Query("select new be.bds.bdsbes.payload.SaleResponse(s.id, s.ma, s.ten, s.giaTri, s.ngayBatDau, s.ngayKetThuc, s.trangThai) from Sale s where s.trangThai = 1")
    SaleResponse getSale();
}