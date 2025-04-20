package dao;

import model.Customer;
import java.util.List;
import exceptions.*;

public interface CustomerDAO {
    void addCustomer(Customer customer) throws CustomerAlreadyExistsException;
    Customer getCustomerById(int id) throws CustomerNotFoundException;
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer) throws CustomerNotFoundException;
    void deleteCustomer(int id) throws CustomerNotFoundException;
    Customer getCustomerByEmail(String email) throws CustomerNotFoundException;
}
