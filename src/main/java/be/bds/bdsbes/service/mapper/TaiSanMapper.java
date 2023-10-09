package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.TaiSan;
import be.bds.bdsbes.payload.TaiSanResponse1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TaiSanMapper extends EntityMapper<TaiSanResponse1, TaiSan>{
@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TaiSan partialUpdate(TaiSanResponse1 taiSanResponse1, @MappingTarget TaiSan taiSan);
}
