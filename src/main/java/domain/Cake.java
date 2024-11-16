package domain;

import java.io.Serializable;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cake implements Identifiable<Integer>, Serializable {
    private Integer id;
    private int weight;
    private int price;
    private String type;

    public Cake() {
    }
    public Cake(int id, int weight, int price, String type) {
        this.id = id;
        this.weight = weight;
        this.price = price;
        this.type = type;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Cake{" +
                "id=" + id +
                ", weight=" + weight +
                ", price=" + price +
                ", type='" + type + '\'' +
                '}';
    }

    public boolean isEmtpy() {
        return this.id == null;
    }

    public boolean isEmpty() {
        return this.id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cake cake = (Cake) o;
        return weight == cake.weight &&
                price == cake.price &&
                Objects.equals(id, cake.id) &&
                Objects.equals(type, cake.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weight, price, type);
    }
}
