/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;


/**
 *
 * @author Dimuthu
 */
public class users {
    private int id;
    private String userName;
    private String password;

    public void setId(int id) {
        this.id = id;
    }

    public void setUserNname(String userNname) {
        this.userName = userNname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUserNname() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public users(int id, String userNname, String password) {
        this.id = id;
        this.userName = userNname;
        this.password = password;
    }
    public users(){
        
    }
}
