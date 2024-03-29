package be.bds.bdsbes.resource;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.iService.IChiTietDichVuService;
import be.bds.bdsbes.service.dto.ChiTietDichVuDTO;
import be.bds.bdsbes.service.impl.PdfGenerator;
import be.bds.bdsbes.utils.AppConstantsUtil;
import be.bds.bdsbes.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/rpc/bds/chi-tiet-dich-vu")
public class ChiTietDichVuController {
    @Autowired
    IChiTietDichVuService iChiTietDichVuService;

    @Autowired
    PdfGenerator pdfGenerator;

    @GetMapping("/list")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.iChiTietDichVuService.getAccounts(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id){
        if(iChiTietDichVuService.getOne(id) == null){
            return ResponseEntity.badRequest().body("Không tìm thấy");
        }
        return ResponseEntity.ok(iChiTietDichVuService.getOne(id));
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody @Valid ChiTietDichVuDTO chiTietDichVuDTO){
//        if(result.hasErrors()){
//            List<ObjectError> errorList = result.getAllErrors();
//            return ResponseEntity.badRequest().body(errorList);
//        }
        return ResponseEntity.ok(iChiTietDichVuService.create(chiTietDichVuDTO));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid ChiTietDichVuDTO chiTietDichVuDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(iChiTietDichVuService.update(chiTietDichVuDTO, id));
    }

    @GetMapping("/list-by-dat-phong")
    public ResponseEntity<?> getListByDatPhong(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "id") Long id) {
        try {
            return ResponseUtil.wrap(this.iChiTietDichVuService.getAllByDatPhong(page, size, id));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/generate-hoa-don-dich-vu")
    public void generatePDF(@RequestParam(value = "id") Long id, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.pdfGenerator.exportDV(response, id);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam(value = "id") Long id){
        return ResponseUtil.wrap(iChiTietDichVuService.delete(id));
    }
}
