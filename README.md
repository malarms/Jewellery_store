# Jewellery_store
Jewellery Store Management System
A comprehensive Java-based desktop application for managing customers, jewellery inventory, user authentication, and sales transactions using JDBC with MySQL. The system is modular, scalable, and follows industry-standard practices like DAO patterns, custom exception handling, and service abstractions.

 Table of Contents
Features

Technologies Used

Project Structure

Setup Instructions

Usage

Contributing

License



 Features
👤 User Management: Register, authenticate, and authorize users with role-based access.

🧾 Customer Management: Add, retrieve, and list customer records.

💍 Jewellery Inventory: Track stock, update prices, and manage stock quantities.

💸 Sales Processing: Validate inventory, update stock, and record transactions with timestamps.


📊 Reports:

Daily Sales Reports

Inventory Status Reports

Top Selling Jewellery

⚠️ Robust Exception Handling: Custom exceptions for user, database, stock, and validation issues.



🛠 Technologies Used
Java 8+

MySQL 8.x

JDBC (Java Database Connectivity)

IDE: IntelliJ IDEA / Eclipse



🗂 Project Structure

src/
├── dao/                  # Data Access Objects for DB interactions
├── model/                # Plain Old Java Objects (POJOs)
├── service/              # Business logic services
├── exceptions/           # Custom exception classes
├── util/                 # Database utility (connection handler)
└── main/                 # Application entry point (Main.java)


Setup Instructions
Clone the Repository:

 git clone https://github.com/yourusername/jewellery-store-system.git
cd jewellery-store-system


Set Up MySQL Database:


Import the provided SQL schema into your MySQL server.


Update DBUtil.java with your database credentials.


Compile the Code:


Using your IDE or javac via command line.


Run the Application:


Execute Main.java to interact with the system.



Usage
Register new users (admin, cashier, etc.).


Add jewellery inventory and customers.


Process sales by linking customers and jewellery items.


Generate daily sales and inventory reports.


Validate users with authentication on each login.

Team Contributions
Team Member 1: Database Architecture & DAO Implementation
Responsibilities:
Designed normalized relational schema for users, customers, jewellery, and sales tables.


Implemented complete DAO interfaces and JDBC implementations for:


UserDAO – registration, authentication, and user role management.


CustomerDAO – customer CRUD operations.


JewelleryDAO – inventory management (add, update, delete, view).


SaleDAO – transaction logging and sales data retrieval.


Wrote reusable query and result-handling logic to abstract database interactions.



Team Member 2: Core Models & Exception Handling
Responsibilities:
Created all model classes (User, Customer, Jewellery, Sale) using clean POJO structure.


Implemented custom exception classes for better error tracking:


UserNotFoundException


CustomerNotFoundException


JewelleryNotFoundException


InsufficientStockException


InvalidInputException


Ensured robust input validation and error messages across all services.



Team Member 3: Business Logic & Service Layer
Responsibilities:
Developed complete service classes for:


UserService – handles login, role management.


CustomerService – validates and manages customer data.


JewelleryService – updates stock, prices, and queries inventory.


SaleService – verifies stock, calculates total, records transactions.


Built utility methods to encapsulate business rules (e.g., stock availability, total pricing).


Integrated exception propagation and handled inter-service communication.



Team Member 4: Application Integration & Testing
Responsibilities:
Designed and implemented the Main.java entry point for testing core features.


Wrote demonstration routines for:


Creating users and logging in.


Registering customers and jewellery items.


Performing sales and updating inventory.


Conducted end-to-end testing to validate DAO–Service–DB integration.


Added inline comments and handled edge-case testing.
