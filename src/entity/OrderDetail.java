/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author huyngo
 */
public class OrderDetail {

    //ATTRIBUTES
    private String id;
    private String petId;
    int quantity;
    double cost;

    //CONSTRUCTORS
    public OrderDetail() {
    }

    public OrderDetail(String id) {
        this.id = id;
    }

    public OrderDetail(String id, String petId, int quantity, double cost) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.cost = cost;
    }
    
    //GETTERS AND SETTERS
     public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    //OVERRIDE METHODS

    @Override
    public String toString() {
        return "OrderDetail{" + "id=" + id + ", petId=" + petId + ", quantity=" + quantity + ", cost=" + cost + '}';
    }

}
