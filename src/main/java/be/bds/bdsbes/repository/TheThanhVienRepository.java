package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.TheThanhVien;
import be.bds.bdsbes.service.dto.response.TheThanhVienResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheThanhVienRepository extends JpaRepository<TheThanhVien, Long> {

    @Query(value = "select ttv.id, ttv.ma, ttv.cap_bac, ttv.ngay_het_han, ttv.giam_gia, ttv.ghi_chu from the_thanh_vien ttv", nativeQuery = true)
    List<TheThanhVienResponse> getAllTTV();

}