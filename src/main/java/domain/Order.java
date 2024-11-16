package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Order implements Identifiable<Integer>{
    private Integer id;
    private boolean status;
    private Cake cake;
    private String name;

    public Order() {}
    public Order(Integer id, boolean status, Cake cake, String name) {
        this.id = id;
        this.status = status;
        this.cake = cake;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Cake getCake() {
        return cake;
    }

    @JsonIgnore
    public Integer getCakeId() {
        return cake.getId();
    }

    public void setCake(Cake cake) {
        this.cake = cake;
    }

    public boolean changeStatus() {
        System.out.println("Order status changed to " + !this.status);
        return this.status = !this.status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                ", cake=" + cake +
                ", name='" + name + '\'' +
                '}';
    }
}
