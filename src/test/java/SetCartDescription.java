import lombok.Data;

@Data
public class SetCartDescription {

    private int product_id;
    private int quantity;

    public SetCartDescription(int product_id, int quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }
}
