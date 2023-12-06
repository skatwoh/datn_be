package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.entities.ChiTietDatPhong;
import be.bds.bdsbes.service.dto.ChiTietDatPhongDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IChiTietDatPhongService {

    List<ChiTietDatPhong> getList();

    Page<ChiTietDatPhong> getPage(Integer page);

    ChiTietDatPhong getOne(Long id);

    ChiTietDatPhong create(ChiTietDatPhongDTO chiTietDatPhongDTO);

    ChiTietDatPhong update(ChiTietDatPhongDTO chiTietDatPhongDTO, Long id);
}
