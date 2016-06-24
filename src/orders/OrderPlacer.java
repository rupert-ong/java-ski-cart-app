package orders;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class OrderPlacer extends HttpServlet{
    private BigDecimal total = BigDecimal.ZERO;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            total = BigDecimal.ZERO;
            List<LineItem> lineItems = getConfirmationInputs(req, res);
            saveToDB(lineItems);
            sendResponse(req, res, lineItems, total);
        } catch(Exception e){}
    }

    private List<LineItem> getConfirmationInputs(HttpServletRequest req, HttpServletResponse res) {

        List<LineItem> lineItems = new ArrayList<LineItem>();
        int ind = 1;
        int rowCount = -1;
        try {
            rowCount = Integer.parseInt(req.getParameter("rowCount").trim());
            for(int i=0; i<rowCount; i++){
                int qty = Integer.parseInt(req.getParameter("num-"+ind).trim());
                if(qty<1){
                    ind++;
                    continue;
                }
                int id = Integer.parseInt(req.getParameter("id-"+ind).trim());
                String product = req.getParameter("prod-"+ind).trim();
                String category = req.getParameter("cat-"+ind).trim();
                BigDecimal price = new BigDecimal(req.getParameter("price-"+ind).trim());
                lineItems.add(new LineItem(qty, id, product, category, price));
                if(qty>0){
                    BigDecimal subTotal = price.multiply(new BigDecimal(qty));
                    this.total = this.total.add(subTotal);
                }
                ind++;
            }
        } catch(NumberFormatException e) {}

        return lineItems;
    }

    private void saveToDB(List<LineItem> lineItems) {
        String msg = "Order Summary: \n";

        for(LineItem item: lineItems) {
            msg += item.toString();
        }
        msg += "Total: $" + this.total + "\n";
        sendConfirmationEmail("customer@no-email.com", msg);
    }

    private void sendConfirmationEmail(String to, String confirmMsg) {
        String from = "webmaster@skistuff.com";
        String smtp_server = "localhost";
        int smtp_port = 2525;

        Properties props = new Properties();
        props.put("mail.smtp.host", smtp_server);
        props.put("mail.smtp.port", smtp_port);
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] addr = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, addr);
            msg.setSubject("Ski Equipment Order Confirmation");
            msg.setSentDate(new Date());
            msg.setText(confirmMsg);
            Transport.send(msg);
        } catch(MessagingException e){}
    }

    private void sendResponse(HttpServletRequest req, HttpServletResponse res, List<LineItem> items, BigDecimal totalPrice) {
        try {
            HttpSession session = req.getSession();
            session.setAttribute("items", items);
            session.setAttribute("total", totalPrice);
            res.sendRedirect("placeOrder.jsp");
        } catch(IOException e){}
    }
}