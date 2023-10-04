package be.bds.bdsbes.service;

import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.ChiTietPhongResponse1;
import be.bds.bdsbes.service.dto.ChiTietPhongDTO;
import be.bds.bdsbes.utils.dto.PagedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IChiTietPhongService {

    List<ChiTietPhong> getList();

    Page<ChiTietPhong> getPage(Integer page);

    ChiTietPhong getOne(Long id);

    ChiTietPhong create(ChiTietPhongDTO chiTietPhongDTO);

    ChiTietPhong update(ChiTietPhongDTO chiTietPhongDTO, Long id);

    PagedResponse<ChiTietPhongResponse1> getChiTietPhong(int page, int size) throws ServiceException;
}
