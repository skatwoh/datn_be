package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.Sale;
import be.bds.bdsbes.entities.Voucher;
import be.bds.bdsbes.payload.SaleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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

    @Query(value = "SELECT * FROM sale v WHERE v.ngay_ket_thuc < :date AND v.trang_thai = :status", nativeQuery = true)
    List<Sale> findByExpiryDateBeforeAndStatus(@Param("date") LocalDate date, @Param("status") Integer status);
}