package be.bds.bdsbes.service;


import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.service.dto.DatPhongDTO;

public interface IDatPhongService {
    DatPhong create(DatPhongDTO datPhongDTO);
}
