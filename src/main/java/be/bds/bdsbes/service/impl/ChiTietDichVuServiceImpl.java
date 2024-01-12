package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.ChiTietDichVu;
import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.ChiTietDichVuResponse1;
import be.bds.bdsbes.repository.ChiTietDichVuRepository;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
import be.bds.bdsbes.service.iService.IChiTietDichVuService;
import be.bds.bdsbes.service.dto.ChiTietDichVuDTO;
import be.bds.bdsbes.service.mapper.ChiTietDichVuMapper;
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
@Service("ChiTietDichVuServiceImpl")
public class ChiTietDichVuServiceImpl implements IChiTietDichVuService {
    @Autowired
    ChiTietDichVuRepository chiTietDichVuRepository;
    @Autowired
    private ChiTietDichVuMapper chiTietDichVuMapper;

    @Override
    public List<ChiTietDichVu> getList() {
        return chiTietDichVuRepository.findAll();
    }

    @Override
    public Page<ChiTietDichVu> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return chiTietDichVuRepository.findAll(pageable);
    }

    @Override
    public ChiTietDichVu getOne(Long id) {
        Optional<ChiTietDichVu> chitietdichVuOptional = chiTietDichVuRepository.findById(id);
        if(chitietdichVuOptional.isPresent()){
            ChiTietDichVu chiTietDichVu = chitietdichVuOptional.get();
            return chiTietDichVu;
        }
        return null;
    }

    @Override
    public ChiTietDichVu create(ChiTietDichVuDTO chiTietDichVuDTO) {
       ChiTietDichVu chiTietDichVu = chiTietDichVuDTO.dto(new ChiTietDichVu());
        return chiTietDichVuRepository.save(chiTietDichVu);
    }

    @Override
    public ChiTietDichVu update(ChiTietDichVuDTO chiTietDichVuDTO, Long id) {
        Optional<ChiTietDichVu> chitietdichVuOptional = chiTietDichVuRepository.findById(id);
        if(chitietdichVuOptional.isPresent()){
            ChiTietDichVu chiTietDichVu = chiTietDichVuDTO.dto(chitietdichVuOptional.get());
            return chiTietDichVuRepository.save(chiTietDichVu);
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
    public PagedResponse<ChiTietDichVuResponse1> getAccounts(int page, int size) throws ServiceException {
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
        Page<ChiTietDichVu> entities = chiTietDichVuRepository.findAll(pageable);

        return new PagedResponse<>(
                this.chiTietDichVuMapper.toDtoList(entities.getContent()),
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }

    @Override
    public PagedResponse<ChiTietDichVuResponse1> getAllByDatPhong(int page, int size, Long id) throws ServiceException {
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
        Page<ChiTietDichVu> entities = chiTietDichVuRepository.getAllByDatPhong(pageable, id);

        List<ChiTietDichVuResponse1> dtos = this.chiTietDichVuMapper.toDtoList(entities.getContent());

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
