package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.payload.ChiTietPhongResponse1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ChiTietPhongRepository extends JpaRepository<ChiTietPhong, Long> {

    @Query("select new be.bds.bdsbes.payload.ChiTietPhongResponse1(c.id, c.tang, c.tienIch, c.dichVu, c.soLuongNguoi, c.dienTich, c.trangThai, l.id, l.tenLoaiPhong) from ChiTietPhong c left join c.idLoaiPhong l where c.id = ?1")
    ChiTietPhongResponse1 get(Long id);

}