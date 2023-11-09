package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.ChiTietPhongResponse1;
import be.bds.bdsbes.payload.PhongResponse1;
import be.bds.bdsbes.repository.PhongRepository;
import be.bds.bdsbes.service.IPhongService;
import be.bds.bdsbes.service.dto.PhongDTO;
import be.bds.bdsbes.service.dto.response.PhongResponse;
import be.bds.bdsbes.service.mapper.PhongMapper;
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
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PhongServiceImpl implements IPhongService {

    @Autowired
    PhongRepository phongRepository;
    @Autowired
    PhongMapper phongMapper;

    @Override
    public List<Phong> getList() {
        return phongRepository.findAll();
    }

    @Override
    public PagedResponse<PhongResponse1> searchRoom(int page, int size, String searchInput) throws ServiceException {
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.DESC, "id");
        Page<Phong> entities = phongRepository.searchRoom(pageable, searchInput);

        List<PhongResponse1> dtos = this.phongMapper.toDtoList(entities.getContent());
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
    public PhongResponse1 get(Long id) {
        return phongRepository.get(id);
    }

    @Override
    public Phong create(PhongDTO phongDTO) throws ServiceException{
        Phong phong = phongDTO.dto(new Phong());
        List<Phong> listP = phongRepository.findAll(Sort.by(Sort.Direction.ASC, "ma"));
        if (listP.size() == 0) {
            if(phongDTO.getIdLoaiPhong().doubleValue() < 0) {
                throw ServiceExceptionBuilderUtil.newBuilder()
                        .addError(new ValidationErrorResponse("giaPhong", ValidationErrorUtil.Max))
                        .build();
            }
            phong.setMa(String.valueOf(101));
            phong.setTrangThai(1);
            return phongRepository.save(phong);
        }
        if (Integer.valueOf(listP.get(listP.size() - 1).getMa()) % 10 == 0) {
            if(phongDTO.getIdLoaiPhong().doubleValue() < 0) {
                throw ServiceExceptionBuilderUtil.newBuilder()
                        .addError(new ValidationErrorResponse("giaPhong", ValidationErrorUtil.Max))
                        .build();
            }
            phong.setMa(String.valueOf(Integer.valueOf(listP.get(listP.size() - 1).getMa()) + 91));
            phong.setTrangThai(1);
            return phongRepository.save(phong);
        }
        if(phongDTO.getIdLoaiPhong().doubleValue() < 0) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("giaPhong", ValidationErrorUtil.Max))
                    .build();
        }
        phong.setMa(String.valueOf(Integer.valueOf(listP.get(listP.size() - 1).getMa()) + 1));
        phong.setTrangThai(1);
        return phongRepository.save(phong);
    }

    @Override
    public Phong update(PhongDTO phongDTO, Long id) {
        Optional<Phong> phongOptional = phongRepository.findById(id);
        if (phongOptional.isPresent()) {
            Phong phong = phongDTO.dto(phongOptional.get());
            return phongRepository.save(phong);
        }
        return null;
    }

    @Override
    public Integer updateTrangThai(Long id) {
        Phong phong = phongRepository.findById(id).get();
        if (phong.getTrangThai() == 0) {
            return phongRepository.updateTrangThaiById(1, id);
        }
        if (phong.getTrangThai() == 1) {
            return phongRepository.updateTrangThaiById(0, id);
        }
        return null;
    }

    @Override
    public PagedResponse<PhongResponse1> getPhong(int page, int size) throws ServiceException {
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
        Page<Phong> entities = phongRepository.findAll(pageable);

        List<PhongResponse1> dtos = this.phongMapper.toDtoList(entities.getContent());
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
    public PagedResponse<PhongResponse1> getPhongSortbyId(int page, int size) throws ServiceException {
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.DESC, "id");
        Page<Phong> entities = phongRepository.findAll(pageable);

        List<PhongResponse1> dtos = this.phongMapper.toDtoList(entities.getContent());
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
    public List<PhongResponse1> singleListRoom() {
        return phongRepository.singleListRoom();
    }

    @Override
    public PagedResponse<PhongResponse1> searchRoomManager(int page, int size, int soNguoi, LocalDate checkIn, LocalDate checkOut)  throws ServiceException {
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.DESC, "id");
        Page<Phong> entities = phongRepository.searchRoomManager(pageable, soNguoi, checkIn, checkOut);

        List<PhongResponse1> dtos = this.phongMapper.toDtoList(entities.getContent());
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
