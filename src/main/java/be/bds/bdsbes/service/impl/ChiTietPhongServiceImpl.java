package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.ChiTietPhongResponse1;
import be.bds.bdsbes.payload.PhongResponse1;
import be.bds.bdsbes.repository.ChiTietPhongRepository;
import be.bds.bdsbes.repository.PhongRepository;
import be.bds.bdsbes.service.iService.IChiTietPhongService;
import be.bds.bdsbes.service.dto.ChiTietPhongDTO;
import be.bds.bdsbes.service.mapper.ChiTietPhongMapper;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class ChiTietPhongServiceImpl implements IChiTietPhongService {

    @Autowired
    private ChiTietPhongRepository chiTietPhongRepository;

    @Autowired
    private ChiTietPhongMapper chiTietPhongMapper;

    @Autowired
    private PhongMapper phongMapper;

    @Autowired
    PhongRepository phongRepository;

    @Override
    public List<ChiTietPhong> getList() {
        return chiTietPhongRepository.findAll();
    }

    @Override
    public PagedResponse<ChiTietPhongResponse1> searchRoom(int page, int size, String searchInput) throws ServiceException {
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.ASC, "id");
        Page<ChiTietPhong> entities = chiTietPhongRepository.searchRoomInformation(pageable, searchInput);

        List<ChiTietPhongResponse1> dtos = this.chiTietPhongMapper.toDtoList(entities.getContent());
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
    public ChiTietPhongResponse1 get(Long id) {
        return chiTietPhongRepository.get(id);
    }

    @Override
    public ChiTietPhong create(ChiTietPhongDTO chiTietPhongDTO) {
        ChiTietPhong chiTietPhong = chiTietPhongDTO.dto(new ChiTietPhong());
        Phong phong = phongRepository.findById(chiTietPhongDTO.getIdPhong()).get();
        chiTietPhong.setTang(String.valueOf(Integer.valueOf(phong.getMa().substring(0, 1))));
        return chiTietPhongRepository.save(chiTietPhong);
    }

    @Override
    public ChiTietPhong update(ChiTietPhongDTO chiTietPhongDTO, Long id) {
        Optional<ChiTietPhong> optionalChiTietPhong = chiTietPhongRepository.findById(id);
        if (optionalChiTietPhong.isPresent()) {
            ChiTietPhong chiTietPhong = chiTietPhongDTO.dto(optionalChiTietPhong.get());
            return chiTietPhongRepository.save(chiTietPhong);
        }
        return null;
    }

    @Override
    public Integer updateTrangThai(Long id) {
        ChiTietPhong phong = chiTietPhongRepository.findById(id).get();
        if (phong.getTrangThai() == 0) {
            return chiTietPhongRepository.updateTrangThaiById(1, id);
        }
        if (phong.getTrangThai() == 1) {
            return chiTietPhongRepository.updateTrangThaiById(0, id);
        }
        return null;
    }

    @Override
    public PagedResponse<ChiTietPhongResponse1> getChiTietPhong(int page, int size) throws ServiceException {
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
        Page<ChiTietPhong> entities = chiTietPhongRepository.findAll(pageable);

        List<ChiTietPhongResponse1> dtos = this.chiTietPhongMapper.toDtoList(entities.getContent());
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
    public PagedResponse<ChiTietPhongResponse1> getChiTietPhongSortbyId(int page, int size) throws ServiceException {

        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.DESC, "id");
        Page<ChiTietPhong> entities = chiTietPhongRepository.findAll(pageable);

        List<ChiTietPhongResponse1> dtos = this.chiTietPhongMapper.toDtoList(entities.getContent());
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
    public ChiTietPhongResponse1 getCTP(Long idPhong) {
        return chiTietPhongRepository.getCTP(idPhong);
    }

    @Override
    public PagedResponse<ChiTietPhongResponse1> searchRoomBySoNguoi(int page, int size, Integer soLuongNguoi, Integer soPhong) {
//        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.DESC, "id");
//
//        Page<ChiTietPhong> entities = chiTietPhongRepository.findPhongBySoNguoi(soLuongNguoi, pageable);
//        List<PhongResponse1> listPhong = new ArrayList<>();
//        entities.getContent().sort(Comparator.comparing(ChiTietPhong::getSoLuongNguoi).reversed());
//        int remainingPeople = soLuongNguoi;
//        for (ChiTietPhong chiTietPhong : entities.getContent()) {
//            Phong phong = chiTietPhong.getPhong();
//            int availableCapacity = chiTietPhong.getSoLuongNguoi();
//            int people = Math.min(remainingPeople, availableCapacity);
//
//            if (people > 0) {
//                chiTietPhong.setSoLuongNguoi(chiTietPhong.getSoLuongNguoi() - people);
//                remainingPeople -= people;
//                PhongResponse1 phongResponse1 = phongMapper.toDto(phong);
//                listPhong.add(phongResponse1);
//            }
//        }
//
//        List<Phong> dtos = entities.map(ChiTietPhong::getPhong).getContent();
//        return new PagedResponse<>(
//                listPhong,
//                page,
//                size,
//                entities.getTotalElements(),
//                entities.getTotalPages(),
//                entities.isLast(),
//                entities.getSort().toString(),
//                dtos
//        );
        return null;
    }
}
