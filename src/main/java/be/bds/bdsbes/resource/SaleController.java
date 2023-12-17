package be.bds.bdsbes.resource;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.iService.ISaleService;
import be.bds.bdsbes.utils.AppConstantsUtil;
import be.bds.bdsbes.utils.ResponseUtil;
import be.bds.bdsbes.utils.dto.SaleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rpc/bds/sale")
public class SaleController {

    @Autowired
    ISaleService iSaleService;

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
                                  @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size){
        try {
            return ResponseUtil.wrap(this.iSaleService.getSale(page, size));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update-trang-thai")
    public ResponseEntity<?> updateStatus(@RequestParam(value = "id") Long id) {
        return ResponseUtil.wrap(this.iSaleService.updateTrangThai(id));
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(this.iSaleService.get(id));
    }

    @GetMapping("/get-sale")
    public ResponseEntity<?> getSale() {
        return ResponseEntity.ok(this.iSaleService.getSale());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody SaleDto saleDto) {
        try {
            return ResponseUtil.wrap(this.iSaleService.create(saleDto));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
