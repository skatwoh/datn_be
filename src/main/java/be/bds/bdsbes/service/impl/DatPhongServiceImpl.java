package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.domain.User;
import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.entities.Voucher;
import be.bds.bdsbes.repository.DatPhongRepository;
import be.bds.bdsbes.service.IDatPhongService;
import be.bds.bdsbes.service.dto.DatPhongDTO;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DatPhongServiceImpl implements IDatPhongService {

    @Autowired
    DatPhongRepository datPhongRepository;

    @Override
    public List<DatPhong> getList() {
        return datPhongRepository.findAll();
    }

    @Override
    public List<DatPhongResponse> getAll() {
        return datPhongRepository.getAllDatPhong();
    }

    @Override
    public DatPhong getOne(Long id) {
        Optional<DatPhong> optionalDatPhong = datPhongRepository.findById(id);
        if(optionalDatPhong.isPresent()){
            DatPhong datPhong = optionalDatPhong.get();
            return datPhong;
        }
        return null;
    }

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
        datPhong.setVoucher(Voucher.builder().id(datPhongDTO.getIdVoucher()).build());
        datPhong.setUser(User.builder().id(datPhongDTO.getUserId()).build());
        return datPhongRepository.save(datPhong);
    }

    @Override
    public DatPhong update(DatPhongDTO datPhongDTO, Long id) {
        Optional<DatPhong> optionalDatPhong = datPhongRepository.findById(id);
        if(optionalDatPhong.isPresent()){
            DatPhong datPhong = optionalDatPhong.get();
            datPhong.setNgayDat(datPhongDTO.getNgayDat());
            datPhong.setCheckIn(datPhongDTO.getCheckIn());
            datPhong.setCheckOut(datPhongDTO.getCheckOut());
            datPhong.setSoNguoi(datPhongDTO.getSoNguoi());
            datPhong.setGhiChu(datPhongDTO.getGhiChu());
            datPhong.setTrangThai(datPhongDTO.getTrangThai());
            datPhong.setVoucher(Voucher.builder().id(datPhongDTO.getIdVoucher()).build());
            datPhong.setUser(User.builder().id(datPhongDTO.getUserId()).build());
            return datPhongRepository.save(datPhong);
        }
        return null;
    }
}
