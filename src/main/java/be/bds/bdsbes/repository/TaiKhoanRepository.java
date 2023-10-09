package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.TaiKhoan;
import be.bds.bdsbes.service.dto.response.TaiKhoanResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Long> {

    @Query(value = "select t.id, t.email, t.mat_khau, t.trang_thai, t.ten, t.id_khach_hang, k.ma from" +
            " tai_khoan t left join khach_hang k on t.id_khach_hang = k.id", nativeQuery = true)
    List<TaiKhoanResponse> getAllTaiKhoan();

    TaiKhoan findByEmail(String email);

    @Query("select t.email, t.ten, t.matKhau, t.trangThai from TaiKhoan t " +
            "where t.id = ?1")
    Optional<TaiKhoan> get(Long id);


}