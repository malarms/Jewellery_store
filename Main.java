import dao.*;
import model.*;
import service.*;
import exceptions.*;

import java.util.Scanner;

public class Main {
    private static JewelleryService jewelleryService;
    private static CustomerService customerService;
    private static SaleService saleService;
    private static UserService userService;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            initializeServices();
            showMainMenu();
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void initializeServices() {
        JewelleryDAO jewelleryDAO = new JewelleryDAOImpl();
        CustomerDAO customerDAO = new CustomerDAOImpl();
        SaleDAO saleDAO = new SaleDAOImpl();
        UserDAO userDAO = new UserDAOImpl();

        jewelleryService = new JewelleryService(jewelleryDAO);
        customerService = new CustomerService(customerDAO);
        saleService = new SaleService(saleDAO, jewelleryDAO, customerDAO);
        userService = new UserService(userDAO);
    }

    private static void showMainMenu() throws Exception {
        while (true) {
            System.out.println("\n========================== Jewellery Store Management ==========================");
            System.out.println("1. Manage Jewellery");
            System.out.println("2. Manage Customers");
            System.out.println("3. Process Sale");
            System.out.println("4. View Reports");
            System.out.println("5. Exit");
            System.out.print("Select option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> showJewelleryMenu();
                case 2 -> showCustomerMenu();
                case 3 -> processNewSale();
                case 4 -> generateReports();
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    // ===================== JEWELLERY MENU =====================
    private static void showJewelleryMenu() throws Exception {
        System.out.println("\n--- Jewellery Management ---");
        System.out.println("1. Add New Jewellery");
        System.out.println("2. View All Jewellery");
        System.out.println("3. Back to Main Menu");
        System.out.print("Select option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> addNewJewellery();
            case 2 -> viewAllJewellery();
            case 3 -> {
            }
            default -> System.out.println("Invalid option!");
        }
    }

    private static void addNewJewellery() throws Exception {
        System.out.println("\nEnter jewellery details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Type: ");
        String type = scanner.nextLine();
        System.out.print("Material: ");
        String material = scanner.nextLine();
        System.out.print("Weight (grams): ");
        double weight = scanner.nextDouble();
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        System.out.print("Stock Quantity: ");
        int stock = scanner.nextInt();
        scanner.nextLine();

        Jewellery jewellery = new Jewellery(name, type, material, weight, price, stock);
        jewelleryService.addJewellery(jewellery);
        System.out.println("Jewellery added successfully!");
    }

    private static void viewAllJewellery() {
        System.out.println("\n=== Available Jewellery ===");
        jewelleryService.getAllJewellery().forEach(j ->
            System.out.printf("ID: %d | %s - ₹%.2f | %d in stock\n",
                j.getId(), j.getName(), j.getPrice(), j.getStockQuantity())
        );
    }

    // ===================== CUSTOMER MENU =====================
    private static void showCustomerMenu() throws Exception {
        while (true) {
            System.out.println("\n--- Customer Management ---");
            System.out.println("1. Add New Customer");
            System.out.println("2. View All Customers");
			System.out.println("3. Delete a Customer");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addNewCustomer();
                case 2 -> viewAllCustomers();
				case 3 -> deleteCustomer(); 
                case 4 -> { return; }
                default -> System.out.println("Invalid option!");
            }
        }
    }
	private static void deleteCustomer() throws Exception {
    System.out.print("Enter Customer ID to delete: ");
    int id = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    try {
        customerService.deleteCustomer(id); // Calls the delete method in the service
        System.out.println("Customer deleted successfully!");
    } catch (CustomerNotFoundException e) {
        System.out.println("Error: " + e.getMessage());
    }
}

    private static void addNewCustomer() throws Exception {
        System.out.print("\nEnter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        Customer customer = new Customer(name, email, phone, address);
        customerService.addCustomer(customer);
        System.out.println("Customer added successfully!");
    }

    private static void viewAllCustomers() throws Exception {
        System.out.println("\n=== Customer List ===");
        var customers = customerService.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        for (Customer customer : customers) {
            System.out.printf("ID: %d | Name: %s | Email: %s | Phone: %s | Address: %s\n",
                customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone(), customer.getAddress());
        }
    }

    // ===================== SALES =====================
    private static void processNewSale() throws Exception {
        System.out.println("\n=== Process New Sale ===");
        System.out.print("Customer ID: ");
        int customerId = scanner.nextInt();
        System.out.print("Jewellery ID: ");
        int jewelleryId = scanner.nextInt();
        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        Sale sale = new Sale(customerId, jewelleryId, quantity);
        saleService.processSale(sale);
        System.out.println("Sale processed successfully!");
    }

    // ===================== REPORTS =====================
    private static void generateReports() {
        System.out.println("\n=== Reports ===");
        // You can implement full report details here later
        System.out.println("Feature coming soon...");
    }
}
