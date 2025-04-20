package service;

import dao.JewelleryDAO;
import model.Jewellery;
import exceptions.*;
import java.util.List;

public class JewelleryService {
    private final JewelleryDAO jewelleryDAO;

    public JewelleryService(JewelleryDAO jewelleryDAO) {
        this.jewelleryDAO = jewelleryDAO;
    }

    public void addJewellery(Jewellery jewellery) throws JewelleryAlreadyExistsException {
        jewelleryDAO.addJewellery(jewellery);
    }

    public Jewellery getJewelleryById(int id) throws JewelleryNotFoundException {
        return jewelleryDAO.getJewelleryById(id);
    }

    public List<Jewellery> getAllJewellery() {
        return jewelleryDAO.getAllJewellery();
    }

    public void updateStock(int jewelleryId, int quantityChange) 
            throws JewelleryNotFoundException, InsufficientStockException {
        Jewellery jewellery = jewelleryDAO.getJewelleryById(jewelleryId);
        int newStock = jewellery.getStockQuantity() + quantityChange;
        
        if (newStock < 0) {
            throw new InsufficientStockException(jewellery.getStockQuantity(), quantityChange);
        }
        jewellery.setStockQuantity(newStock);
        jewelleryDAO.updateJewellery(jewellery);
    }
}