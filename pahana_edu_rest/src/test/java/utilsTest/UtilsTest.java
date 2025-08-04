/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilsTest;
import Utils.Customer;
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
}
