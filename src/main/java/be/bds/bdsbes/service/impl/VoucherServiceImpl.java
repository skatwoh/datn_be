package be.bds.bdsbes.service.impl;
import be.bds.bdsbes.entities.TheThanhVien;
import be.bds.bdsbes.entities.Voucher;
import be.bds.bdsbes.repository.VoucherRepository;
import be.bds.bdsbes.service.IVoucherService;
import be.bds.bdsbes.service.dto.VoucherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("voucherServiceImpl")
public class VoucherServiceImpl implements IVoucherService{
    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public List<VoucherRepository> getList() {
        return voucherRepository.getAllV();
    }

    @Override
    public Page<Voucher> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return voucherRepository.findAll(pageable);

    }

    @Override
    public Voucher getOne(Long id) {
        Optional<Voucher> voucherOptional = voucherRepository.findById(id);
        if(voucherOptional.isPresent()){
            Voucher voucher = voucherOptional.get();
            return voucher;
        }
        return null;
    }

    @Override
    public Voucher add(VoucherDto voucherDto) {
        Voucher voucher = voucherDto.dtoVoucher(new Voucher());
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher update(VoucherDto voucherDto, Long id) {
        Optional<Voucher> voucherOptional = voucherRepository.findById(id);
        if(voucherOptional.isPresent()){
            Voucher voucher = voucherDto.dtoVoucher(voucherOptional.get());
            return voucherRepository.save(voucher);
        }
        return null;
    }
}
