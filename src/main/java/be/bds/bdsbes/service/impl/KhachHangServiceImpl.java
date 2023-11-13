package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.entities.TaiKhoan;
import be.bds.bdsbes.entities.TheThanhVien;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.KhachHangResponse1;
import be.bds.bdsbes.payload.TaiKhoanResponse1;
import be.bds.bdsbes.repository.KhachHangRepository;
import be.bds.bdsbes.repository.TaiKhoanRepository;
import be.bds.bdsbes.service.IKhachHangService;
import be.bds.bdsbes.service.dto.KhachHangDTO;
import be.bds.bdsbes.service.dto.TheThanhVienDTO;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
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

@Slf4j
@Service("khachHangServiceImpl")
public class KhachHangServiceImpl implements IKhachHangService {

    @Autowired
    KhachHangRepository khachHangRepository;
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
        khachHang.setTheThanhVien(TheThanhVien.builder().id(Long.parseLong("1")).build());
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
}
