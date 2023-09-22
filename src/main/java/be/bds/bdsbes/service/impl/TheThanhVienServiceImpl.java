package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.TheThanhVien;
import be.bds.bdsbes.repository.TheThanhVienRepository;
import be.bds.bdsbes.service.TheThanhVienService;
import be.bds.bdsbes.service.dto.TheThanhVienDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("theThanhVienServiceImpl")
public class TheThanhVienServiceImpl implements TheThanhVienService {

    @Autowired
    private TheThanhVienRepository theThanhVienRepository;

    @Override
    public List<TheThanhVien> getList() {
        return theThanhVienRepository.findAll();
    }

    @Override
    public Page<TheThanhVien> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return theThanhVienRepository.findAll(pageable);
    }

    @Override
    public TheThanhVien getOne(Long id) {
        Optional<TheThanhVien> theThanhVienOptional = theThanhVienRepository.findById(id);
        if(theThanhVienOptional.isPresent()){
            TheThanhVien theThanhVien = theThanhVienOptional.get();
            return theThanhVien;
        }
        return null;
    }

    @Override
    public TheThanhVien add(TheThanhVienDTO theThanhVienDTO) {
        TheThanhVien theThanhVien = theThanhVienDTO.dto(new TheThanhVien());
        return theThanhVienRepository.save(theThanhVien);
    }

    @Override
    public TheThanhVien update(TheThanhVienDTO theThanhVienDTO, Long id) {
        Optional<TheThanhVien> theThanhVienOptional = theThanhVienRepository.findById(id);
        if(theThanhVienOptional.isPresent()){
            TheThanhVien theThanhVien = theThanhVienDTO.dto(theThanhVienOptional.get());
            return theThanhVienRepository.save(theThanhVien);
        }
        return null;
    }
}
