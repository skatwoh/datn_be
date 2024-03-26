package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.payload.ChiTietPhongResponse1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ChiTietPhongRepository extends JpaRepository<ChiTietPhong, Long> {

    @Query("select new be.bds.bdsbes.payload.ChiTietPhongResponse1(c.id, c.tang, c.dichVu, c.dienTich, c.trangThai, l.id, l.ma, l.giaPhong, l.image) from ChiTietPhong c left join c.phong l where c.id = ?1")
    ChiTietPhongResponse1 get(Long id);

    @Query("select p from ChiTietPhong p where p.tang like concat('%', ?1, '%') or p.phong.ma like concat('%', ?1, '%') " +
            " or p.dichVu like concat('%', ?1, '%')")
    Page<ChiTietPhong> searchRoomInformation(Pageable pageable, String inputSearch);

    @Transactional
    @Modifying
    @Query("UPDATE ChiTietPhong p SET p.trangThai = :trangThai WHERE p.id = :id")
    Integer updateTrangThaiById(int trangThai, Long id);

    @Query("select new be.bds.bdsbes.payload.ChiTietPhongResponse1(c.id, c.tang, c.dichVu, c.dienTich, c.trangThai, l.id, l.ma, l.giaPhong, l.image) from ChiTietPhong c left join c.phong l where l.id = ?1")
    ChiTietPhongResponse1 getCTP(Long idPhong);

//    @Query("select ctp from ChiTietPhong ctp join Phong p on p.id = ctp.phong.id where ctp.soLuongNguoi >= :soNguoi")
//    Page<ChiTietPhong> findPhongBySoNguoi(@Param("soNguoi") Integer songuoi, Pageable pageable);

    @Query("select ctp.id from ChiTietPhong ctp join Phong p on ctp.phong.id = p.id where p.id = ?1")
    Long findByIdCTP(Long id);

}