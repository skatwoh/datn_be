package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.DuAn;
import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.entities.Voucher;
import be.bds.bdsbes.payload.VoucherResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query("select new be.bds.bdsbes.payload.VoucherResponse( vcher.id, vcher.ma, vcher.moTa, vcher.giamGia, vcher.ngayBatDau, vcher.ngayKetThuc, vcher.trangThai, vcher.soLuong) from Voucher vcher where vcher.id = ?1")
    VoucherResponse getVoucher(Long id);

    @Query("select v from Voucher v where v.trangThai = 1 and v.soLuong > 0")
    Page<Voucher> getAllByTrangThai(Pageable pageable);

    @Query("select d from Voucher d where d.ma like CONCAT('%', ?1, '%') or d.moTa like CONCAT('%',?1, '%') order by d.ma desc")
    Page<Voucher> searchVoucher(Pageable pageable, String searchInput);

    @Transactional
    @Modifying
    @Query("UPDATE Voucher d SET d.trangThai = :trangThai WHERE d.id = :id")
    Integer updateTrangThaiById(int trangThai, Long id);


    @Query(value = "SELECT * FROM voucher v WHERE v.ngay_ket_thuc < :date AND v.trang_thai = :status", nativeQuery = true)
    List<Voucher> findByExpiryDateBeforeAndStatus(@Param("date") LocalDate date, @Param("status") Integer status);


}