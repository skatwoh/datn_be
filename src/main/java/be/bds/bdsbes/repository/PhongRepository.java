package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.payload.PhongResponse1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PhongRepository extends JpaRepository<Phong, Long> {

    @Query("select new be.bds.bdsbes.payload.PhongResponse1(p.id, p.ma, p.giaPhong, p.trangThai, l.id, l.tenLoaiPhong) from Phong p inner join p.loaiPhong l where p.id = ?1")
    PhongResponse1 get(Long id);

    @Query("select new be.bds.bdsbes.payload.PhongResponse1(p.id, p.ma, p.giaPhong, p.trangThai, l.id, l.tenLoaiPhong) from Phong p inner join p.loaiPhong l")
    List<PhongResponse1> singleListRoom();

    @Query("select p from Phong p where p.ma like CONCAT('%', ?1, '%') or p.loaiPhong.tenLoaiPhong like CONCAT('%',?1, '%') order by p.ma asc")
    Page<Phong> searchRoom(Pageable pageable, String searchInput);

    @Transactional
    @Modifying
    @Query("UPDATE Phong p SET p.trangThai = :trangThai WHERE p.id = :id")
    Integer updateTrangThaiById(int trangThai, Long id);
}