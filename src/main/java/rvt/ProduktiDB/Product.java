package rvt.ProduktiDB;

public class Product {
    private int id;
    private String name;
    private double price;
    private int categoryId;
    private int quantity;

    public Product() {
    }

    public Product(int id, String name, double price, int categoryId, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.quantity = quantity;
    }

    public Product(String name, double price, int categoryId, int quantity) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.quantity = quantity;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + price + " EUR | cat=" + categoryId + " | qty=" + quantity;
    }
}
