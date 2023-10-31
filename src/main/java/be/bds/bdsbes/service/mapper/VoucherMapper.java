package be.bds.bdsbes.service.mapper;


import be.bds.bdsbes.entities.Voucher;
import be.bds.bdsbes.payload.VoucherResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE , componentModel = "spring")
public interface VoucherMapper extends EntityMapper<VoucherResponse , Voucher> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Voucher partialUpdate( VoucherResponse voucherResponse, @MappingTarget Voucher voucher);
}
