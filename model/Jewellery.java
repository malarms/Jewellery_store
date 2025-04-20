package model;

import java.time.LocalDate;

public class Jewellery {
    private int id;
    private String name;
    private String type;  
    private String material;  
    private double weight; 
    private double price;
    private int stockQuantity;
    private LocalDate dateAdded;


    public Jewellery() {}

    public Jewellery(String name, String type, String material, 
                    double weight, double price, int stockQuantity) {
        this.name = name;
        this.type = type;
        this.material = material;
        this.weight = weight;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.dateAdded = LocalDate.now();
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    
    public LocalDate getDateAdded() { return dateAdded; }
    public void setDateAdded(LocalDate dateAdded) { this.dateAdded = dateAdded; }


    public String toString() {
        return String.format("Jewellery [ID: %d, Name: %s, Type: %s, Material: %s, Weight: %.2fg, Price: ₹%.2f, Stock: %d]",
                id, name, type, material, weight, price, stockQuantity);
    }
}