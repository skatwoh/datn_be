package be.bds.bdsbes.resource;

import be.bds.bdsbes.service.IVoucherService;
import be.bds.bdsbes.service.dto.VoucherDto;
import be.bds.bdsbes.service.impl.VoucherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/voucher")
public class VoucherController {
    @Autowired
    private IVoucherService iVoucherService = new VoucherServiceImpl();

    @GetMapping("get-page")
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0", name ="page") Integer page){
        return ResponseEntity.ok(iVoucherService.getPage(page));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id){
        if(iVoucherService.getOne(id) == null){
            return ResponseEntity.ok("Not found");
        }
        return ResponseEntity.ok(iVoucherService.getOne(id));
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody @Valid VoucherDto voucherDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return ResponseEntity.ok(errorList);
        }
        return ResponseEntity.ok(iVoucherService.add(voucherDto));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid VoucherDto voucherDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return ResponseEntity.ok(errorList);
        }
        if(iVoucherService.update(voucherDto, id) == null){
            return ResponseEntity.ok("Update failed");
        }
        return ResponseEntity.ok(iVoucherService.update(voucherDto, id));
    }
}
