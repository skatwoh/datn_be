package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.payload.KhachHangResponse1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {

    @Query("select kh.id from KhachHang kh join User u on kh.id = u.khachHang.id join HoaDon hd on kh.id = hd.khachHang.id where u.id = ?1 and hd.trangThai = 1")
    Long findByIdKhachHang(Long id);

    @Query("select kh.id from KhachHang kh join User u on kh.id = u.khachHang.id where u.id = ?1")
    Long findByI(Long id);

    @Query("select u.id from KhachHang kh join User u on kh.id = u.khachHang.id where kh.id = ?1")
    Long findByIdUser(Long id);

    @Query("select new be.bds.bdsbes.payload.KhachHangResponse1(k.id, k.ma, k.hoTen, k.ngaySinh, k.gioiTinh, k.diaChi, k.sdt) from KhachHang k where k.id = ?1")
    KhachHangResponse1 get(Long id);
}