package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.repository.DatPhongRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Service
public class PdfGenerator {

    private final DatPhongRepository datPhongRepository;

    public PdfGenerator(DatPhongRepository datPhongRepository) {
        this.datPhongRepository = datPhongRepository;
    }

    public void export(HttpServletResponse response, Long id) throws IOException, DocumentException, ParseException {
        DatPhong datPhong = datPhongRepository.findById(id).get();
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
        Paragraph paragraphEmail = new Paragraph("Email: " + datPhong.getUser().getEmail(), fontInfor);
        paragraphEmail.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraphNull = new Paragraph("\n", fontInfor);
        paragraphNull.setAlignment(Paragraph.ALIGN_LEFT);

        PdfPTable pdfPTable = new PdfPTable(2);
        pdfPTable.setWidthPercentage(100f);
        PdfPCell headerCell2 = new PdfPCell(new Phrase("Ma so phong", fontTable));
        headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell headerCell3 = new PdfPCell(new Phrase(datPhong.getPhong().getMa(), fontTable));
        headerCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(headerCell2);
        pdfPTable.addCell(headerCell3);
        PdfPCell cellGiaPhong = new PdfPCell(new Phrase("Gia phong", fontTable));
        PdfPCell cellGiaPhong2 = new PdfPCell(new Phrase(String.valueOf(formatter.format(datPhong.getPhong().getGiaPhong())) + "/ngay", fontTable));
        PdfPCell cellSoNguoi = new PdfPCell(new Phrase("So nguoi", fontTable));
        PdfPCell cellSoNguoi2 = new PdfPCell(new Phrase(datPhong.getSoNguoi().toString(), fontTable));
        PdfPCell cellCheckin = new PdfPCell(new Phrase("Ngay nhan", fontTable));
        PdfPCell cellCheckin2 = new PdfPCell(new Phrase(sdf2.format(sdf1.parse(String.valueOf(datPhong.getCheckIn()))), fontTable));
        PdfPCell cellCheckout = new PdfPCell(new Phrase("Ngay tra", fontTable));
        PdfPCell cellCheckout2 = new PdfPCell(new Phrase(sdf2.format(sdf1.parse(String.valueOf(datPhong.getCheckOut()))), fontTable));
        PdfPCell cellSoNgay = new PdfPCell(new Phrase("So ngay thue ", fontTable));
        PdfPCell cellSoNgay2 = new PdfPCell(new Phrase(String.valueOf(datPhong.getCheckOut().getDayOfMonth()-datPhong.getCheckIn().getDayOfMonth()), fontTable));
        cellGiaPhong.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellGiaPhong2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellSoNguoi.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellSoNguoi2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellCheckin.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellCheckin2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellCheckout.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellCheckout2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellSoNgay.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellSoNgay2.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(cellGiaPhong);
        pdfPTable.addCell(cellGiaPhong2);
        pdfPTable.addCell(cellSoNguoi);
        pdfPTable.addCell(cellSoNguoi2);
        pdfPTable.addCell(cellCheckin);
        pdfPTable.addCell(cellCheckin2);
        pdfPTable.addCell(cellCheckout);
        pdfPTable.addCell(cellCheckout2);
        pdfPTable.addCell(cellSoNgay);
        pdfPTable.addCell(cellSoNgay2);
        pdfPTable.completeRow();

        //
        int tongTien = (int) (Double.parseDouble(String.valueOf(datPhong.getPhong().getGiaPhong())) * (datPhong.getCheckOut().getDayOfMonth()-datPhong.getCheckIn().getDayOfMonth()));
        String formattedTongTien = formatter.format(tongTien);
        Paragraph paragraphTongTien = new Paragraph("Tong tien: " + formattedTongTien + "VND", fontInfor);
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
        document.add(paragraphEmail);
        document.add(paragraphNull);
        document.add(pdfPTable);
        document.add(paragraphTongTien);
        document.add(paragraph9);
        document.add(paragraphEnd);
        document.close();
    }


}
