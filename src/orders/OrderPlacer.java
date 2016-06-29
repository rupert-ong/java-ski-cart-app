package orders;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

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

            if(lineItems.size()>0) {
                saveToDB(lineItems);
                sendResponse(req, res, lineItems, total);
            } else {
                sendErrorResponse(req, res, "Please check something to purchase.");
            }
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
        // Create Order instance
        String email = "customer@no-email.com";
        Order order = new Order(email, lineItems, this.total);

        // Save to DB
        Connection conn = getConnection();
        String sql = "INSERT INTO customer_order(pid, email, quantity) VALUES (?,?,?)";

        for(LineItem item : order.getItems()) {
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, item.getId());
                pstmt.setString(2, order.getEmail());
                pstmt.setInt(3, item.getQty());
                pstmt.executeUpdate();
                pstmt.close();
            } catch(SQLException e){}
        }

        try {
            conn.close();
        } catch(SQLException e){}

        // Send Email
        String msg = "Order Summary: \n";

        for(LineItem item : order.getItems()) {
            msg += item.toString();
        }
        msg += "Total: $" + order.getTotal() + "\n";
        sendConfirmationEmail(order.getEmail(), msg);
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

    private void sendErrorResponse(HttpServletRequest req, HttpServletResponse res, String msg) {
        try{
            HttpSession session = req.getSession();
            session.setAttribute("result", msg);
            res.sendRedirect("badResult.jsp");
        } catch(IOException e) {}
    }

    /**
     * Convenience method to connect to DB
     * @return connection to database
     */
    private Connection getConnection(){
        String uri = "jdbc:postgresql://localhost/skistuff";
        Properties props = setLoginForDB("rupert", "secret");
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(uri, props);
        } 
        catch(ClassNotFoundException e){}
        catch(SQLException e) {}

        return conn;
    }

    /**
     * Sets login for database
     * @param  uname  
     * @param  passwd 
     * @return A property (key, value) for login info
     */
    private Properties setLoginForDB(final String uname, final String passwd){
        Properties props = new Properties();
        props.setProperty("user", uname);
        props.setProperty("password", passwd);
        return props;
    }

}