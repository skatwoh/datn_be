package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.entities.HoaDon;
import be.bds.bdsbes.repository.DatPhongRepository;
import be.bds.bdsbes.repository.HoaDonRepository;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private HoaDonRepository hoaDonRepository;

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
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        Paragraph paragraphMaKH = new Paragraph("Ma khach hang: " + hoaDon.getKhachHang().getMa(), fontInfor);
        paragraphMaKH.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphTenKH = new Paragraph("Ten khach hang: " + hoaDon.getKhachHang().getHoTen(), fontInfor);
        paragraphTenKH.setAlignment(Paragraph.ALIGN_LEFT);
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
        document.add(paragraphTenKH);
        document.add(paragraphSDT);
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
        FileInputStream fis = new FileInputStream("src/main/resources/template/hoa_don.docx");
        XWPFDocument document = new XWPFDocument(fis);

        List<DatPhongResponse> dataList = datPhongRepository.getDatPhong(id);
        for (DatPhongResponse data : dataList) {
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    // Thay thế trường dữ liệu trong cặp {{}}
                    text = text.replace("{{ma}}", data.getMa());
                    run.setText(text, 0);
                }
            }
        }

        try (FileOutputStream out = new FileOutputStream("src/main/resources/template/output/datphong.pdf")) {
            PDDocument pdfDocument = new PDDocument();
            PDPage page = new PDPage();
            pdfDocument.addPage(page);

            pdfDocument.save(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
