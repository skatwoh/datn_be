package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.HoaDon;
import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.payload.HoaDonResponse;
import be.bds.bdsbes.payload.PhongResponse1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface HoaDonMapper extends EntityMapper<HoaDonResponse, HoaDon>{

//    @Mapping(target = "tenLoaiPhong", source = "loaiPhong.tenLoaiPhong")
    @Mapping(target = "idKhachHang", source = "khachHang.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    HoaDonResponse toDto(HoaDon hoaDon);
}
