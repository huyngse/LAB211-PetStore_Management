/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;

/**
 *
 * @author huyngo
 */
public class OrderHeader implements Comparable<OrderHeader> {

    //ATTRIBUTES
    private String id;
    private String date;
    private String customerName;

    //CONSTRUCTORS
    public OrderHeader() {
    }

    public OrderHeader(String id) {
        this.id = id;
    }

    public OrderHeader(String id, String date, String customerName) {
        this.id = id;
        this.date = date;
        this.customerName = customerName;
    }

    //GETTERS AND SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    //OVERRIDE METHODS
    @Override
    public int compareTo(OrderHeader o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "OrderHeader{" + "id=" + id + ", date=" + date + ", customerName=" + customerName + '}';
    }

    @Override
    public boolean equals(Object obj) {
        OrderHeader other = (OrderHeader) obj;
        return other.getId().equalsIgnoreCase(this.id);
    }
    
}
