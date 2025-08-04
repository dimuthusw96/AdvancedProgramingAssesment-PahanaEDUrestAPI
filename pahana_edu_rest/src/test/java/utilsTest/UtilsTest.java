/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilsTest;
import Utils.Customer;
import Utils.Item;
import Utils.Utils;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


/**
 *
 * @author Dimuthu
 */
public class UtilsTest {
    public Utils utils;

    @BeforeEach
public void setUp() {
    utils = new Utils();
    System.out.println("Utils instance created: " + utils);
}
     // Customer tests
    @Test
    public void testGetCustomers() {
        List<Customer> customers = utils.getCustomers();
        assertNotNull(customers);
        assertTrue(customers.size() >= 0);
    }

    @Test
    public void testGetCustomerById() {
        Customer customer = utils.getCustomerById(1);
        assertNotNull(customer);
        assertEquals(1, customer.getId());
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

        boolean created =utils.createCustomer(customer);
        assertTrue(created);
        assertTrue(customer.getId() > 0);

        customer.setName("Updated Customer");
        customer.setUnit_consumed(10);
        boolean updated = utils.updateCustomer(customer);
        assertTrue(updated);

        Customer updatedCustomer = utils.getCustomerById(customer.getId());
        assertEquals("Updated Customer", updatedCustomer.getName());
        assertEquals(10, updatedCustomer.getUnit_consumed());

        boolean deleted = utils.deleteCustomer(customer.getId());
        assertTrue(deleted);

        assertNull(utils.getCustomerById(customer.getId()));
    }
    
    //itemtest
    @Test
    public void testGetItems() {
        List<Item> items = utils.getItems();
        assertNotNull(items);
        assertTrue(items.size() >= 0);
    }

    @Test
    public void testGetItemById() {
        Item item = utils.getItemById(3);
        assertNotNull(item);
        assertEquals(3, item.getId());
        assertNotNull(item.getName());
    }

    @Test
    public void testCreateUpdateDeleteItem() {
        Item item = new Item();
        item.setName("Test Item");
        item.setPrice(9.99);
        item.setQuantity(100);

        boolean created = utils.createItem(item);
        assertTrue(created);
        assertTrue(item.getId() > 0);

        item.setName("Updated Item");
        item.setPrice(19.99);
        item.setQuantity(200);
        boolean updated = utils.updateItem(item);
        assertTrue(updated);

        Item updatedItem = utils.getItemById(item.getId());
        assertEquals("Updated Item", updatedItem.getName());
        assertEquals(19.99, updatedItem.getPrice());
        assertEquals(200, updatedItem.getQuantity());

        boolean deleted = utils.deleteItem(item.getId());
        assertTrue(deleted);

        assertNull(utils.getItemById(item.getId()));
    }
}
