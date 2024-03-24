import lombok.Data;

@Data
public class Products {
    private String name;
    private String category;
    private double price;
    private int discount;

    public Products(String name, String category, double price, int discount) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.discount = discount;
    }
}


