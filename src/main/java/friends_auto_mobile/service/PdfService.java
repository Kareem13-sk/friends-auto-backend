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

            title.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(title);

            document.add(
                    new Paragraph(" ")
            );

            // CUSTOMER DETAILS

            document.add(
                    new Paragraph(
                            "Customer Name : "
                                    + bill.getCustomerName(),
                            normalFont
                    )
            );

            document.add(
                    new Paragraph(" ")
            );

            // ITEMS HEADING

            Paragraph itemHeading =
                    new Paragraph(
                            "Purchased Items",
                            headingFont
                    );

            document.add(itemHeading);

            document.add(
                    new Paragraph(" ")
            );

            // TABLE

            PdfPTable table =
                    new PdfPTable(7);

            table.setWidthPercentage(100);

            table.setSpacingBefore(10f);

            table.addCell("S.No");
            table.addCell("Product");
            table.addCell("Qty");
            table.addCell("%");
            table.addCell("Actual Price");
            table.addCell("Final Price");
            table.addCell("Total");

            int serialNo = 1;

            for (BillItem item : bill.getItems()) {

                table.addCell(
                        String.valueOf(
                                serialNo++
                        )
                );

                table.addCell(
                        item.getProductName()
                );

                table.addCell(
                        String.valueOf(
                                item.getQuantity()
                        )
                );

                table.addCell(
                        String.valueOf(
                                item.getPercentage()
                        ) + "%"
                );

                table.addCell(
                        "₹" +
                                String.format(
                                        "%.2f",
                                        item.getActualPrice()
                                )
                );

                table.addCell(
                        "₹" +
                                String.format(
                                        "%.2f",
                                        item.getPrice()
                                )
                );

                table.addCell(
                        "₹" +
                                String.format(
                                        "%.2f",
                                        item.getTotal()
                                )
                );
            }

            document.add(table);

            document.add(
                    new Paragraph(" ")
            );

            // BILL SUMMARY

            Paragraph summaryHeading =
                    new Paragraph(
                            "Bill Summary",
                            headingFont
                    );

            document.add(summaryHeading);

            document.add(
                    new Paragraph(" ")
            );

            document.add(
                    new Paragraph(
                            "Total Amount : ₹"
                                    + String.format(
                                    "%.2f",
                                    bill.getTotalAmount()
                            ),
                            normalFont
                    )
            );

            document.add(
                    new Paragraph(
                            "Paid Amount : ₹"
                                    + String.format(
                                    "%.2f",
                                    bill.getPaidAmount()
                            ),
                            normalFont
                    )
            );

            document.add(
                    new Paragraph(
                            "Balance Amount : ₹"
                                    + String.format(
                                    "%.2f",
                                    bill.getBalanceAmount()
                            ),
                            normalFont
                    )
            );

            document.add(
                    new Paragraph(" ")
            );

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
