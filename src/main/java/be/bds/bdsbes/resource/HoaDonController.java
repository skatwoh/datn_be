package be.bds.bdsbes.resource;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.iService.IHoaDonService;
import be.bds.bdsbes.service.dto.HoaDonDTO;
import be.bds.bdsbes.service.impl.PdfGenerator;
import be.bds.bdsbes.utils.AppConstantsUtil;
import be.bds.bdsbes.utils.ResponseUtil;
import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/rpc/bds/hoa-don")
public class HoaDonController {

    @Autowired
    IHoaDonService iHoaDonService;

    @Autowired
    PdfGenerator pdfGenerator;

    @GetMapping("list")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.iHoaDonService.getHoaDon(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("list-by-customer")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "hoTen", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) String hoTen,
            @RequestParam(value = "sdt", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) String sdt) {
        try {
            return ResponseUtil.wrap(this.iHoaDonService.getHoaDonByCustomer(page, size, hoTen, sdt));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody HoaDonDTO hoaDonDTO) {
        try {
            return ResponseUtil.wrap(this.iHoaDonService.create(hoaDonDTO));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("create-or-update")
    public ResponseEntity<?> createOrUpdate(@RequestBody HoaDonDTO hoaDonDTO) {
        try {
            return ResponseUtil.wrap(this.iHoaDonService.createOrUpdate(hoaDonDTO));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/generate-hoa-don")
    public void generatePDF(@RequestParam(value = "id") Long id, HttpServletResponse response) throws IOException, DocumentException, ParseException {
        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.pdfGenerator.export(response,id);
    }

    @PutMapping("update-tong-tien")
    public ResponseEntity<?> updateTongTien(@RequestBody HoaDonDTO hoaDonDTO) {
        try {
            return ResponseUtil.wrap(this.iHoaDonService.updateTongTien(hoaDonDTO));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        }
    }
}
