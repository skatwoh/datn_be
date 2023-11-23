package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.repository.DatPhongRepository;
import be.bds.bdsbes.repository.HoaDonRepository;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        DatPhong datPhong = datPhongRepository.findById(id).get();
        int tienPhong = (int) (Double.parseDouble(String.valueOf(datPhong.getPhong().getGiaPhong())) * (datPhong.getCheckOut().getDayOfMonth()-datPhong.getCheckIn().getDayOfMonth()));
        int tongGia =  (int) (Double.parseDouble(String.valueOf(datPhong.getTongGia())));
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
//        String soTien = "100000";
//        NumberFormat formatterVN = NumberFormat.getInstance(new Locale("vi", "VN"));
//        String soTienChu = formatterVN.format(Long.parseLong(soTien));
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setColor(BaseColor.BLACK);
        fontTitle.setSize(20);
        Font fontDate = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setColor(BaseColor.BLACK);
        fontDate.setSize(18);

        Paragraph paragraph = new Paragraph("HOA DON THANH TOAN", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        Paragraph paragraphPhong = new Paragraph("PHONG " + datPhong.getPhong().getMa(), fontTitle);
        paragraphPhong.setAlignment(Paragraph.ALIGN_CENTER);
        Paragraph paragraphDate = new Paragraph("\nNgay " + sdf2.format(sdf1.parse(String.valueOf(datPhong.getNgayDat()))), fontDate);
        paragraphDate.setAlignment(Paragraph.ALIGN_CENTER);
        Paragraph paragraphLine = new Paragraph("", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        Chunk lineSeparator = new Chunk(new LineSeparator());
        paragraphLine.add(lineSeparator);

        Font fontInfor = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE);
        fontInfor.setSize(15);
        Font fontTable = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE);
        fontTable.setSize(13);

        Paragraph paragraphMaKH = new Paragraph("Ma khach hang: " + datPhong.getUser().getKhachHang().getMa(), fontInfor);
        paragraphMaKH.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphTenKH = new Paragraph("Ten khach hang: " + datPhong.getUser().getName(), fontInfor);
        paragraphTenKH.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphSDT = new Paragraph("SDT: " + datPhong.getUser().getSdt(), fontInfor);
        paragraphSDT.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphNull = new Paragraph("\n", fontInfor);
        paragraphNull.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphMaPhong = new Paragraph("\nMa so phong: " + datPhong.getPhong().getMa(), fontTable);
        paragraphNull.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphLoaiPhong = new Paragraph("\nLoai phong: " + datPhong.getPhong().getLoaiPhong().getTenLoaiPhong(), fontTable);
        paragraphNull.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphGiaPhong = new Paragraph("\nGia phong: " + String.valueOf(formatter.format(datPhong.getPhong().getGiaPhong())) + "VND/ngay", fontTable);
        paragraphNull.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphNgayNhan = new Paragraph("\nNgay nhan phong: " + sdf2.format(sdf1.parse(String.valueOf(datPhong.getCheckIn()))), fontTable);
        paragraphNull.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphNgayTra = new Paragraph("\nNgay tra phong: " + sdf2.format(sdf1.parse(String.valueOf(datPhong.getCheckOut()))), fontTable);
        paragraphNull.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphSoNgay = new Paragraph("\nSo ngay thue: " + String.valueOf(datPhong.getCheckOut().getDayOfMonth()-datPhong.getCheckIn().getDayOfMonth()) + " ngay", fontTable);
        paragraphNull.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphPhiDichVu = new Paragraph("\nPhi dich vu: " + String.valueOf(formatter.format(tongGia - tienPhong)) + "VND", fontTable);
        paragraphNull.setAlignment(Paragraph.ALIGN_LEFT);
