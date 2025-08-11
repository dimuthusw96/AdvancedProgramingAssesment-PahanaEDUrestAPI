/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reports;

/**
 *
 * @author Dimuthu
 */
public class ReorderLevels {
      private int id;
      private String name;
      private int quantity;
      private int reorderlevel;

    public void setId(int id) {
        this.id = id;
    }

    public ReorderLevels(int id, String name, int quantity, int reorderlevel) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.reorderlevel = reorderlevel;
    }

    public ReorderLevels() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setReorderlevel(int reorderlevel) {
        this.reorderlevel = reorderlevel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getReorderlevel() {
        return reorderlevel;
    }
      
              
}
