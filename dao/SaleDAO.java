package dao;

import model.Sale;
import java.util.List; 
import java.util.Date; 
import exceptions.*;

public interface SaleDAO {
    Sale getSaleById(int id) throws SaleNotFoundException;
	void addSale(Sale sale) throws SaleNotFoundException;
    List<Sale> getAllSales();
    List<Sale> getSalesByCustomer(int customerId);
    List<Sale> getSalesByDateRange(Date startDate, Date endDate);
}

