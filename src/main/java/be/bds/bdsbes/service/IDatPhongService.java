package be.bds.bdsbes.service;


import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.service.dto.DatPhongDTO;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;

import java.util.List;

public interface IDatPhongService {

    List<DatPhong> getList();

    List<DatPhongResponse> getAll();
    DatPhong getOne(Long id);
    DatPhong create(DatPhongDTO datPhongDTO);

    DatPhong update(DatPhongDTO datPhongDTO, Long id);
}
