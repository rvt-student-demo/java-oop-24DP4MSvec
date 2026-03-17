package rvt.Online_Shop;

public class Item {
    private String product;
    private int qty;
    private int unitPrice;
    
    public Item(String product, int qty, int unitPrice){
        this.product = product;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }
    public int price(){
        int price = qty * unitPrice;
        return price;
    }
    public void increaseQuantity(){
        this.qty += 1;
    }

    @Override
    public String toString(){
        return this.product + ": " + this.qty;
    }
}
