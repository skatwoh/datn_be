package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.FeedBack;
import be.bds.bdsbes.service.dto.response.FeedbackResponse;
import be.bds.bdsbes.service.dto.response.TheThanhVienResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<FeedBack, Long> {
    @Query(value = "select feb.id, feb.id_chi_tiet_phong, feb.id_khach_hang, feb.mo_ta, feb.trang_thai from feed_back feb", nativeQuery = true)
    List<FeedbackResponse> getAllFeb();
 }