package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.payload.ChiTietPhongResponse1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ChiTietPhongRepository extends JpaRepository<ChiTietPhong, Long> {

    @Query("select new be.bds.bdsbes.payload.ChiTietPhongResponse1(c.id, c.tang, c.tienIch, c.dichVu, c.soLuongNguoi, c.dienTich, c.trangThai, l.id, l.ma) from ChiTietPhong c left join c.phong l where c.id = ?1")
    ChiTietPhongResponse1 get(Long id);

    @Query("select p from ChiTietPhong p where p.tang like concat('%', ?1, '%') or p.phong.ma like concat('%', ?1, '%') " +
            "or p.tienIch like concat('%', ?1, '%') or p.dichVu like concat('%', ?1, '%')")
    Page<ChiTietPhong> searchRoomInformation(Pageable pageable, String inputSearch);

    @Transactional
    @Modifying
    @Query("UPDATE ChiTietPhong p SET p.trangThai = :trangThai WHERE p.id = :id")
    Integer updateTrangThaiById(int trangThai, Long id);

}