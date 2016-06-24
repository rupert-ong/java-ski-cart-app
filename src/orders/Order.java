package orders;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class Order {

  private String email;
  private List<LineItem> items;
  private BigDecimal total;

  public Order(String email, List<LineItem> items, BigDecimal total){
    this.email = email;
    this.items = items;
    this.total = total;
  }

  public String getEmail() { return this.email; }
  public List<LineItem> getItems() { return this.items; }
  public BigDecimal getTotal() { return this.total; }
}