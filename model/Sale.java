package model;

import java.time.LocalDateTime;

public class Sale {
    private int id;
    private int customerId;
    private int jewelleryId;
    private int quantity;
    private double totalPrice;
    private LocalDateTime saleDate;
    private String paymentMethod;
	
    // No-arg constructor
    public Sale() {
        this.saleDate = LocalDateTime.now();
    }
    
    // New constructor
    public Sale(int customerId, int jewelleryId, int quantity) {
        this(customerId, jewelleryId, quantity, 0.0, "CASH");
    }


    public Sale(int customerId, int jewelleryId, int quantity, 
               double totalPrice, String paymentMethod) {
        this.customerId = customerId;
        this.jewelleryId = jewelleryId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.saleDate = LocalDateTime.now();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    
    public int getJewelleryId() { return jewelleryId; }
    public void setJewelleryId(int jewelleryId) { this.jewelleryId = jewelleryId; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    
    public LocalDateTime getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDateTime saleDate) { this.saleDate = saleDate; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String toString() {
        return String.format("Sale [ID: %d, Customer: %d, Jewellery: %d, Qty: %d, Total: $%.2f, Method: %s]",
                id, customerId, jewelleryId, quantity, totalPrice, paymentMethod);
    }
}