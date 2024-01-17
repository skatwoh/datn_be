package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.ChiTietDichVu;
import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.entities.DichVu;
import be.bds.bdsbes.entities.HoaDon;
import be.bds.bdsbes.repository.ChiTietDichVuRepository;
import be.bds.bdsbes.repository.DatPhongRepository;
import be.bds.bdsbes.repository.DichVuRepository;
import be.bds.bdsbes.repository.HoaDonRepository;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.List;

@Service
public class PdfGenerator {

    private final DatPhongRepository datPhongRepository;


    @Autowired
    private DichVuRepository dichVuRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private ChiTietDichVuRepository chiTietDichVuRepository;

    public PdfGenerator(DatPhongRepository datPhongRepository) {
        this.datPhongRepository = datPhongRepository;
    }

    public void export(HttpServletResponse response, Long id) throws IOException, DocumentException, ParseException {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
//        int tienPhong = (int) (Double.parseDouble(String.valueOf(datPhong.getPhong().getGiaPhong())) * (datPhong.getCheckOut().getDayOfMonth()-datPhong.getCheckIn().getDayOfMonth()));
        int tongGia =  (int) (Double.parseDouble(String.valueOf(hoaDon.getTongTien())));
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC);
        fontTitle.setColor(BaseColor.BLACK);
        fontTitle.setSize(20);
        Font fontDate = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC);
        fontTitle.setColor(BaseColor.BLACK);
        fontDate.setSize(18);

        Paragraph paragraph = new Paragraph("HOA DON THANH TOAN", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        Paragraph paragraphDate = new Paragraph("\nNgay " + sdf2.format(sdf1.parse(String.valueOf(hoaDon.getNgayTao()))), fontDate);
        paragraphDate.setAlignment(Paragraph.ALIGN_CENTER);
        Paragraph paragraphLine = new Paragraph("", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        Chunk lineSeparator = new Chunk(new LineSeparator());
        paragraphLine.add(lineSeparator);

        Font fontInfor = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontInfor.setSize(15);
        Font fontTable = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTable.setSize(13);
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
// Tạo LocalDateTime hiện tại theo múi giờ ICT
        LocalDateTime now = LocalDateTime.now(zoneId);
// Hiển thị giờ Việt Nam
        System.out.println(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Paragraph paragraphMaKH = new Paragraph("Ma khach hang: " + hoaDon.getKhachHang().getMa(), fontInfor);
        paragraphMaKH.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphTenKH = new Paragraph("Ten khach hang: " + hoaDon.getKhachHang().getHoTen(), fontInfor);
        paragraphTenKH.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphTenKHTQ = new Paragraph("Ten khach hang: " + "Khach hang tai quay", fontInfor);
        paragraphTenKHTQ.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphSDT = new Paragraph("SDT: " + hoaDon.getKhachHang().getSdt(), fontInfor);
        paragraphSDT.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphNgayThanhToan = new Paragraph("Ngay thanh toan: " + hoaDon.getNgayThanhToan(), fontInfor);
        paragraphNgayThanhToan.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphNull = new Paragraph("\n", fontInfor);
        paragraphNull.setAlignment(Paragraph.ALIGN_LEFT);

        String danhSachPhong = "";
        List<DatPhong> list = datPhongRepository.getDatPhongByHoaDon(hoaDon.getId());
        for (int x = 0; x < list.size();x++) {
            if (x == list.size() -1){
                danhSachPhong = danhSachPhong + list.get(x).getPhong().getMa();
                break;
            }
            danhSachPhong = danhSachPhong + list.get(x).getPhong().getMa() + ",";
        }
        Paragraph paragraphMaPhong = new Paragraph("Danh sach phong: " + danhSachPhong, fontTable);
        paragraphNull.setAlignment(Paragraph.ALIGN_LEFT);


        paragraphNull.setAlignment(Paragraph.ALIGN_LEFT);


        String formattedTongTien = formatter.format(hoaDon.getTongTien());
        Paragraph paragraphTongTien = new Paragraph("\nTong thanh toan: " + formattedTongTien + "VND", fontInfor);
        paragraphTongTien.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraph9 = new Paragraph("\n", fontInfor);
        paragraph9.setAlignment(Paragraph.ALIGN_LEFT);
        paragraph9.add(lineSeparator);
        Paragraph paragraphEnd = new Paragraph("\nCAM ON QUY KHACH DA SU DUNG \nDICH VU CUA CHUNG TOI!", fontTitle);
        paragraphEnd.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        document.add(paragraphDate);
        document.add(paragraphLine);
        document.add(paragraphMaKH);
        if(hoaDon.getKhachHang().getSdt().equals("") || hoaDon.getKhachHang().getSdt() == null){
            document.add(paragraphTenKHTQ);
        } else if(!hoaDon.getKhachHang().getSdt().equals("") || hoaDon.getKhachHang().getSdt() != null){
            document.add(paragraphTenKH);
        }
        if(!(hoaDon.getKhachHang().getSdt().trim().equals("")) || hoaDon.getKhachHang().getSdt() != null){
            document.add(paragraphSDT);
        }
        document.add(paragraphNgayThanhToan);
        document.add(paragraphNull);
        document.add(paragraphMaPhong);
        for(int x = 0;x < list.size();x ++){
            document.add(paragraphLine);
            Paragraph paragraphThongTinPhong = new Paragraph("Ma so phong: " + list.get(x).getPhong().getMa(), fontTable);
            Paragraph paragraphLoaiPhong = new Paragraph("Loai phong: " + list.get(x).getPhong().getLoaiPhong().getTenLoaiPhong(), fontTable);
            Paragraph paragraphCheckIn = new Paragraph( "Ngay nhan phong: " + formatDate.format(list.get(x).getCheckIn()), fontTable);
            Paragraph paragraphCheckOut = new Paragraph( "Ngay tra phong: " + formatDate.format(list.get(x).getCheckOut()), fontTable);
            Paragraph paragraphTienPhong = new Paragraph( "Tong tien: " + formatter.format(list.get(x).getTongGia()) + "VND", fontTable);



            document.add(paragraphThongTinPhong);
            document.add(paragraphLoaiPhong);
            document.add(paragraphCheckIn);
            document.add(paragraphCheckOut);
            document.add(paragraphTienPhong);
        }
        document.add(paragraphLine);
        document.add(paragraphTongTien);
        if(hoaDon.getGhiChu() != null){
            String formattedTienCoc = formatter.format(BigDecimal.valueOf(Double.parseDouble(hoaDon.getGhiChu())));
            Paragraph paragraphTienCoc = new Paragraph("\nTien coc: " + formattedTienCoc + "VND", fontInfor);
            paragraphTienCoc.setAlignment(Paragraph.ALIGN_LEFT);
            Paragraph paragraphThanhToanSau = new Paragraph("\nThanh toan sau: " + formatter.format(hoaDon.getTongTien().subtract(BigDecimal.valueOf(Double.parseDouble(hoaDon.getGhiChu())))) + "VND", fontInfor);
            paragraphThanhToanSau.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(paragraphTienCoc);
            document.add(paragraphThanhToanSau);
        }
        document.add(paragraph9);
        document.add(paragraphEnd);
        document.close();
    }

    private void convertDocxToPdf() throws IOException {
        // Đường dẫn tới tệp DOCX và PDF
        String docxFilePath = "src/main/resources/template/output/template.docx";
        String pdfFilePath = "src/main/resources/template/output/datphong.pdf";
        // Đọc nội dung từ tệp DOCX
        FileInputStream docxInputStream = new FileInputStream(docxFilePath);
        XWPFDocument doc = new XWPFDocument(docxInputStream);
        // Tạo một tài liệu PDF mới
        PDDocument pdfDocument = new PDDocument();
        // Duyệt qua các đoạn văn bản trong DOCX và thêm vào tài liệu PDF
        for (XWPFParagraph paragraph : doc.getParagraphs()) {
            String[] lines = paragraph.getText().split("\n");
            for (String line : lines) {
                PDPage page = new PDPage();
                pdfDocument.addPage(page);
                try (PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page)) {
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(10, 700);
                    contentStream.showText(line);
                    contentStream.endText();
                }
            }
        }
        // Lưu tài liệu PDF
        pdfDocument.save(pdfFilePath);
        pdfDocument.close();
    }

    public void exportPdf(Long id) throws IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/template/HoaDonThanhToan.docx");
        XWPFDocument document = new XWPFDocument(fis);

        DatPhong datPhong = datPhongRepository.findById(id).get();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("Name: " + datPhong.getGhiChu());
        run.addBreak();
        run.setText("Mã khách hàng: " + datPhong.getKhachHang().getHoTen()
        );

// Add further paragraphs and formatting for other required data based on your structure and template

        XWPFTable table = document.createTable();

        try (FileOutputStream out = new FileOutputStream("src/main/resources/template/output/datphong.pdf")) {
            PDDocument pdfDocument = new PDDocument();
            PDPage page = new PDPage();
            pdfDocument.addPage(page);

            pdfDocument.save(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportDV(HttpServletResponse response, Long id) throws IOException, DocumentException, ParseException {
        DatPhong datPhong = datPhongRepository.findById(id).get();
        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        int tongTien = 0;
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC);
        fontTitle.setColor(BaseColor.BLACK);
        fontTitle.setSize(20);
        Font fontDate = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC);
        fontTitle.setColor(BaseColor.BLACK);
        fontDate.setSize(18);
//        String formattedGiaDichVu = formatter.format(dichVu.getGiaDichVu());
        Paragraph paragraph = new Paragraph("HOA DON DICH VU PHONG " + datPhong.getPhong().getMa(), fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        Paragraph paragraphLine = new Paragraph("", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        Chunk lineSeparator = new Chunk(new LineSeparator());
        paragraphLine.add(lineSeparator);
        Paragraph paragraphTenKH = new Paragraph("Ten khach hang: " + datPhong.getKhachHang().getHoTen(), fontDate);
        paragraphTenKH.setAlignment(Paragraph.ALIGN_LEFT);
        List<ChiTietDichVu> listCTDV = chiTietDichVuRepository.getListByDatPhong(datPhong.getId());
//        String formattedTongTien = formatter.format(dichVu.getGiaDichVu());
        Paragraph paragraph9 = new Paragraph("\n", fontDate);
        paragraph9.setAlignment(Paragraph.ALIGN_LEFT);
        paragraph9.add(lineSeparator);
        Paragraph paragraphEnd = new Paragraph("\nCAM ON QUY KHACH DA SU DUNG \nDICH VU CUA CHUNG TOI!", fontTitle);
        paragraphEnd.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);
        document.add(paragraphLine);
        document.add(paragraphTenKH);
        for (ChiTietDichVu chiTietDichVu : listCTDV){
            LocalDateTime datetime = LocalDateTime.parse(chiTietDichVu.getGhiChu(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            String formattedDateTime = datetime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            document.add(paragraphLine);
            Paragraph paragraphDichVu = new Paragraph("Ten dich vu: " + chiTietDichVu.getDichVu().getTenDichVu(), fontDate);
            Paragraph paragraphGia = new Paragraph("Gia dich vu: " + formatter.format(chiTietDichVu.getDichVu().getGiaDichVu()), fontDate);
            Paragraph paragraphSoLuong = new Paragraph("So luong: " + chiTietDichVu.getTrangThai(), fontDate);
            Paragraph paragraphTongTien = new Paragraph("Tong tien: " + formatter.format(chiTietDichVu.getDichVu().getGiaDichVu().multiply(BigDecimal.valueOf(chiTietDichVu.getTrangThai()))), fontDate);
            Paragraph paragraphNgayDat = new Paragraph("Thoi gian dat: " + formattedDateTime, fontDate);
            document.add(paragraphDichVu);
            document.add(paragraphGia);
            document.add(paragraphSoLuong);
            document.add(paragraphTongTien);
            document.add(paragraphNgayDat);
            tongTien += (int) Double.parseDouble(String.valueOf(chiTietDichVu.getDichVu().getGiaDichVu().multiply(BigDecimal.valueOf(chiTietDichVu.getTrangThai()))));
        }
        Paragraph paragraphTongTien = new Paragraph("\nTong thanh toan: " + formatter.format(tongTien) + "VND", fontDate);
        paragraphTongTien.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(paragraphLine);
        document.add(paragraphTongTien);
        document.add(paragraph9);
        document.add(paragraphEnd);
        document.close();
    }

    public void export2(HttpServletResponse response, Long id) throws IOException, XDocReportException, ParseException {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=HoaDonThanhToan.docx");
        try (InputStream in = getClass().getResourceAsStream("src/main/resources/template/HoaDonThanhToan.docx")) {
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);
            IContext context = report.createContext();
            context.put("hoaDon", hoaDon);
            context.put("datPhongs", datPhongRepository.getDatPhongByHoaDon(hoaDon.getId()));
            try (OutputStream out = response.getOutputStream()) {
                report.process(context, out);
            }
        }
    }

    public void export3(HttpServletResponse response, Long id) throws IOException, XDocReportException, ParseException {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        // Load the template
        InputStream templateStream = getClass().getResourceAsStream("src/main/resources/template/HoaDonThanhToan.docx");
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(templateStream, TemplateEngineKind.Velocity);
        // Create a context for your data
        IContext context = report.createContext();
        context.put("hoaDon", hoaDon);  // Assuming you have a getter in your HoaDon class for each field
        // Create a new OutputStream
        OutputStream out = response.getOutputStream();
        // Set the response headers for Word document
        response.setContentType("application/msword");
        response.setHeader("Content-Disposition", "attachment; filename=your_document.docx");
        // Generate the report
        report.process(context, out);
    }
}
