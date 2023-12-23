package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DatPhongMapper extends EntityMapper<DatPhongResponse, DatPhong> {

    @Mapping(target = "maKhachHang", source = "khachHang.ma")
    @Mapping(target = "hoTen", source = "khachHang.hoTen")
    @Mapping(target = "sdt", source = "khachHang.sdt")
    @Mapping(target = "tenPhong", source = "phong.ma")
    @Mapping(target = "giaPhong", source = "phong.giaPhong")
    @Mapping(target = "idHoaDon", source = "hoaDon.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DatPhongResponse toDto(DatPhong datPhong);
}
