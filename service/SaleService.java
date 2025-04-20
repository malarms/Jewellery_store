package service;

import dao.SaleDAO;
import dao.JewelleryDAO;
import dao.CustomerDAO;
import model.Sale;
import model.Jewellery;
import exceptions.*;
import java.time.LocalDateTime;

public class SaleService {
    private final SaleDAO saleDAO;
    private final JewelleryDAO jewelleryDAO;
    private final CustomerDAO customerDAO;

    public SaleService(SaleDAO saleDAO, JewelleryDAO jewelleryDAO, CustomerDAO customerDAO) {
        this.saleDAO = saleDAO;
        this.jewelleryDAO = jewelleryDAO;
        this.customerDAO = customerDAO;
    }

    public void processSale(Sale sale) 
            throws JewelleryNotFoundException, 
                   InsufficientStockException, 
                   CustomerNotFoundException,
                   SaleNotFoundException {
        
        // Validate customer exists
        customerDAO.getCustomerById(sale.getCustomerId());
        
        // Check stock
        Jewellery jewellery = jewelleryDAO.getJewelleryById(sale.getJewelleryId());
        if (jewellery.getStockQuantity() < sale.getQuantity()) {
            throw new InsufficientStockException(
                jewellery.getStockQuantity(), 
                sale.getQuantity()
            );
        }
        
        // Calculate total and set timestamp
        sale.setTotalPrice(jewellery.getPrice() * sale.getQuantity());
        sale.setSaleDate(LocalDateTime.now());
        
        // Record sale
        saleDAO.addSale(sale);
        
        // Update stock
        jewellery.setStockQuantity(jewellery.getStockQuantity() - sale.getQuantity());
        jewelleryDAO.updateJewellery(jewellery);
    }
}