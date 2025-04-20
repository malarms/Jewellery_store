package service;

import dao.SaleDAO;
import dao.JewelleryDAO;
import model.Sale;
import model.Jewellery;
import exceptions.JewelleryNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {
    private final SaleDAO saleDAO;
    private final JewelleryDAO jewelleryDAO;

    public ReportService(SaleDAO saleDAO, JewelleryDAO jewelleryDAO) {
        this.saleDAO = saleDAO;
        this.jewelleryDAO = jewelleryDAO;
    }

    public String generateDailySalesReport(LocalDate date) {
        try {
            List<Sale> sales = saleDAO.getSalesByDateRange(
                Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant())
            );
            
            double total = sales.stream()
                .mapToDouble(Sale::getTotalPrice)
                .sum();
            
            return String.format(
                "Daily Sales Report for %s\n" +
                "Total Sales: %d\n" +
                "Total Revenue: $%.2f\n" +
                "Top Selling Items:\n%s",
                date,
                sales.size(),
                total,
                getTopSellingItems(sales, 5)
            );
        } catch (Exception e) {
            return "Error generating report: " + e.getMessage();
        }
    }

    public String generateInventoryReport() {
        try {
            List<Jewellery> lowStock = jewelleryDAO.getLowStockJewellery(5);
            List<Jewellery> outOfStock = jewelleryDAO.getLowStockJewellery(1);
            
            return String.format(
                "Inventory Status Report\n" +
                "Low Stock Items (<5): %d\n" +
                "Out of Stock Items: %d",
                lowStock.size(),
                outOfStock.size()
            );
        } catch (Exception e) {
            return "Error generating inventory report: " + e.getMessage();
        }
    }

    private String getTopSellingItems(List<Sale> sales, int limit) {
        try {
            Map<Jewellery, Integer> itemCounts = sales.stream()
                .collect(Collectors.groupingBy(
                    s -> {
                        try {
                            return jewelleryDAO.getJewelleryById(s.getJewelleryId());
                        } catch (JewelleryNotFoundException e) {
                            throw new RuntimeException("Jewellery not found for sale ID: " + s.getId(), e);
                        }
                    },
                    Collectors.summingInt(Sale::getQuantity)
                ));
            
            return itemCounts.entrySet().stream()
                .sorted(Map.Entry.<Jewellery, Integer>comparingByValue().reversed())
                .limit(limit)
                .map(e -> String.format("- %s: %d sold", e.getKey().getName(), e.getValue()))
                .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            return "Could not determine top selling items: " + e.getMessage();
        }
    }
}