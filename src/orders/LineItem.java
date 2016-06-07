package orders;

import java.math.BigDecimal;

public class LineItem {
    private int qty;
    private int id;
    private String product;
    private String category;
    private BigDecimal price;

    public LineItem(int qty, int id, String product, String category, BigDecimal price) {
        this.qty = qty;
        this.id = id;
        this.product = product;
        this.category = category;
        this.price = price;
    }

    public int getQty() { return this.qty; }
    public int getId() { return this.id; }
    public String getProduct() { return this.product; }
    public String getCategory() { return this.category; }
    public BigDecimal getPrice() { return this.price; }
}