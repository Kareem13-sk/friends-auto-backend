package friends_auto_mobile.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
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

        ByteArrayOutputStream out =
                new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            24
                    );

            Font headingFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            16
                    );

            Font normalFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA,
                            12
                    );

            // TITLE

            Paragraph title =
                    new Paragraph(
                            "Friends Auto Mobile Invoice",
                            titleFont
                    );

            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);

            document.add(new Paragraph(" "));

            // CUSTOMER DETAILS

            document.add(new Paragraph(
                    "Customer Name: "
                            + bill.getCustomerName(),
                    normalFont
            ));

            document.add(new Paragraph(" "));

            // BILL ITEMS HEADING

            Paragraph itemHeading =
                    new Paragraph(
                            "Purchased Items",
                            headingFont
                    );

            document.add(itemHeading);

            document.add(new Paragraph(" "));

            // TABLE

            PdfPTable table =
                    new PdfPTable(4);

            table.setWidthPercentage(100);

            table.setSpacingBefore(10f);

            table.addCell("Product");
            table.addCell("Quantity");
            table.addCell("Price");
            table.addCell("Total");

            for (BillItem item : bill.getItems()) {

                table.addCell(item.getProductName());

                table.addCell(
                        String.valueOf(
                                item.getQuantity()
                        )
                );

                table.addCell(
                        "₹" + item.getPrice()
                );

                table.addCell(
                        "₹" + item.getTotal()
                );
            }

            document.add(table);

            document.add(new Paragraph(" "));

            // BILL SUMMARY

            Paragraph summaryHeading =
                    new Paragraph(
                            "Bill Summary",
                            headingFont
                    );

            document.add(summaryHeading);

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Total Amount: ₹"
                            + bill.getTotalAmount(),
                    normalFont
            ));

            document.add(new Paragraph(
                    "Paid Amount: ₹"
                            + bill.getPaidAmount(),
                    normalFont
            ));

            document.add(new Paragraph(
                    "Balance Amount: ₹"
                            + bill.getBalanceAmount(),
                    normalFont
            ));

            document.add(new Paragraph(" "));

            // FOOTER

            Paragraph footer =
                    new Paragraph(
                            "Thank You For Visiting Friends Auto Mobile",
                            normalFont
                    );

            footer.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(footer);

            document.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return new ByteArrayInputStream(
                out.toByteArray()
        );
    }
}