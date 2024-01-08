package be.bds.bdsbes.resource;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.iService.IHoaDonService;
import be.bds.bdsbes.service.dto.HoaDonDTO;
import be.bds.bdsbes.service.impl.PdfGenerator;
import be.bds.bdsbes.service.impl.Test;
import be.bds.bdsbes.utils.ApiError;
import be.bds.bdsbes.utils.AppConstantsUtil;
import be.bds.bdsbes.utils.ResponseUtil;
import be.bds.bdsbes.utils.StatusError;
import com.itextpdf.text.DocumentException;
import fr.opensagres.xdocreport.core.XDocReportException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/rpc/bds/hoa-don")
public class HoaDonController {

    @Autowired
    IHoaDonService iHoaDonService;

    @Autowired
    PdfGenerator pdfGenerator;

    @Autowired
    Test test;

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
    public void generatePDF(@RequestParam(value = "id") Long id, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.pdfGenerator.export(response, id);
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

    @GetMapping("detail")
    public ResponseEntity<?> getOne(@RequestParam(value = "id") Long id) {
        if (iHoaDonService.getOne(id) == null) {
            return ResponseEntity.badRequest().body("Không tồn tại");
        }
        return ResponseEntity.ok(iHoaDonService.getOne(id));
    }

    @PutMapping("update-status")
    public ResponseEntity<?> delete(@RequestParam(value = "id") Long id, @RequestBody Integer trangThai) {
        try {
            return ResponseUtil.wrap(this.iHoaDonService.updateTrangThai(trangThai, id));
        } catch (ServiceException e) {
            ApiError apiError = new ApiError(String.valueOf(StatusError.Failed), e.getMessage());
            return ResponseUtil.wrap(apiError);
        }
    }

    @PostMapping("delete")
    public ResponseEntity<?> deleteHD(@RequestBody HoaDonDTO hoaDonDTO) {
        return ResponseUtil.wrap(this.iHoaDonService.deleteHoaDon(hoaDonDTO));
    }

    @GetMapping("list-by-search")
    public ResponseEntity<?> getListbySearch(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "input", defaultValue = "") String searchInput) {
        try {
            return ResponseUtil.wrap(this.iHoaDonService.getHoaDonBySearch(page, size, searchInput));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("create-or-update-tai-quay")
    public ResponseEntity<?> createOrUpdateTaiQuay(@RequestBody HoaDonDTO hoaDonDTO) {
        try {
            return ResponseUtil.wrap(this.iHoaDonService.createOrUpdateTaiQuay(hoaDonDTO));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("doanh-thu-by-day")
    public ResponseEntity<?> doanhthubyday(@RequestParam(value = "checkIn") String checkIn,@RequestParam(value = "checkOut") String checkOut) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedCheckIn = LocalDate.parse(checkIn, formatter);
        LocalDate parsedCheckOut = LocalDate.parse(checkOut, formatter);
        return ResponseUtil.wrap(this.iHoaDonService.getDoanhThuByDay(parsedCheckIn,parsedCheckOut));
    }

    @GetMapping("doanh-thu-by-to-day")
    public ResponseEntity<?> doanhThuByTime(@RequestParam(value = "year") int year,@RequestParam(value = "month") int month,@RequestParam(value = "day") int day) {
        return ResponseUtil.wrap(this.iHoaDonService.getDoanhThuByToDay(day, month, year));
    }

    @GetMapping("doanh-thu-by-month")
    public ResponseEntity<?> doanhThuByMonth(@RequestParam(value = "year") int year,@RequestParam(value = "month") int month) {
        return ResponseUtil.wrap(this.iHoaDonService.getDoanhThuByMonth(month, year));
    }

    @GetMapping("doanh-thu-by-year")
    public ResponseEntity<?> doanhThuByYear(@RequestParam(value = "year") int year) {
        return ResponseUtil.wrap(this.iHoaDonService.getDoanhThuByYear(year));
    }

    @GetMapping("all-doanh-thu")
    public ResponseEntity<?> getAllDoanhThu() {
        return ResponseUtil.wrap(this.iHoaDonService.getAllDoanhThu());
    }

    @PutMapping("tinh-tien-dich-vu")
    public ResponseEntity<?> tinhTienDichVu(@RequestParam(value = "id") Long id, @RequestBody BigDecimal tongTien) throws ServiceException {
        return ResponseUtil.wrap(this.iHoaDonService.updateTienDichVu(tongTien, id));
    }

}
