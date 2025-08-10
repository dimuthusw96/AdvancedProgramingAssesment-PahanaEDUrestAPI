/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilsTest;

import Utils.Bill;
import Utils.BillItem;
import Utils.Customer;
import Utils.Item;
import Utils.Utils;
import Utils.users;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import services.BillService;
import services.CustomerService;
import services.ItemService;
import services.userssService;

/**
 *
 * @author Dimuthu
 */
public class UtilsTest {

    public Utils utils;
    public services.CustomerService customerService;
    public services.BillService billingservice;
    public services.ItemService itemService;
    public services.userssService userService;

    @BeforeEach
    public void setUp() {
        utils = new Utils();
        customerService = new CustomerService();
        billingservice = new BillService();
        itemService = new ItemService();
        userService=new userssService();
        System.out.println("Utils instance created: " + utils);
    }
    // Customer tests

    @Test
    public void testGetCustomers() {
        List<Customer> customers = customerService.getCustomers();
        assertNotNull(customers);
        assertTrue(customers.size() >= 0);
    }

    @Test
    public void testGetCustomerById() {
        Customer customer = customerService.getCustomerById(375);
        assertNotNull(customer);
        assertEquals(375, customer.getId());
        assertNotNull(customer.getName());
    }

    @Test
    public void testCreateUpdateDeleteCustomer() {
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setEmail("test@example.com");
        customer.setAddress("123 Test St");
        customer.setMobile("1234567890");
        customer.setUnit_consumed(0);

        boolean created = customerService.createCustomer(customer);
        assertTrue(created);
        assertTrue(customer.getId() > 0);

        customer.setName("Updated Customer");
        customer.setUnit_consumed(10);
        boolean updated = customerService.updateCustomer(customer);
        assertTrue(updated);

        Customer updatedCustomer = customerService.getCustomerById(customer.getId());
        assertEquals("Updated Customer", updatedCustomer.getName());
        assertEquals(10, updatedCustomer.getUnit_consumed());

        boolean deleted = customerService.deleteCustomer(customer.getId());
        assertTrue(deleted);

        assertNull(customerService.getCustomerById(customer.getId()));
    }

    //itemtest
    @Test
    public void testGetItems() {
        List<Item> items = itemService.getItems();
        assertNotNull(items);
        assertTrue(items.size() >= 0);
    }

    @Test
    public void testGetItemById() {
        Item item = itemService.getItemById(287);
        assertNotNull(item);
        assertEquals(287, item.getId());
        assertNotNull(item.getName());
    }

    @Test
    public void testCreateUpdateDeleteItem() {
        Item item = new Item();
        item.setName("Test Item");
        item.setPrice(9.99);
        item.setQuantity(100);

        boolean created = itemService.createItem(item);
        assertTrue(created);
        assertTrue(item.getId() > 0);

        item.setName("Updated Item");
        item.setPrice(19.99);
        item.setQuantity(200);
        boolean updated = itemService.updateItem(item);
        assertTrue(updated);

        Item updatedItem = itemService.getItemById(item.getId());
        assertEquals("Updated Item", updatedItem.getName());
        assertEquals(19.99, updatedItem.getPrice());
        assertEquals(200, updatedItem.getQuantity());

        boolean deleted = itemService.deleteItem(item.getId());
        assertTrue(deleted);

        assertNull(itemService.getItemById(item.getId()));
    }

    //bill
    @Test
    public void testCreateBill() {
        // First create a customer for the bill
        Customer customer = new Customer();
        customer.setName("Bill Test Customer");
        customer.setEmail("bill@test.com");
        customer.setAddress("123 Bill St");
        customer.setMobile("9876543210");
        customer.setUnit_consumed(0);
        boolean custCreated = customerService.createCustomer(customer);
        assertTrue(custCreated);

        // Create an item for the bill
        Item item = new Item();
        item.setName("Bill Test Item");
        item.setPrice(5.00);
        item.setQuantity(50);
        boolean itemCreated = itemService.createItem(item);
        assertTrue(itemCreated);

        // Create bill item
        BillItem billItem = new BillItem();
        billItem.setItem(item);
        billItem.setQuantity(2);
        billItem.setPrice(item.getPrice());
        billItem.setSubtotal(item.getPrice() * 2);

        Bill bill = new Bill();
        bill.setCustomer(customer);
        bill.setBillDate(new Date());
        bill.setTotalAmount(billItem.getSubtotal());
        bill.getBillItems().add(billItem);

        boolean billCreated = billingservice.createBill(bill);
        assertTrue(billCreated);
        assertTrue(bill.getId() > 0);

        // Cleanup - delete created customer and item
        customerService.deleteCustomer(customer.getId());
        itemService.deleteItem(item.getId());
    }

    //users
    @Test
    public void testGetusers() {
        List<users> user = userService.getusers();
        assertNotNull(user);
        assertTrue(user.size() >= 0);
    }

    @Test
    public void testGetuserById() {
        users user = userService.getUserById(55);
        assertNotNull(user);
        assertEquals(55, user.getId());
        assertNotNull(user.getUserNname());
    }

    @Test
public void testCreateUpdateDeleteuser() {
    users user = new users();
    user.setUserNname("Test user");
    user.setPassword("pass");

    boolean created = userService.createUser(user);
    assertTrue(created);
    assertTrue(user.getId() > 0); 

    user.setUserNname("Updated user");
    user.setPassword("passup");

    boolean updated = userService.updateUser(user);
    assertTrue(updated);

    users updateduser = userService.getUserById(user.getId()); 
    assertEquals("Updated user", updateduser.getUserNname());
    assertEquals("passup", updateduser.getPassword());

    boolean deleted = userService.deleteuser(user.getId());
    assertTrue(deleted);

    assertNull(userService.getUserById(user.getId())); 
}

    @Test
public void testUserValidate_ValidCredentials_ReturnsTrue() {
    users testUser = new users();
    testUser.setUserNname("dsw");
    testUser.setPassword("123");

    boolean result = userService.userValidate(testUser);
    assertTrue(result);
}

@Test
public void testUserValidate_InvalidCredentials_ReturnsFalse() {
    users testUser = new users();
    testUser.setUserNname("nonexistent");
    testUser.setPassword("wrongpass");

    boolean result = userService.userValidate(testUser);
    assertFalse(result);
}
@Test
public void testAvailableuser() {
    users testUser = new users();
    testUser.setUserNname("testuser");
   

    boolean result = userService.validUserByname(testUser);
    assertFalse(result);
}
}
