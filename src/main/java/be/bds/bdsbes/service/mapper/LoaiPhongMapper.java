package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.LoaiPhong;
import be.bds.bdsbes.payload.LoaiPhongResponse1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface LoaiPhongMapper extends EntityMapper<LoaiPhongResponse1, LoaiPhong> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    LoaiPhong partialUpdate(LoaiPhongResponse1 loaiPhongResponse1, @MappingTarget LoaiPhong loaiPhong);
}
