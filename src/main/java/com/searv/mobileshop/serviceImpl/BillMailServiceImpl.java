package com.searv.mobileshop.serviceImpl;

import com.searv.mobileshop.entity.Bill;
import com.searv.mobileshop.entity.BillItem;
import com.searv.mobileshop.service.BillMailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class BillMailServiceImpl implements BillMailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendBillMail(Bill bill) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    true,
                    "UTF-8"
            );

            helper.setFrom("shivaratnaraut@gmail.com");
            helper.setTo(bill.getCustomerEmail());
            helper.setSubject("Jagadamb Mobile Shop - Your Bill");

            helper.setText(buildBillHtml(bill), true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send bill email : " + e.getMessage());
        }
    }

    private String buildBillHtml(Bill bill) {

        StringBuilder itemRows = new StringBuilder();

        int count = 1;

        for (BillItem item : bill.getItems()) {
            itemRows.append("<tr>")
                    .append("<td style='padding:12px;border:1px solid #dbeafe;text-align:center;'>").append(count++).append("</td>")
                    .append("<td style='padding:12px;border:1px solid #dbeafe;text-align:center;'>").append(item.getBrand()).append("</td>")
                    .append("<td style='padding:12px;border:1px solid #dbeafe;text-align:center;'>").append(item.getModel()).append("</td>")
                    .append("<td style='padding:12px;border:1px solid #dbeafe;text-align:center;'>").append(item.getColour()).append("</td>")
                    .append("<td style='padding:12px;border:1px solid #dbeafe;text-align:center;'>").append(item.getRam()).append("</td>")
                    .append("<td style='padding:12px;border:1px solid #dbeafe;text-align:center;'>").append(item.getStorage()).append("</td>")
                    .append("<td style='padding:12px;border:1px solid #dbeafe;text-align:center;'>").append(item.getQuantity()).append("</td>")
                    .append("<td style='padding:12px;border:1px solid #dbeafe;text-align:center;'>₹ ").append(item.getPrice()).append("</td>")
                    .append("<td style='padding:12px;border:1px solid #dbeafe;text-align:center;'>₹ ").append(item.getAmount()).append("</td>")
                    .append("</tr>");
        }

        return ""
                + "<html>"
                + "<body style='margin:0;padding:0;background:#eef2f7;font-family:Arial,sans-serif;'>"

                + "<div style='padding:25px;'>"
                + "<div style='max-width:980px;margin:auto;background:#ffffff;border-radius:18px;overflow:hidden;box-shadow:0 10px 30px rgba(0,0,0,0.18);'>"

                + "<div style='background:linear-gradient(135deg,#061b49,#0052cc);padding:35px;color:white;'>"
                + "<table width='100%'>"
                + "<tr>"
                + "<td style='width:90px;'>"
                + "<div style='width:75px;height:75px;border-radius:50%;background:white;color:#0052cc;text-align:center;line-height:75px;font-size:38px;'>📱</div>"
                + "</td>"
                + "<td>"
                + "<h1 style='margin:0;font-size:34px;letter-spacing:1px;'>JAGADAMBA<br/>MOBILE SHOP <span style='color:#facc15;font-size:22px;'>& ELECTRONICS</span></h1>"
                + "<p style='margin:12px 0 0;font-size:16px;'>📞 Mobile Sales &nbsp; | &nbsp; 🎧 Accessories &nbsp; | &nbsp; 🛠 Repairing</p>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</div>"

                + "<div style='padding:35px;'>"

                + "<table width='100%' style='margin-bottom:30px;'>"
                + "<tr>"
                + "<td>"
                + "<h2 style='margin:0;color:#0f2a5f;font-size:30px;'>INVOICE / BILL</h2>"
                + "<div style='display:inline-block;margin-top:10px;background:#0052cc;color:white;padding:8px 18px;border-radius:8px;font-weight:bold;'>Bill No: #" + bill.getId() + "</div>"
                + "</td>"
                + "<td style='text-align:right;color:#0f2a5f;'>"
                + "<div style='font-size:18px;font-weight:bold;'>📅 Bill Date</div>"
                + "<div style='margin-top:8px;font-weight:bold;color:#111827;'>" + bill.getBillDate() + "</div>"
                + "</td>"
                + "</tr>"
                + "</table>"

                + "<div style='border:1px solid #bfdbfe;background:#f8fbff;border-radius:14px;padding:24px;margin-bottom:28px;'>"
                + "<h3 style='margin:0 0 20px;color:#0052cc;font-size:22px;'>👤 CUSTOMER DETAILS</h3>"
                + "<table width='100%'>"
                + "<tr>"
                + "<td style='padding:8px;color:#111827;'><b>Name</b><br/>" + bill.getCustomerName() + "</td>"
                + "<td style='padding:8px;color:#111827;'><b>Mobile Number</b><br/>" + bill.getMobileNumber() + "</td>"
                + "<td style='padding:8px;color:#111827;'><b>Email Address</b><br/>" + bill.getCustomerEmail() + "</td>"
                + "</tr>"
                + "</table>"
                + "</div>"

                + "<table width='100%' style='border-collapse:collapse;margin-bottom:30px;font-size:13px;'>"
                + "<thead>"
                + "<tr style='background:#004aad;color:white;'>"
                + "<th style='padding:12px;border:1px solid #2563eb;'>No.</th>"
                + "<th style='padding:12px;border:1px solid #2563eb;'>Brand</th>"
                + "<th style='padding:12px;border:1px solid #2563eb;'>Model</th>"
                + "<th style='padding:12px;border:1px solid #2563eb;'>Colour</th>"
                + "<th style='padding:12px;border:1px solid #2563eb;'>RAM</th>"
                + "<th style='padding:12px;border:1px solid #2563eb;'>Storage</th>"
                + "<th style='padding:12px;border:1px solid #2563eb;'>Qty</th>"
                + "<th style='padding:12px;border:1px solid #2563eb;'>Price</th>"
                + "<th style='padding:12px;border:1px solid #2563eb;'>Amount</th>"
                + "</tr>"
                + "</thead>"
                + "<tbody>"
                + itemRows
                + "</tbody>"
                + "</table>"

                + "<table width='100%' style='margin-bottom:30px;'>"
                + "<tr>"
                + "<td style='width:48%;vertical-align:top;'>"
                + "<div style='background:#fff8e6;border:1px solid #fde68a;border-radius:14px;padding:22px;color:#92400e;'>"
                + "<h3 style='margin:0 0 10px;'>🧾 Thank you for shopping with us!</h3>"
                + "<p style='margin:0;color:#111827;line-height:1.6;'>We truly appreciate your business.<br/>Visit us again!</p>"
                + "</div>"
                + "</td>"

                + "<td style='width:52%;vertical-align:top;'>"
                + "<div style='border:1px solid #d1d5db;border-radius:14px;overflow:hidden;margin-left:25px;'>"

                + "<table width='100%' style='border-collapse:collapse;'>"
                + "<tr>"
                + "<td style='padding:16px;font-weight:bold;'>Total Amount</td>"
                + "<td style='padding:16px;text-align:right;font-weight:bold;'>₹ " + bill.getTotalAmount() + "</td>"
                + "</tr>"

                + "<tr>"
                + "<td style='padding:16px;color:#dc2626;font-weight:bold;border-top:1px solid #e5e7eb;'>Discount</td>"
                + "<td style='padding:16px;text-align:right;color:#dc2626;font-weight:bold;border-top:1px solid #e5e7eb;'>- ₹ " + bill.getDiscount() + "</td>"
                + "</tr>"

                + "<tr style='background:#ecfdf5;'>"
                + "<td style='padding:18px;color:#16a34a;font-size:20px;font-weight:bold;border-top:1px solid #d1d5db;'>FINAL AMOUNT</td>"
                + "<td style='padding:18px;text-align:right;color:#16a34a;font-size:22px;font-weight:bold;border-top:1px solid #d1d5db;'>₹ " + bill.getFinalAmount() + "</td>"
                + "</tr>"
                + "</table>"

                + "</div>"
                + "</td>"
                + "</tr>"
                + "</table>"

                + "<div style='border:1px solid #bfdbfe;background:#f8fbff;border-radius:14px;padding:22px;'>"
                + "<table width='100%'>"
                + "<tr>"
                + "<td style='color:#004aad;font-weight:bold;font-size:18px;'>✅ JAGADAMBA MOBILE SHOP & ELECTRONICS</td>"
                + "<td style='text-align:right;color:#004aad;font-size:28px;font-family:cursive;'>Thank You!</td>"
                + "</tr>"
                + "<tr>"
                + "<td style='color:#374151;padding-top:6px;'>Mobile Sales | Accessories | Repairing</td>"
                + "<td></td>"
                + "</tr>"
                + "</table>"
                + "</div>"

                + "</div>"
                + "</div>"
                + "</div>"

                + "</body>"
                + "</html>";
    }
}