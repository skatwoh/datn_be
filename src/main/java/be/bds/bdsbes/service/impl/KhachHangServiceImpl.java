package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.entities.TheThanhVien;
import be.bds.bdsbes.repository.KhachHangRepository;
import be.bds.bdsbes.repository.TaiKhoanRepository;
import be.bds.bdsbes.service.IKhachHangService;
import be.bds.bdsbes.service.dto.KhachHangDTO;
import be.bds.bdsbes.service.dto.TheThanhVienDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("khachHangServiceImpl")
public class KhachHangServiceImpl implements IKhachHangService {

    @Autowired
    KhachHangRepository khachHangRepository;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    TheThanhVienDTO theThanhVienDTO;

    @Override
    public List<KhachHang> getList() {
        return khachHangRepository.findAll();
    }

    @Override
    public Page<KhachHang> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return khachHangRepository.findAll(pageable);
    }

    @Override
    public KhachHang getOne(Long id) {
        Optional<KhachHang> khachHangOptional = khachHangRepository.findById(id);
        if(khachHangOptional.isPresent()){
            KhachHang khachHang = khachHangOptional.get();
            return khachHang;
        }
        return null;
    }

    @Override
    public KhachHang create(KhachHangDTO khachHangDTO) {
        KhachHang khachHang = khachHangDTO.dto(new KhachHang());
        khachHang.setIdTheThanhVien(TheThanhVien.builder().id(Long.parseLong("1")).build());
        return khachHangRepository.save(khachHang);
    }

    @Override
    public KhachHang update(KhachHangDTO khachHangDTO, Long id) {
        Optional<KhachHang> khachHangOptional = khachHangRepository.findById(id);
        if(khachHangOptional.isPresent()){
            KhachHang khachHang = khachHangDTO.dto(khachHangOptional.get());
            return khachHangRepository.save(khachHang);
        }
        return null;
    }
}
