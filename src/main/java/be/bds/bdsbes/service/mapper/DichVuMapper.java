package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.DichVu;
import be.bds.bdsbes.entities.DuAn;
import be.bds.bdsbes.payload.DichVuResponse1;
import be.bds.bdsbes.payload.DuAnResponse1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DichVuMapper extends EntityMapper<DichVuResponse1, DichVu>{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DichVu partialUpdate(DichVuResponse1 dichVuResponse1, @MappingTarget DichVu dichVu);
}
