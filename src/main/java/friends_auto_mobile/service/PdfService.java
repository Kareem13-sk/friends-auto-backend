package friends_auto_mobile.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import friends_auto_mobile.entity.Bill;
import friends_auto_mobile.entity.BillItem;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public ByteArrayInputStream generateInvoice(Bill bill) {

        Document document = new Document();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont =
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);

            Font normalFont =
                    FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph title =
                    new Paragraph("Friends Auto Mobile Invoice", titleFont);

            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Customer Name: " + bill.getCustomerName(),
                    normalFont));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Bill Items:",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));

            document.add(new Paragraph(" "));

            for (BillItem item : bill.getItems()) {

                document.add(new Paragraph(
                        "Product: " + item.getProductName(),
                        normalFont));

                document.add(new Paragraph(
                        "Quantity: " + item.getQuantity(),
                        normalFont));

                document.add(new Paragraph(
                        "Price: ₹" + item.getPrice(),
                        normalFont));

                document.add(new Paragraph(
                        "Total: ₹" + item.getTotal(),
                        normalFont));

                document.add(new Paragraph(" "));
            }

            document.add(new Paragraph(
                    "Total Amount: ₹" + bill.getTotalAmount(),
                    normalFont));

            document.add(new Paragraph(
                    "Paid Amount: ₹" + bill.getPaidAmount(),
                    normalFont));

            document.add(new Paragraph(
                    "Balance Amount: ₹" + bill.getBalanceAmount(),
                    normalFont));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Thank You For Visiting Friends Auto Mobile",
                    normalFont));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}