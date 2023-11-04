package be.bds.bdsbes.service;


import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.TaiKhoanResponse1;
import be.bds.bdsbes.service.dto.DatPhongDTO;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
import be.bds.bdsbes.utils.dto.PagedResponse;

import java.util.List;

public interface IDatPhongService {

    List<DatPhong> getList();

    List<DatPhongResponse> getAll();
    DatPhong getOne(Long id);
    DatPhong create(DatPhongDTO datPhongDTO);

    DatPhong update(DatPhongDTO datPhongDTO, Long id);

    PagedResponse<DatPhongResponse> getRoomOrder(int page, int size) throws ServiceException;
}
