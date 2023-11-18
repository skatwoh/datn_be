package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.LoaiPhong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.LoaiPhongResponse1;
import be.bds.bdsbes.repository.LoaiPhongRepository;
import be.bds.bdsbes.service.ILoaiPhongService;
import be.bds.bdsbes.service.dto.LoaiPhongDTO;
import be.bds.bdsbes.service.dto.response.LoaiPhongResponse;
import be.bds.bdsbes.service.mapper.LoaiPhongMapper;
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
@Service
public class LoaiPhongServiceImpl implements ILoaiPhongService {

    @Autowired
    LoaiPhongRepository loaiPhongRepository;

    @Autowired
    private LoaiPhongMapper loaiPhongMapper;

    public int getNumberOfRecords() {
        Long count = loaiPhongRepository.count();
        return count.intValue();
    }


    private String generateAutoCode() {
        int numberOfDigits = 2;

        int numberOfExistingRecords = getNumberOfRecords();

        String autoCode = "LP" + String.format("%0" + numberOfDigits + "d", numberOfExistingRecords + 1);

        return autoCode;
    }


    @Override
    public List<LoaiPhong> getList() {
        return loaiPhongRepository.findAll();
    }

    @Override
    public List<LoaiPhongResponse1> singleListRoomType() {
        return loaiPhongRepository.singleListRoomType();
    }

    @Override
    public Page<LoaiPhong> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return loaiPhongRepository.findAll(pageable);
    }

    @Override
    public LoaiPhongResponse1 get(Long id) {
        return loaiPhongRepository.get(id);
    }

    @Override
    public LoaiPhong create(LoaiPhongDTO loaiPhongDTO) {
        LoaiPhong loaiPhong = loaiPhongDTO.dto(new LoaiPhong());
        loaiPhong.setMaLoaiPhong(generateAutoCode());
        return loaiPhongRepository.save(loaiPhong);
    }

    @Override
    public LoaiPhong update(LoaiPhongDTO loaiPhongDTO, Long id) {
        Optional<LoaiPhong> optionalLoaiPhong = loaiPhongRepository.findById(id);
        if(optionalLoaiPhong.isPresent()){
            LoaiPhong loaiPhong = loaiPhongDTO.dto(optionalLoaiPhong.get());
            return loaiPhongRepository.save(loaiPhong);
        }
        return null;
    }

    @Override
    public PagedResponse<LoaiPhongResponse1> getLoaiPhong(int page, int size) throws ServiceException {
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
        Page<LoaiPhong> entities = loaiPhongRepository.findAll(pageable);

        return new PagedResponse<>(
                this.loaiPhongMapper.toDtoList(entities.getContent()),
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }
}