//        PdfPCell headerCell2 = new PdfPCell(new Phrase("Ma so phong", fontTable));
//        PdfPCell headerCell3 = new PdfPCell(new Phrase(datPhong.getPhong().getMa(), fontTable));
//        PdfPCell cellGiaPhong = new PdfPCell(new Phrase("Gia phong", fontTable));
//        PdfPCell cellGiaPhong2 = new PdfPCell(new Phrase(String.valueOf(formatter.format(datPhong.getPhong().getGiaPhong())) + "/ngay", fontTable));
//        PdfPCell cellSoNguoi = new PdfPCell(new Phrase("So nguoi", fontTable));
//        PdfPCell cellSoNguoi2 = new PdfPCell(new Phrase(datPhong.getSoNguoi().toString(), fontTable));
//        PdfPCell cellCheckin = new PdfPCell(new Phrase("Ngay nhan", fontTable));
//        PdfPCell cellCheckin2 = new PdfPCell(new Phrase(sdf2.format(sdf1.parse(String.valueOf(datPhong.getCheckIn()))), fontTable));
//        PdfPCell cellCheckout = new PdfPCell(new Phrase("Ngay tra", fontTable));
//        PdfPCell cellCheckout2 = new PdfPCell(new Phrase(sdf2.format(sdf1.parse(String.valueOf(datPhong.getCheckOut()))), fontTable));
//        PdfPCell cellSoNgay = new PdfPCell(new Phrase("So ngay thue ", fontTable));
//        PdfPCell cellSoNgay2 = new PdfPCell(new Phrase(String.valueOf(datPhong.getCheckOut().getDayOfMonth()-datPhong.getCheckIn().getDayOfMonth()), fontTable));
//        PdfPCell cellPhiDichVu = new PdfPCell(new Phrase("Phi dich vu ", fontTable));
//        PdfPCell cellPhiDichVu2 = new PdfPCell(new Phrase(String.valueOf(formatter.format(tongGia - tienPhong)), fontTable));
//        cellGiaPhong.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cellGiaPhong2.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cellSoNguoi.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cellSoNguoi2.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cellCheckin.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cellCheckin2.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cellCheckout.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cellCheckout2.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cellSoNgay.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cellSoNgay2.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cellPhiDichVu.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cellPhiDichVu2.setHorizontalAlignment(Element.ALIGN_CENTER);
//        pdfPTable.addCell(cellGiaPhong);
//        pdfPTable.addCell(cellGiaPhong2);
//        pdfPTable.addCell(cellSoNguoi);
//        pdfPTable.addCell(cellSoNguoi2);
//        pdfPTable.addCell(cellCheckin);
//        pdfPTable.addCell(cellCheckin2);
//        pdfPTable.addCell(cellCheckout);
//        pdfPTable.addCell(cellCheckout2);
//        pdfPTable.addCell(cellSoNgay);
//        pdfPTable.addCell(cellSoNgay2);
//        pdfPTable.addCell(cellPhiDichVu);
//        pdfPTable.addCell(cellPhiDichVu2);
//        pdfPTable.completeRow();

        //
        String formattedTongTien = formatter.format(datPhong.getTongGia());
        Paragraph paragraphTongTien = new Paragraph("\nTong thanh toan: " + formattedTongTien + "VND", fontInfor);
        paragraphTongTien.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraph9 = new Paragraph("\n", fontInfor);
        paragraph9.setAlignment(Paragraph.ALIGN_LEFT);
        paragraph9.add(lineSeparator);
        Paragraph paragraphEnd = new Paragraph("\nCAM ON QUY KHACH DA SU DUNG \nDICH VU CUA CHUNG TOI!", fontTitle);
        paragraphEnd.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        document.add(paragraphPhong);
        document.add(paragraphDate);
        document.add(paragraphLine);
        document.add(paragraphMaKH);
        document.add(paragraphTenKH);
        document.add(paragraphSDT);
        document.add(paragraphNull);
        document.add(paragraphMaPhong);
        document.add(paragraphLoaiPhong);
        document.add(paragraphGiaPhong);
        document.add(paragraphNgayNhan);
        document.add(paragraphNgayTra);
        document.add(paragraphSoNgay);
        document.add(paragraphPhiDichVu);
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

        List<DatPhongResponse> dataList = (List<DatPhongResponse>) datPhongRepository.get(id);
        for (DatPhongResponse data : dataList) {
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    // Thay thế trường dữ liệu trong cặp {{}}
                    text = text.replace("{{fieldName}}", data.getFieldName());
                    run.setText(text, 0);
                }
            }
        }

        try (FileOutputStream out = new FileOutputStream("output.pdf")) {
            PDDocument pdfDocument = new PDDocument();
            PDPage page = new PDPage();
            pdfDocument.addPage(page);

            pdfDocument.save(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
