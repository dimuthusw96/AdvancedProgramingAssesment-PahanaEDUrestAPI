/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 *
 * @author Dimuthu
 */
public class Item {
       public int id;
    private String name;
    private double price;
    private int quantity;
    private int reorderlevel;

    public void setReorderlevel(int reorderlevel) {
        this.reorderlevel = reorderlevel;
    }

    public int getReorderlevel() {
        return reorderlevel;
    }
    
      public Item() {
      
      }

    public Item(int id, String name, double price, int quantity,int reorderlevel) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.reorderlevel=reorderlevel;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
