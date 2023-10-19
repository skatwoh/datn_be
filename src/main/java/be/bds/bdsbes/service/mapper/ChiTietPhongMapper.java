package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.payload.ChiTietPhongResponse1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ChiTietPhongMapper extends EntityMapper<ChiTietPhongResponse1, ChiTietPhong> {

    @Mapping(target = "tenLoaiPhong", source = "idLoaiPhong.tenLoaiPhong")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ChiTietPhongResponse1 toDto(ChiTietPhong chiTietPhong);
}

