package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.entities.ChiTietDichVu;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.ChiTietDichVuResponse1;
import be.bds.bdsbes.payload.DichVuResponse1;
import be.bds.bdsbes.service.dto.ChiTietDichVuDTO;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
import be.bds.bdsbes.utils.dto.PagedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IChiTietDichVuService {
    List<ChiTietDichVu> getList();

    Page<ChiTietDichVu> getPage(Integer page);

    ChiTietDichVu getOne(Long id);

    ChiTietDichVu create(ChiTietDichVuDTO chiTietDichVuDTO);

    ChiTietDichVu update(ChiTietDichVuDTO chiTietDichVuDTO, Long id);

    PagedResponse<ChiTietDichVuResponse1> getAccounts(int page, int size) throws ServiceException;

    PagedResponse<ChiTietDichVuResponse1> getAllByDatPhong(int page, int size, Long id) throws ServiceException;
}
