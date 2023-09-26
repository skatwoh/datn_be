package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.BaoTri;
import be.bds.bdsbes.service.dto.BaoTriDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BaoTriMapper extends EntityMapper<BaoTriDto, BaoTri> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BaoTri partialUpdate(BaoTriDto baoTriDto, @MappingTarget BaoTri baoTri);
}