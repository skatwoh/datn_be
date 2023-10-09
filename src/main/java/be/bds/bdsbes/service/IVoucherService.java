package be.bds.bdsbes.service;

import be.bds.bdsbes.entities.Voucher;
import be.bds.bdsbes.repository.VoucherRepository;
import be.bds.bdsbes.service.dto.VoucherDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IVoucherService {

    List<VoucherRepository> getList();

    Page<Voucher> getPage(Integer page);

    Voucher getOne(Long id);

    Voucher add(VoucherDto voucherDto);

    Voucher update(VoucherDto voucherDto, Long id);
}
