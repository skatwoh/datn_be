package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.payload.RoomMappingChiTietPhong;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RoomMappingCTPMapper extends EntityMapper<RoomMappingChiTietPhong, Phong> {

    @Mapping(target = "tenLoaiPhong", source = "loaiPhong.tenLoaiPhong")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RoomMappingChiTietPhong toDto(Phong phong);

    @AfterMapping
    default void mapTang(Phong phong, @MappingTarget RoomMappingChiTietPhong roomDto) {
        if (phong.getChiTietPhongs() != null && !phong.getChiTietPhongs().isEmpty()) {
            roomDto.setTang(phong.getChiTietPhongs().iterator().next().getTang());
        }
    }

//    @AfterMapping
//    default void mapSoLuong(Phong phong, @MappingTarget RoomMappingChiTietPhong roomDto) {
//        if (phong.getChiTietPhongs() != null && !phong.getChiTietPhongs().isEmpty()) {
//            roomDto.setSoLuong(phong.getChiTietPhongs().iterator().next().getSoLuongNguoi());
//        }
//    }
}
