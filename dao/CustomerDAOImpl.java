package dao;

import model.Customer;
import exceptions.*;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public void addCustomer(Customer customer) throws CustomerAlreadyExistsException {
        String sql = "INSERT INTO customers (name, email, phone, address, registration_date) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setCustomerParameters(stmt, customer);
            stmt.setDate(5, Date.valueOf(customer.getRegistrationDate()));
            
            stmt.executeUpdate();
            setGeneratedId(stmt, customer);
        } catch (SQLException e) {
            handleSQLException(e, customer.getEmail());
        }
    }

    @Override
    public Customer getCustomerById(int id) throws CustomerNotFoundException {
        return querySingleCustomer("SELECT * FROM customers WHERE id = ?", stmt -> stmt.setInt(1, id));
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customers";
        List<Customer> customers = new ArrayList<>();
        
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                customers.add(mapCustomer(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
        return customers;
    }

    @Override
    public void updateCustomer(Customer customer) throws CustomerNotFoundException {
        // First check if customer exists
        if (!customerExists(customer.getId())) {
            throw new CustomerNotFoundException("Customer with ID " + customer.getId() + " not found");
        }
        
        String sql = "UPDATE customers SET name = ?, email = ?, phone = ?, address = ? WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setCustomerParameters(stmt, customer);
            stmt.setInt(5, customer.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Handle duplicate email case
            if (e.getErrorCode() == 1062) { // MySQL duplicate entry code
                throw new RuntimeException("Email already exists: " + customer.getEmail(), e);
            }
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public void deleteCustomer(int id) throws CustomerNotFoundException {
        if (!customerExists(id)) {
            throw new CustomerNotFoundException(id);
        }
        executeUpdate("DELETE FROM customers WHERE id = ?", stmt -> stmt.setInt(1, id));
    }

    @Override
    public Customer getCustomerByEmail(String email) throws CustomerNotFoundException {
        return querySingleCustomer("SELECT * FROM customers WHERE email = ?", 
            stmt -> stmt.setString(1, email));
    }

    // ===== Helper Methods =====
    private boolean customerExists(int id) {
        String sql = "SELECT 1 FROM customers WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if record exists
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking customer existence", e);
        }
    }

    private Customer querySingleCustomer(String sql, ParameterSetter setter) 
        throws CustomerNotFoundException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setter.setParameters(stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapCustomer(rs);
                }
                throw new CustomerNotFoundException("Customer not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error while querying customer", e);
        }
    }

    private void executeUpdate(String sql, ParameterSetter setter) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setter.setParameters(stmt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database update failed", e);
        }
    }

    private Customer mapCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        customer.setAddress(rs.getString("address"));
        customer.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
        return customer;
    }

    private void setCustomerParameters(PreparedStatement stmt, Customer customer) 
            throws SQLException {
        stmt.setString(1, customer.getName());
        stmt.setString(2, customer.getEmail());
        stmt.setString(3, customer.getPhone());
        stmt.setString(4, customer.getAddress());
    }

    private void setGeneratedId(PreparedStatement stmt, Customer customer) 
            throws SQLException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                customer.setId(generatedKeys.getInt(1));
            }
        }
    }

    private void handleSQLException(SQLException e, String email) 
            throws CustomerAlreadyExistsException {
        if (e.getErrorCode() == 1062) { // MySQL duplicate entry
            throw new CustomerAlreadyExistsException(email);
        }
        throw new RuntimeException("Database error", e);
    }

    @FunctionalInterface
    private interface ParameterSetter {
        void setParameters(PreparedStatement stmt) throws SQLException;
    }
}