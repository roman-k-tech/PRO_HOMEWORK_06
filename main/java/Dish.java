import javax.persistence.*;

@Entity
@Table(name="RESTORAUNT_MENU")
public class Dish
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="name", nullable = false)
    private String name;
    private int price;
    private int weight;
    private boolean discount;

    public Dish() { }

    public Dish(String name, int price, int weight, boolean discount)
    {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public boolean isDiscount() {
        return discount;
    }
    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    @Override
    public String toString()
    {
        return "Dish name = " + name + ", price = " + price + ", weight = " + weight + ", discount = " + discount;
    }
}
