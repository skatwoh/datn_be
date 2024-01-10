package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.domain.User;
import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.entities.TheThanhVien;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.KhachHangResponse1;
import be.bds.bdsbes.repository.KhachHangRepository;
import be.bds.bdsbes.repository.UserRepository;
import be.bds.bdsbes.service.iService.IKhachHangService;
import be.bds.bdsbes.service.dto.KhachHangDTO;
import be.bds.bdsbes.service.dto.TheThanhVienDTO;
import be.bds.bdsbes.service.mapper.KhachHangMapper;
import be.bds.bdsbes.utils.AppConstantsUtil;
import be.bds.bdsbes.utils.ServiceExceptionBuilderUtil;
import be.bds.bdsbes.utils.ValidationErrorUtil;
import be.bds.bdsbes.utils.dto.KeyValue;
import be.bds.bdsbes.utils.dto.PagedResponse;
import be.bds.bdsbes.utils.dto.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service("khachHangServiceImpl")
public class KhachHangServiceImpl implements IKhachHangService {

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private KhachHangMapper khachHangMapper;

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
    public KhachHangResponse1 getOne(Long id) {
        return khachHangRepository.get(id);
    }

    @Override
    public KhachHang create(KhachHangDTO khachHangDTO) {
        Random random = new Random();
        int min = 1;
        int max = Integer.MAX_VALUE;
        int ma = random.nextInt(max - min + 1) + min;
        KhachHang khachHang = khachHangDTO.dto(new KhachHang());
        khachHang.setMa("KH" + ma);
        khachHang.setHoTen(khachHangDTO.getHoTen());
        khachHang.setCccd(khachHangDTO.getCccd());
        khachHang.setSdt(khachHangDTO.getSdt());
        khachHang.setTheThanhVien(TheThanhVien.builder().id(Long.parseLong("1")).build());
        return khachHangRepository.save(khachHang);
    }

    @Override
    public KhachHang update(KhachHangDTO khachHangDTO, Long id) {
        Optional<KhachHang> khachHangOptional = khachHangRepository.findById(id);
        if(khachHangOptional.isPresent()){
            KhachHang khachHang = khachHangDTO.dto(khachHangOptional.get());
            User user = userRepository.getUserByKhachHang(khachHang.getId());
            user.setSdt(khachHangDTO.getSdt());
            user.setName(khachHangDTO.getHoTen());
            this.userRepository.save(user);
            return khachHangRepository.save(khachHang);
        }
        return null;
    }

    @Override
    public PagedResponse<KhachHangResponse1> getKhachHang(int page, int size) throws ServiceException {
        if (page <= 0) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("page", ValidationErrorUtil.Invalid))
                    .build();
        }

        if (size > AppConstantsUtil.MAX_PAGE_SIZE) {
            List<KeyValue> params = new ArrayList<>();
            params.add(new KeyValue("max", String.valueOf(AppConstantsUtil.MAX_PAGE_SIZE)));

            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("pageSize", ValidationErrorUtil.Invalid, params))
                    .build();
        }

        // Retrieve all entities
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.ASC, "id");
        Page<KhachHang> entities = khachHangRepository.findAll(pageable);
        List<KhachHangResponse1> dtos = this.khachHangMapper.toDtoList(entities.getContent());

        return new PagedResponse<>(
                dtos,
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }

    @Override
    public Boolean createOrUpdate(KhachHangDTO khachHangDTO) throws ServiceException{
        for(KhachHang kh: khachHangRepository.findAll()){
            if(kh.getCccd().trim().equals(khachHangDTO.getCccd().trim())){
                kh.setSdt(khachHangDTO.getSdt());
                kh.setHoTen(khachHangDTO.getHoTen());
                kh.setCccd(khachHangDTO.getCccd());
                this.khachHangRepository.save(kh);
                return true;
            }
        }
        Random random = new Random();
        int min = 1;
        int max = Integer.MAX_VALUE;
        int ma = random.nextInt(max - min + 1) + min;
        KhachHang khachHang = khachHangDTO.dto(new KhachHang());
        khachHang.setMa("KH" + ma);
        khachHang.setHoTen(khachHangDTO.getHoTen());
        khachHang.setCccd(khachHangDTO.getCccd());
        khachHang.setSdt(khachHangDTO.getSdt());
        khachHang.setTheThanhVien(TheThanhVien.builder().id(Long.parseLong("1")).build());
        this.khachHangRepository.save(khachHang);
        return true;
    }

    @Override
    public Long findIdByCCCD(String cccd) {
        return khachHangRepository.findIdByCccd(cccd);
    }

    @Override
    public KhachHang updateKH(KhachHangDTO khachHangDTO, Long id) {
        Long idKH = khachHangRepository.findByI(id);
        Optional<KhachHang> khachHangOptional = khachHangRepository.findById(idKH);
        if(khachHangOptional.isPresent()){
            KhachHang khachHang = khachHangDTO.dto(khachHangOptional.get());
            khachHang.setHoTen(khachHangDTO.getHoTen());
            khachHang.setCccd(khachHangDTO.getCccd());
            khachHang.setSdt(khachHangDTO.getSdt());
            khachHang.setGioiTinh(khachHangDTO.getGioiTinh());
            khachHang.setNgaySinh(khachHangDTO.getNgaySinh());
            khachHang.setGhiChu(khachHangDTO.getGhiChu());
            return khachHangRepository.save(khachHang);
        }
        return null;
    }


}
