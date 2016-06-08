package orders;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
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
            sendResponse(req, res, lineItems, this.total);
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
        Map params = req.getParameterMap();
        Set paramsSet = params.entrySet();  // entrySet returns a Set that contains Map.Entry objects
        Iterator it = paramsSet.iterator();

        try{
            while(it.hasNext()){
                Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>)it.next();  // Convert Set returned by next() to Map.Entry object
                String key = entry.getKey();
                // String[] value = entry.getValue();

                if(key.startsWith("check-")){
                    entry = (Map.Entry<String, String[]>)it.next();
                    int qty = Integer.parseInt(entry.getValue()[0].trim());

                    entry = (Map.Entry<String, String[]>)it.next();
                    int id = Integer.parseInt(entry.getValue()[0].trim());

                    entry = (Map.Entry<String, String[]>)it.next();
                    String product = entry.getValue()[0].trim();

                    entry = (Map.Entry<String, String[]>)it.next();
                    String category = entry.getValue()[0].trim();
                    
                    entry = (Map.Entry<String, String[]>)it.next();
                    BigDecimal price = new BigDecimal(entry.getValue()[0].trim());

                    lineItems.add(new LineItem(qty, id, product, category, price));

                    if(qty > 0) {
                        BigDecimal subTotal = price.multiply(new BigDecimal(qty));
                        this.total = this.total.add(subTotal);
                    }
                }
            }
        } catch(NumberFormatException e){
            sendErrorResponse(req, res, "Data validation errors occurred during processing");
            return null;
        }

        /*try {
            rowCount = Integer.parseInt(req.getParameter("rowCount").trim());  // Hidden input field
            for(int i=0; i<rowCount; i++){
                String check = "check-" + ind;

                if(req.getParameter(check) != null){  // checkbox checked
                    int qty = Integer.parseInt(req.getParameter("num-"+ind).trim());
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
        }*/

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