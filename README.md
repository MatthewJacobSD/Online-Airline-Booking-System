# ✈️ Airline Online Booking System
![Java Version](https://img.shields.io/badge/Java-17+-orange)
![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)

A Java-based command-line application for managing airline bookings, customers, flights, and routes, with interactive console input and CSV file storage.

---

## 🚀 Features

* **Customer Management** – Add, view, and validate customer info
* **Flight Booking System** – Create bookings with date validation
* **Flight Operations** – Add flight details (number, airports, schedule)
* **Route Management** – Define flight routes with code validation
* **CSV Handling** – Import/export records to CSV files
* **Input Validation** – Strong validation for all fields
* **Interactive Console UI** – Easy-to-use terminal interface

---

## 📁 Project Structure

```
src/
└── io.github.MatthewJacobSD/
    ├── AirlineOnlineBookingSystem.java  # Main entry point
    ├── models/                          # Entity classes
    │   ├── Booking.java
    │   ├── Customer.java
    │   ├── Flight.java
    │   └── Route.java
    ├── services/                        # Business logic
    │   ├── BaseService.java
    │   ├── SBooking.java
    │   ├── SCustomer.java
    │   ├── SFlight.java
    │   └── SRoute.java
    └── utils/                           # Utility helpers
        ├── ConsoleUI.java
        ├── CSVHandler.java
        ├── FileHandler.java
        ├── ServiceRouter.java
        └── Validator.java
```

---

## 🧩 System Components

### 🔸 Models

* `Booking` – Stores booking ID and date
* `Customer` – Contains name, email, phone, address
* `Flight` – Holds number, departure/arrival, time
* `Route` – Defines flight routes (e.g., JFK-LAX)

### 🔹 Services

* `BaseService` – Abstract class for CRUD logic
* `SBooking`, `SCustomer`, `SFlight`, `SRoute` – Each handles logic & validation for its model

### 🛠 Utilities

* `ConsoleUI` – User interface and terminal prompts
* `CSVHandler` – Manages CSV read/write
* `FileHandler` – File I/O helpers
* `ServiceRouter` – Routes user input to correct service
* `Validator` – Validation for all field types

---

## ✅ Data Validation Rules

All fields go through strict validation:

| Field           | Rules                                              |
|-----------------|----------------------------------------------------|
| UUID            | Auto-generated, must be valid                      |
| Dates           | Must be today or within 1 year (`yyyy-MM-dd`)      |
| Flight Number   | 2–6 alphanumeric characters (e.g., `AA123`)        |
| Airport Codes   | 2-6 uppercase letters (e.g., `JFK`)                |
| Route Name      | Format `XXX-YYY` (e.g., `JFK-LAX`)                 |
| Email           | Must follow email format (e.g., `user@domain.com`) |
| Phone Number    | 7–15 digits only                                   |
| Required Fields | All required fields are validated before saving    |

---

## 🛠 Getting Started

### 🔧 Prerequisites

* Java JDK 17+
* Maven (recommended build tool)
* IDE or terminal (IntelliJ, Eclipse, etc.)
* MariaDB (for database import)

### 📦 Installation

```bash
git clone https://github.com/MatthewJacobSD/airline-booking-system.git
cd airline-booking-system
```

---

## ▶️ Running the Application

### Option 1: Manual Compilation

```bash
javac -d target/classes src/main/java/io/github/MatthewJacobSD/*.java src/main/java/io/github/MatthewJacobSD/**/*.java
java -cp target/classes io.github.MatthewJacobSD.AirlineOnlineBookingSystem
```

### Option 2: Using Maven

```bash
mvn clean compile exec:java
```

---

## 💻 Usage Guide

1. **Launch the App**
   * You'll see a main menu with options to manage Customers, Bookings, Flights, Routes, or Exit.

2. **Manage Data**

   * Read existing CSV files
   * Add new records interactively
   * Save changes (append or overwrite)

3. **Interactive Input**
   * The console walks you through entering each field, with real-time validation.

### ✈️ Example: Add a Flight

1. Select **"3. Manage Flight Data"**
2. Choose **"2. Write flight data"**
3. Enter:

   * Flight Number: `AA123`
   * Departure: `JFK`
   * Arrival: `LAX`
   * Departure Time: 2025-05-04 14:30
   * Arrival Time: 2025-05-04 17:45
   * Route ID: (valid UUID from routes.csv)
4. Save — it's added to `flights.csv`

---

## 📂 Default CSV Files

| File            | Description       |
|-----------------|-------------------|
| `customers.csv` | Customer records  |
| `bookings.csv`  | Flight bookings   |
| `flights.csv`   | Flight info       |
| `routes.csv`    | Route definitions |

---

## 🔁 Example Workflow

1. Add customers from the Customer Data menu
2. Define flight routes between airports
3. Create flights using those routes
4. Book customers onto flights

---

## 🧠 Design Patterns

* **Service Layer** – Business logic separate from models
* **Abstract Base Class** – Shared CRUD logic (`BaseService`)
* **Dependency Injection** – Pass dependencies cleanly
* **Builder Pattern** – Used during entity creation with validation

---

## 📄 Use Case Diagram
```text
Actors:
- Customer
- Admin

Use Cases:
- Add Customer (Admin)
- Make Booking (Customer)
- View Flights (Customer, Admin)
- Add Flight (Admin)
- Add Route (Admin)

Relationships:
- Customer -> Make Booking
- Customer -> View Flights
- Admin -> Add Customer, Add Flight, Add Route, View Flights
Actors:
- Customer
- Admin

Use Cases:
- Add Customer (Admin)
- Make Booking (Customer)
- View Flights (Customer, Admin)
- Add Flight (Admin)
- Add Route (Admin)

Relationships:
- Customer -> Make Booking
- Customer -> View Flights
- Admin -> Add Customer, Add Flight, Add Route, View Flights
```
---

## 📊 Class Diagram
```text
Customer
- customerId: String
- firstName: String
- lastName: String
- email: String
- phone: String
- address: String

Booking
- bookingId: String
- bookingDate: LocalDate
- customerId: String
- flightId: String
+ Association: Booking -> Customer (customerId)
+ Association: Booking -> Flight (flightId)

Flight
- flightId: String
- flightNumber: String
- departureAirport: String
- arrivalAirport: String
- departureDateTime: LocalDateTime
- arrivalDateTime: LocalDateTime
- routeId: String
+ Association: Flight -> Route (routeId)

Route
- routeId: String
- routeName: String
```
---

## 🗄️ MariaDB Import Instructions(MariaDB example)
1. Create a new database in MariaDB. (set the data source to mariaDB, or any other database you want to use)
```mariadb
2. Run the following SQL commands to create tables:

```mariadb
CREATE TABLE customers (
    id VARCHAR(36) PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    email VARCHAR(255),
    phoneNo VARCHAR(15),
    address TEXT
);

CREATE TABLE routes (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(7)
);

CREATE TABLE flights (
    id VARCHAR(36) PRIMARY KEY,
    flightNo VARCHAR(6),
    depAirport CHAR(3),
    arrAirport CHAR(3),
    depTime DATETIME,
    arrTime DATETIME,
    routeId VARCHAR(36),
    FOREIGN KEY (routeId) REFERENCES routes(id)
);

CREATE TABLE bookings (
    id VARCHAR(36) PRIMARY KEY,
    date DATE,
    customerId VARCHAR(36),
    flightId VARCHAR(36),
    FOREIGN KEY (customerId) REFERENCES customers(id),
    FOREIGN KEY (flightId) REFERENCES flights(id)
);
```
3. Import CSV files into the respective tables using the following command:

```mariadb
LOAD DATA INFILE '/path/to/customers.csv'
INTO TABLE customers
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

LOAD DATA INFILE '/path/to/routes.csv'
INTO TABLE routes
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

LOAD DATA INFILE '/path/to/flights.csv'
INTO TABLE flights
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

LOAD DATA INFILE '/path/to/bookings.csv'
INTO TABLE bookings
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;
```
4. Adjust the file paths and ensure MariaDB has file permissions.
5. Run the commands in your MariaDB client.
6. Verify the data is imported correctly by running `SELECT * FROM table_name;`.
7. You can now use the application to manage bookings and flights.
---

## 📜 License

This project is licensed under the [MIT License](LICENSE).

---

## 🙌 Acknowledgments

* Java Time API – date/time operations
* Java Reflection – dynamic CSV parsing
* UUID – unique record IDs

---

## 🤝 Contributing

1. Fork the repo
2. Create a new branch: `git checkout -b feature-name`
3. Commit changes: `git commit -m "Added X feature"`
4. Push branch: `git push origin feature-name`
5. Open a pull request 🎉

---