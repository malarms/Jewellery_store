package dao;

import model.Jewellery;
import exceptions.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DBUtil;

public class JewelleryDAOImpl implements JewelleryDAO {

    public void addJewellery(Jewellery jewellery) throws JewelleryAlreadyExistsException {
        String sql = "INSERT INTO jewellery (name, type, material, weight, price, stock_quantity, date_added) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setJewelleryParameters(stmt, jewellery);
            stmt.setDate(7, Date.valueOf(jewellery.getDateAdded()));
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating jewellery failed, no rows affected.");
            }
            
            setGeneratedId(stmt, jewellery);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new JewelleryAlreadyExistsException(jewellery.getName());
            }
            throw new RuntimeException("Database error", e);
        }
    }

    public Jewellery getJewelleryById(int id) throws JewelleryNotFoundException {
        return querySingleJewellery("SELECT * FROM jewellery WHERE id = ?", 
            stmt -> stmt.setInt(1, id));
    }

    @Override
    public List<Jewellery> getAllJewellery() {
        List<Jewellery> jewelleryList = new ArrayList<>();
        String sql = "SELECT * FROM jewellery";
        
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                jewelleryList.add(mapJewellery(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
        return jewelleryList;
    }

    public void updateJewellery(Jewellery jewellery) throws JewelleryNotFoundException {
        if (!jewelleryExists(jewellery.getId())) {
            throw new JewelleryNotFoundException(jewellery.getId());
        }
        
        String sql = "UPDATE jewellery SET name = ?, type = ?, material = ?, weight = ?, " +
                   "price = ?, stock_quantity = ? WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setJewelleryParameters(stmt, jewellery);
            stmt.setInt(7, jewellery.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    public void deleteJewellery(int id) throws JewelleryNotFoundException {
        if (!jewelleryExists(id)) {
            throw new JewelleryNotFoundException(id);
        }
        
        String sql = "DELETE FROM jewellery WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    public List<Jewellery> getJewelleryByType(String type) {
        return queryJewelleryList("SELECT * FROM jewellery WHERE type = ?",
            stmt -> stmt.setString(1, type));
    }

    public List<Jewellery> getLowStockJewellery(int threshold) {
        return queryJewelleryList("SELECT * FROM jewellery WHERE stock_quantity <= ?",
            stmt -> stmt.setInt(1, threshold));
    }

    // ===== Helper Methods =====
    private boolean jewelleryExists(int id) {
        String sql = "SELECT 1 FROM jewellery WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    private Jewellery querySingleJewellery(String sql, ParameterSetter setter) 
        throws JewelleryNotFoundException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setter.setParameters(stmt);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapJewellery(rs);
            }
            throw new JewelleryNotFoundException("Jewellery not found");
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    private List<Jewellery> queryJewelleryList(String sql, ParameterSetter setter) {
        List<Jewellery> jewelleryList = new ArrayList<>();
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setter.setParameters(stmt);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                jewelleryList.add(mapJewellery(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
        return jewelleryList;
    }

    private Jewellery mapJewellery(ResultSet rs) throws SQLException {
        Jewellery jewellery = new Jewellery();
        jewellery.setId(rs.getInt("id"));
        jewellery.setName(rs.getString("name"));
        jewellery.setType(rs.getString("type"));
        jewellery.setMaterial(rs.getString("material"));
        jewellery.setWeight(rs.getDouble("weight"));
        jewellery.setPrice(rs.getDouble("price"));
        jewellery.setStockQuantity(rs.getInt("stock_quantity"));
        jewellery.setDateAdded(rs.getDate("date_added").toLocalDate());
        return jewellery;
    }

    private void setJewelleryParameters(PreparedStatement stmt, Jewellery jewellery) 
        throws SQLException {
        stmt.setString(1, jewellery.getName());
        stmt.setString(2, jewellery.getType());
        stmt.setString(3, jewellery.getMaterial());
        stmt.setDouble(4, jewellery.getWeight());
        stmt.setDouble(5, jewellery.getPrice());
        stmt.setInt(6, jewellery.getStockQuantity());
    }

    private void setGeneratedId(PreparedStatement stmt, Jewellery jewellery) 
        throws SQLException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                jewellery.setId(generatedKeys.getInt(1));
            }
        }
    }

    private interface ParameterSetter {
        void setParameters(PreparedStatement stmt) throws SQLException;
    }
}