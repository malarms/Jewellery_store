package dao;

import model.Sale;
import exceptions.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import util.DBUtil;

public class SaleDAOImpl implements SaleDAO {

    public void addSale(Sale sale) throws SaleNotFoundException {
        String sql = "INSERT INTO sales (customer_id, jewellery_id, quantity, total_price, sale_date, payment_method) " +
                   "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, sale.getCustomerId());
            stmt.setInt(2, sale.getJewelleryId());
            stmt.setInt(3, sale.getQuantity());
            stmt.setDouble(4, sale.getTotalPrice());
            stmt.setTimestamp(5, Timestamp.valueOf(sale.getSaleDate()));
            stmt.setString(6, sale.getPaymentMethod());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating sale failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    sale.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new SaleNotFoundException("Failed to record sale: " + e.getMessage());
        }
    }

    @Override
    public Sale getSaleById(int id) throws SaleNotFoundException {
        String sql = "SELECT * FROM sales WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapSale(rs);
            }
            throw new SaleNotFoundException("Sale with ID " + id + " not found");
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    public List<Sale> getAllSales() {
        String sql = "SELECT * FROM sales";
        List<Sale> sales = new ArrayList<>();
        
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                sales.add(mapSale(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
        return sales;
    }

    @Override
    public List<Sale> getSalesByCustomer(int customerId) {
        String sql = "SELECT * FROM sales WHERE customer_id = ?";
        List<Sale> sales = new ArrayList<>();
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                sales.add(mapSale(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
        return sales;
    }

    public List<Sale> getSalesByDateRange(Date startDate, Date endDate) {
        String sql = "SELECT * FROM sales WHERE sale_date BETWEEN ? AND ?";
        List<Sale> sales = new ArrayList<>();
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setTimestamp(1, new Timestamp(startDate.getTime()));
            stmt.setTimestamp(2, new Timestamp(endDate.getTime()));
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sales.add(mapSale(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving sales by date range", e);
        }
        return sales;
    }

    private Sale mapSale(ResultSet rs) throws SQLException {
        Sale sale = new Sale();
        sale.setId(rs.getInt("id"));
        sale.setCustomerId(rs.getInt("customer_id"));
        sale.setJewelleryId(rs.getInt("jewellery_id"));
        sale.setQuantity(rs.getInt("quantity"));
        sale.setTotalPrice(rs.getDouble("total_price"));
        sale.setSaleDate(rs.getTimestamp("sale_date").toLocalDateTime());
        sale.setPaymentMethod(rs.getString("payment_method"));
        return sale;
    }
}