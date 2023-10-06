package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.payload.PhongResponse1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PhongMapper extends EntityMapper<PhongResponse1, Phong>{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Phong partialUpdate(PhongResponse1 phongResponse1, @MappingTarget Phong phong);
}
