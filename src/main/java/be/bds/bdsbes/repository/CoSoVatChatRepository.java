package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.CoSoVatChat;
import be.bds.bdsbes.service.dto.response.CoSoVatChatResponse;
import be.bds.bdsbes.service.dto.response.TaiKhoanResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CoSoVatChatRepository extends JpaRepository<CoSoVatChat, Long> {
    @Query(value = "select csvc.id, csvc.id_tai_san, csvc.so_luong, csvc.ghi_chu, csvc.id_chi_tiet_phong, csvc.trang_thai from" +
            " co_so_vat_chat csvc left join tai_san ts on csvc.id_tai_san = ts.id" +
            "co_so_vat_chat csvc left join chi_tiet_phong ctp on csvc.id_chi_tiet_phongn = ctp.id", nativeQuery = true)
    List<CoSoVatChatResponse> getAllCoSoVatChat();
}