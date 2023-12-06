package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.Sale;
import be.bds.bdsbes.payload.SaleResponse;
import be.bds.bdsbes.utils.dto.SaleDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SaleMapper extends EntityMapper<SaleResponse, Sale> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SaleResponse toDto(Sale sale);
}