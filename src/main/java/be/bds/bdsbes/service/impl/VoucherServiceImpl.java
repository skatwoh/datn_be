package be.bds.bdsbes.service.impl;
import be.bds.bdsbes.entities.Voucher;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.VoucherResponse;
import be.bds.bdsbes.repository.VoucherRepository;
import be.bds.bdsbes.service.iService.IVoucherService;
import be.bds.bdsbes.service.dto.VoucherDto;
import be.bds.bdsbes.service.mapper.VoucherMapper;
import be.bds.bdsbes.utils.AppConstantsUtil;
import be.bds.bdsbes.utils.ServiceExceptionBuilderUtil;
import be.bds.bdsbes.utils.ValidationErrorUtil;
import be.bds.bdsbes.utils.dto.KeyValue;
import be.bds.bdsbes.utils.dto.PagedResponse;
import be.bds.bdsbes.utils.dto.ValidationErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("voucherServiceImpl")
@EnableScheduling
public class VoucherServiceImpl implements IVoucherService{
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private VoucherMapper voucherMapper;
//    @Scheduled(cron = "0 0/1 * * * ?") // Chạy 1 Phút 1 lần
    @Scheduled(cron = "0 0 0 * * ?") // Trong cron expression "0 0 0 * * ?", giá trị thứ ba là 0 (giờ),
    // giá trị thứ tư là 0 (phút), và giá trị thứ năm là 0 (giây). Điều này đặt cron expression để chạy lúc 00:00:00 hàng ngày.
    public void expireVouchers() {
        LocalDate today = LocalDate.now();

        List<Voucher> expiredVouchers = voucherRepository.findByExpiryDateBeforeAndStatus(today , 1);

        for (Voucher voucher : expiredVouchers) {
             voucher.setTrangThai(0);
        }
        voucherRepository.saveAll(expiredVouchers);
    }
    @Override
    public List<Voucher> getList(){
        return voucherRepository.findAll();
    }

    @Override
    public VoucherResponse getVoucher(Long id) {
        return voucherRepository.getVoucher(id);
    }
    @Override
    public Page<Voucher> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return voucherRepository.findAll(pageable);

    }

    @Override
    public Voucher add(VoucherDto voucherDto) {
        Voucher voucher = voucherDto.dtoVoucher(new Voucher());
//        int leftLimit = 97; // letter 'a'
//        int rightLimit = 122; // letter 'z'
//        int targetStringLength = 10;
//        Random random = new Random();
//        String generatedString = random.ints(leftLimit, rightLimit + 1)
//                .limit(targetStringLength)
//                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                .toString();
        voucher.setMa(generateAutoCode());
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

    @Override
    public Integer updateTrangThai(Long id) {

        Voucher vcher = voucherRepository.findById(id).get();
        if (vcher.getTrangThai() == 0) {
            return voucherRepository.updateTrangThaiById(1, id);
        }
        if (vcher.getTrangThai() == 1) {
            return voucherRepository.updateTrangThaiById(0, id);
        }

        return null;
    }

    @Override
    public PagedResponse<VoucherResponse> getVouchers(int page, int size) throws ServiceException {
        if (page <= 0) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("page", ValidationErrorUtil.Invalid))
                    .build();
        }

        if (size > AppConstantsUtil.MAX_PAGE_SIZE) {
            List<KeyValue> params = new ArrayList<>();
            params.add(new KeyValue("max", String.valueOf(AppConstantsUtil.MAX_PAGE_SIZE)));

            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("pageSize", ValidationErrorUtil.Invalid, params))
                    .build();
        }

        // Retrieve all entities
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.ASC, "id");
        Page<Voucher> entities = voucherRepository.findAll(pageable);

        List<VoucherResponse> dtos = this.voucherMapper.toDtoList(entities.getContent());
        return new PagedResponse<>(
                dtos,
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }

    @Override
    public PagedResponse<VoucherResponse> getVouchersByTrangThai(int page, int size) throws ServiceException {
        if (page <= 0) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("page", ValidationErrorUtil.Invalid))
                    .build();
        }

        if (size > AppConstantsUtil.MAX_PAGE_SIZE) {
            List<KeyValue> params = new ArrayList<>();
            params.add(new KeyValue("max", String.valueOf(AppConstantsUtil.MAX_PAGE_SIZE)));

            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("pageSize", ValidationErrorUtil.Invalid, params))
                    .build();
        }

        // Retrieve all entities
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.ASC, "id");
        Page<Voucher> entities = voucherRepository.getAllByTrangThai(pageable);

        List<VoucherResponse> dtos = this.voucherMapper.toDtoList(entities.getContent());
        return new PagedResponse<>(
                dtos,
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }


    @Override
    public PagedResponse<VoucherResponse> searchVoucher(int page, int size, String searchInput) throws ServiceException {
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.DESC, "id");
        Page<Voucher> entities = voucherRepository.searchVoucher(pageable, searchInput);

        List<VoucherResponse> dtos = this.voucherMapper.toDtoList(entities.getContent());
        return new PagedResponse<>(
                dtos,
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }

    public int getNumberOfRecords() {
        Long count = voucherRepository.count();
        return count.intValue();
    }


    private String generateAutoCode() {
        int numberOfDigits = 2;

        int numberOfExistingRecords = getNumberOfRecords();

        String autoCode = "V" + String.format("%0" + numberOfDigits + "d", numberOfExistingRecords + 1);

        return autoCode;
    }



}
