package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.DichVu;
import be.bds.bdsbes.entities.DuAn;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.DichVuResponse1;
import be.bds.bdsbes.payload.DuAnResponse1;
import be.bds.bdsbes.repository.DichVuRepository;
import be.bds.bdsbes.service.IDichVuService;
import be.bds.bdsbes.service.dto.DichVuDTO;
import be.bds.bdsbes.service.dto.response.DichVuResponse;
import be.bds.bdsbes.service.mapper.DichVuMapper;
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
@Service("DichVuServiceImpl")
public class DichVuServiceImpl implements IDichVuService {
    @Autowired
    private  DichVuRepository dichVuRepository;
    @Autowired
    private DichVuMapper dichVuMapper;

    @Override
    public List<DichVuResponse> getList() {
        return dichVuRepository.getAllDv();
    }
    @Override
    public DichVuResponse1 getDichVu(Long id) {
        return dichVuRepository.getDichVu(id);
    }

    @Override
    public Page<DichVu> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return dichVuRepository.findAll(pageable);
    }


    @Override
    public DichVu create(DichVuDTO dichVuDTO) {
        DichVu dichVu = dichVuDTO.dto(new DichVu());
        return dichVuRepository.save(dichVu);
    }
    @Override
    public Integer updateTrangThai(Long id) {
        DichVu dichVu = dichVuRepository.findById(id).get();
        if (dichVu.getTrangThai() == 0) {
            return dichVuRepository.updateTrangThaiById(1, id);
        }
        if (dichVu.getTrangThai() == 1) {
            return dichVuRepository.updateTrangThaiById(0, id);
        }
        return null;
    }

    @Override
    public DichVu update(DichVuDTO dichVuDTO, Long id) {
        Optional<DichVu> dichVuOptional = dichVuRepository.findById(id);
        if(dichVuOptional.isPresent()){
            DichVu dichVu = dichVuDTO.dto(dichVuOptional.get());
            return dichVuRepository.save(dichVu);
        }
        return null;
    }
    /**
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    @Override
    public PagedResponse<DichVuResponse1> getAccounts(int page, int size) throws ServiceException {
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
        Page<DichVu> entities = dichVuRepository.findAll(pageable);

        return new PagedResponse<>(
                this.dichVuMapper.toDtoList(entities.getContent()),
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }

    @Override
    public PagedResponse<DichVuResponse1> searchRoomService(int page, int size, String searchInput) throws ServiceException {
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.DESC, "id");
        Page<DichVu> entities = dichVuRepository.searchRoomService(pageable, searchInput);

        List<DichVuResponse1> dtos = this.dichVuMapper.toDtoList(entities.getContent());
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

