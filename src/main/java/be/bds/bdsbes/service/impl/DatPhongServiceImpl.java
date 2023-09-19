package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.repository.DatPhongRepository;
import be.bds.bdsbes.service.IDatPhongService;
import be.bds.bdsbes.service.dto.DatPhongDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DatPhongServiceImpl implements IDatPhongService {

    @Autowired
    DatPhongRepository datPhongRepository;

    /**
     * Creates a new `DatPhong` object based on the provided `DatPhongDTO`.
     *
     * @param  datPhongDTO  the `DatPhongDTO` object containing the data for creating the `DatPhong` object
     * @return              the created `DatPhong` object
     */
    @Override
    public DatPhong create(DatPhongDTO datPhongDTO) {
        DatPhong datPhong = new DatPhong();
        datPhong.setNgayDat(datPhongDTO.getNgayDat());
        datPhong.setCheckIn(datPhongDTO.getCheckIn());
        datPhong.setCheckOut(datPhongDTO.getCheckOut());
        datPhong.setSoNguoi(datPhongDTO.getSoNguoi());
        datPhong.setGhiChu(datPhongDTO.getGhiChu());
        datPhong.setTrangThai(datPhongDTO.getTrangThai());
        return datPhongRepository.save(datPhong);
    }
}
