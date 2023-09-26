package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.TaiKhoan;
import be.bds.bdsbes.repository.TaiKhoanRepository;
import be.bds.bdsbes.service.ITaiKhoanService;
import be.bds.bdsbes.service.dto.TaiKhoanDTO;
import be.bds.bdsbes.service.dto.response.TaiKhoanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("taiKhoanServiceImpl")
public class TaiKhoanServiceImpl implements ITaiKhoanService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Override
    public List<TaiKhoan> getList() {
        return taiKhoanRepository.findAll();
    }

    @Override
    public List<TaiKhoanResponse> getAllTaiKhoan() {
        return taiKhoanRepository.getAllTaiKhoan();
    }

    @Override
    public Page<TaiKhoan> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return taiKhoanRepository.findAll(pageable);
    }

    @Override
    public TaiKhoan getOne(Long id) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanRepository.findById(id);
        if(taiKhoanOptional.isPresent()){
            TaiKhoan taiKhoan = taiKhoanOptional.get();
            return taiKhoan;
        }
        return null;
    }

    @Override
    public TaiKhoan add(TaiKhoanDTO taiKhoanDTO) {
        TaiKhoan taiKhoan = taiKhoanDTO.dto(new TaiKhoan());
        return taiKhoanRepository.save(taiKhoan);
    }

    @Override
    public TaiKhoan update(TaiKhoanDTO taiKhoanDTO, Long id) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanRepository.findById(id);
        if(taiKhoanOptional.isPresent()){
            TaiKhoan taiKhoan = taiKhoanDTO.dto(taiKhoanOptional.get());
            return taiKhoan;
        }
        return null;
    }

}
