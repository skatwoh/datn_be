package be.bds.bdsbes.resource;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.repository.DatPhongRepository;
import be.bds.bdsbes.service.iService.IDatPhongService;
import be.bds.bdsbes.service.dto.DatPhongDTO;
import be.bds.bdsbes.service.impl.PdfGenerator;
import be.bds.bdsbes.utils.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.zxing.client.j2se.MatrixToImageWriter;

@Slf4j
@RestController
@RequestMapping("/rpc/bds/dat-phong")
public class DatPhongController {
    @Autowired
    private DatPhongRepository datPhongRepository;

    @Autowired
    IDatPhongService iDatPhongService;

    @Autowired
    private PdfGenerator pdfGenerator;

    @GetMapping("/list")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.iDatPhongService.getRoomOrder(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("detail")
    public ResponseEntity<?> getOne(@RequestParam(value = "id") Long id) {
        if (iDatPhongService.getOne(id) == null) {
            return ResponseEntity.badRequest().body("Không tồn tại");
        }
        return ResponseEntity.ok(iDatPhongService.getOne(id));
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@Valid @RequestBody DatPhongDTO datPhongDTO) {
        try {
            Boolean response = iDatPhongService.create(datPhongDTO);
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            log.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        }

    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestParam(value = "id") Long id, @Valid @RequestBody DatPhongDTO datPhongDTO, BindingResult result) {
        if (iDatPhongService.update(datPhongDTO, id) == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy");
        }
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            return ResponseEntity.ok(errors);
        } else {
            return ResponseEntity.ok().body(iDatPhongService.update(datPhongDTO, id));
        }
    }

    @GetMapping("/pdf/generate")
    public void generatePDF(@RequestParam(value = "id") Long id, HttpServletResponse response) throws IOException, DocumentException, ParseException {
        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.pdfGenerator.export(response, id);
    }

    @GetMapping("/generateQR")
    public byte[] generateQRCode(@RequestParam("data") String data) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @GetMapping("/list-room-order-by-user")
    public ResponseEntity<?> getListByUser(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "id", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) Long id,
            @RequestParam(value = "trangThai", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) Integer trangThai) {
        try {
            return ResponseUtil.wrap(this.iDatPhongService.getRoomOderByUser(page, size, id, trangThai));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/lich-su-dat-phong")
    public ResponseEntity<?> getLichSuDatPhong(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "id", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) Long id
            ) {
        try {
            return ResponseUtil.wrap(this.iDatPhongService.getLichSuDatPhong(page, size, id));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/list-room-order-by-upper-price")
    public ResponseEntity<?> getListRoomByUpperPrice(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "giaPhong", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) BigDecimal giaPhong,
            @RequestParam(value = "id") Long id
    ) {
        try {
            return ResponseUtil.wrap(this.iDatPhongService.getPhongByUpperPrice(page, size, giaPhong, id));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("update-status")
    public ResponseEntity<?> delete(@RequestParam(value = "id") Long id) {
        try {
            return ResponseUtil.wrap(this.iDatPhongService.updateTrangThai(id));
        } catch (ServiceException e) {
            ApiError apiError = new ApiError(String.valueOf(StatusError.Failed), e.getMessage());
            return ResponseUtil.wrap(apiError);
        }
    }


    @GetMapping("/generate-bill")
    public ResponseEntity<?> generateInvoice(@RequestParam(value = "id") Long id) {
        try {
            pdfGenerator.exportPdf(id);
            FileInputStream pdfInputStream = new FileInputStream("src/main/resources/template/output/datphong.pdf");
            // Trả về tệp PDF dưới dạng InputStreamResource
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=datphong.pdf");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(pdfInputStream));
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về lỗi nếu có vấn đề trong quá trình tạo hóa đơn
            return ResponseEntity.status(500).body(null);
        }
    }

//    @PutMapping("update-id-hoa-don")
//    public ResponseEntity<?> updateHoaDonByDatPhong(@RequestParam (value = "id") Long id){
//
//    }

//    @GetMapping("/generate-bill")
//    public void generateInvoice(@RequestParam(value = "id") Long id) {
//        this.pdfGenerator.exportPdf2(id);
//    }

    @PutMapping("update-dat-phong")
    public ResponseEntity<?> updateDatPhongById(
            @RequestParam(value = "id") Long id, @Valid @RequestBody DatPhongDTO datPhongDTO
    ) {
        try {
            Integer response = iDatPhongService.updateDatPhong(id, datPhongDTO);
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            log.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @GetMapping("/list-room-of-bill")
    public ResponseEntity<?> getListOrderOfBill(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "userId", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) Long userId) {
        try {
            return ResponseUtil.wrap(this.iDatPhongService.getRoomOfBill(page, size, userId));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
