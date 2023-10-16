package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.Voucher;
import be.bds.bdsbes.service.dto.response.TheThanhVienResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query(value = "select vcher.id, vcher.ma, vcher.mo_ta, vcher.giam_gia, vcher.ngay_bat_dau, vcher.ngay_ket_thuc, vcher.trang_thai, vcher.so_luong from voucher vcher", nativeQuery = true)
    List<VoucherRepository> getAllV();

}