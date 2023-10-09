package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.DuAn;
import be.bds.bdsbes.payload.DuAnResponse1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DuAnMapper extends EntityMapper<DuAnResponse1, DuAn>{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DuAn partialUpdate(DuAnResponse1 duAnResponse1, @MappingTarget DuAn duAn);
}
