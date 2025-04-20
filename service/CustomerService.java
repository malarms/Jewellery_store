package service;

import dao.CustomerDAO;
import model.Customer;
import exceptions.*;
import java.util.List;

public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public void addCustomer(Customer customer) throws CustomerAlreadyExistsException {
        customerDAO.addCustomer(customer);
    }

    public Customer getCustomerById(int id) throws CustomerNotFoundException {
        return customerDAO.getCustomerById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }
}