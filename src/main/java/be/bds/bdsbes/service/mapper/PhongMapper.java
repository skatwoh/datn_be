package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.entities.LoaiPhong;
import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.payload.ChiTietPhongResponse1;
import be.bds.bdsbes.payload.PhongResponse1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PhongMapper extends EntityMapper<PhongResponse1, Phong>{

    @Mapping(target = "tenLoaiPhong", source = "loaiPhong.tenLoaiPhong")
    @Mapping(target = "idLoaiPhong", source = "loaiPhong.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PhongResponse1 toDto(Phong phong);
}
