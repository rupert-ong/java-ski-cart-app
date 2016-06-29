package orders;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

public class OrderHandler extends HttpServlet {
    private BigDecimal total = BigDecimal.ZERO;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try{
            total = BigDecimal.ZERO;
            List<LineItem> lineItems = getAndVerifyInputs(req, res);

            if(lineItems.size() > 0) {
                sendResponse(req, res, lineItems, this.total);
            } else {
                sendErrorResponse(req, res, "Please check something to purchase.");
            }
        } catch(Exception e){}
    }

    /**
     * Get all checked items from the table and store them in a list
     * @param  req Http Request
     * @param  res Http Response
     * @return     List of LineItems
     */
    private List<LineItem> getAndVerifyInputs(HttpServletRequest req, HttpServletResponse res) {
        List<LineItem> lineItems = new ArrayList<LineItem>();
        int ind = 1;
        int rowCount = -1;

        try {
            rowCount = Integer.parseInt(req.getParameter("rowCount").trim());  // Hidden input field
            for(int i=0; i<rowCount; i++){
                String check = "check-" + ind;
                int qty = Integer.parseInt(req.getParameter("num-"+ind).trim());

                if(req.getParameter(check) != null && qty>0){  // checkbox checked and qty is greater than 0
                    int id = Integer.parseInt(req.getParameter("id-"+ind).trim());
                    String product = req.getParameter("prod-"+ind).trim();
                    String category = req.getParameter("cat-"+ind).trim();
                    BigDecimal price = new BigDecimal(req.getParameter("price-"+ind).trim());

                    lineItems.add(new LineItem(qty, id, product, category, price));

                    if(qty > 0) {
                        BigDecimal subTotal = price.multiply(new BigDecimal(qty));
                        this.total = this.total.add(subTotal);
                    }
                }
                ind++;
            }
        } catch(NumberFormatException e){
            sendErrorResponse(req, res, "Data validation errors occurred during processing");
            return null;
        }

        return lineItems;
    }

    private void sendResponse(HttpServletRequest req, HttpServletResponse res, List<LineItem> items, BigDecimal totalPrice) {
        try{
            HttpSession session = req.getSession();
            session.setAttribute("items", items);
            session.setAttribute("total", totalPrice);
            res.sendRedirect("confirmOrder.jsp");
        } catch(IOException e) {}
    }

    private void sendErrorResponse(HttpServletRequest req, HttpServletResponse res, String msg) {
        try{
            HttpSession session = req.getSession();
            session.setAttribute("result", msg);
            res.sendRedirect("badResult.jsp");
        } catch(IOException e) {}
    }
}