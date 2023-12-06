package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.ChiTietDatPhong;
import be.bds.bdsbes.repository.ChiTietDatPhongRepository;
import be.bds.bdsbes.service.iService.IChiTietDatPhongService;
import be.bds.bdsbes.service.dto.ChiTietDatPhongDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChiTietDatPhongServiceImpl implements IChiTietDatPhongService {

    @Autowired
    ChiTietDatPhongRepository chiTietDatPhongRepository;

    @Override
    public List<ChiTietDatPhong> getList() {
        return chiTietDatPhongRepository.findAll();
    }

    @Override
    public Page<ChiTietDatPhong> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return chiTietDatPhongRepository.findAll(pageable);
    }

    @Override
    public ChiTietDatPhong getOne(Long id) {
        Optional<ChiTietDatPhong> optionalChiTietDatPhong = chiTietDatPhongRepository.findById(id);
        if(optionalChiTietDatPhong.isPresent()){
            ChiTietDatPhong chiTietDatPhong = optionalChiTietDatPhong.get();
            return chiTietDatPhong;
        }
        return null;
    }

    @Override
    public ChiTietDatPhong create(ChiTietDatPhongDTO chiTietDatPhongDTO) {
        ChiTietDatPhong chiTietDatPhong = chiTietDatPhongDTO.dto(new ChiTietDatPhong());
        return chiTietDatPhongRepository.save(chiTietDatPhong);
    }

    @Override
    public ChiTietDatPhong update(ChiTietDatPhongDTO chiTietDatPhongDTO, Long id) {
        Optional<ChiTietDatPhong> optionalChiTietDatPhong = chiTietDatPhongRepository.findById(id);
        if(optionalChiTietDatPhong.isPresent()){
            ChiTietDatPhong chiTietDatPhong = chiTietDatPhongDTO.dto(optionalChiTietDatPhong.get());
            return chiTietDatPhongRepository.save(chiTietDatPhong);
        }
        return null;
    }
}
