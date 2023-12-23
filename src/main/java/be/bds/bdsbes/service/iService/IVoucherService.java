package be.bds.bdsbes.service.iService;
import be.bds.bdsbes.entities.Voucher;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.VoucherResponse;
import be.bds.bdsbes.service.dto.VoucherDto;
import be.bds.bdsbes.utils.dto.PagedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IVoucherService {


    List<Voucher> getList();

    Page<Voucher> getPage(Integer page);
    VoucherResponse getVoucher(Long id);

    Voucher add(VoucherDto voucherDto);

    Voucher update(VoucherDto voucherDto, Long id);

    Integer updateTrangThai(Long id);

    PagedResponse<VoucherResponse> getVouchers(int page, int size) throws ServiceException;

    PagedResponse<VoucherResponse> getVouchersByTrangThai(int page, int size) throws ServiceException;
    PagedResponse<VoucherResponse> searchVoucher(int page, int size, String searchInput) throws ServiceException;


}

