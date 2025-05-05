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


## 📝 Example output
```text
C:\Users\emerd\.jdks\openjdk-24\bin\java.exe "-javaagent:C:\Users\emerd\AppData\Local\Programs\IntelliJ IDEA Ultimate\lib\idea_rt.jar=53417" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath "C:\Users\emerd\IdeaProjects\college\Distinction Projects\aobs\target\classes;C:\Users\emerd\.m2\repository\com\google\code\gson\gson\2.10.1\gson-2.10.1.jar" io.github.MatthewJacobSD.AirlineOnlineBookingSystem

✈️ Airline Online Booking System
━━━━━━━━━━━━━━━━━━━━━━━
1. Manage Customer Data
2. Manage Booking Data
3. Manage Flight Data
4. Manage Route Data
5. Exit
🔄 Choose an option: 1

Customer Data Menu
――――――――――――――――――
1. Read customer data
2. Write customer data
3. Return to main menu
• 🔄 Choose an option: 
1

Read Customer Data
――――――――――――――――――
• 📂 Enter file path (e.g., customers.csv): 
customers.csv
• ⏳ Loading customer data...

Reading File
――――――――――――
• ⏳ Attempting to read: customers.csv
✓ ✅ Successfully read 2 lines

Customer Data
―――――――――――――
📋 Customer 
{
id: aada4f8f-4d00-40c8-8fbc-e4ae296e1290,
 firstName: MatthewJacob,
 lastName: SD,
 email: matthewjacobsd@xample.com,
 phoneNo: 145729257,
 address: george street st micheal 26
}

Customer Data Menu
――――――――――――――――――
1. Read customer data
2. Write customer data
3. Return to main menu
• 🔄 Choose an option: 
3

✈️ Airline Online Booking System
━━━━━━━━━━━━━━━━━━━━━━━
1. Manage Customer Data
2. Manage Booking Data
3. Manage Flight Data
4. Manage Route Data
5. Exit
🔄 Choose an option: 2

Booking Data Menu
―――――――――――――――――
1. Read booking data
2. Write booking data
3. Return to main menu
• 🔄 Choose an option: 
1

Read Booking Data
―――――――――――――――――
• 📂 Enter file path (e.g., bookings.csv): 
bookings.csv
• ⏳ Loading booking data...

Reading File
――――――――――――
• ⏳ Attempting to read: bookings.csv
✓ ✅ Successfully read 0 lines

Booking Data Menu
―――――――――――――――――
1. Read booking data
2. Write booking data
3. Return to main menu
• 🔄 Choose an option: 
✗ File is empty
✗ Invalid CSV structure for Booking
3

✈️ Airline Online Booking System
━━━━━━━━━━━━━━━━━━━━━━━
1. Manage Customer Data
2. Manage Booking Data
3. Manage Flight Data
4. Manage Route Data
5. Exit
🔄 Choose an option: 3

Flight Data Menu
――――――――――――――――
1. Read flight data
2. Write flight data
3. Return to main menu
• 🔄 Choose an option: 
1

Read Flight Data
――――――――――――――――
• 📂 Enter file path (e.g., flights.csv): 
flights.csv
• ⏳ Loading flight data...

Reading File
――――――――――――
• ⏳ Attempting to read: flights.csv
✓ ✅ Successfully read 2 lines

Flight Data
―――――――――――

Reading File
――――――――――――
• ⏳ Attempting to read: routes.csv
✓ ✅ Successfully read 2 lines

Reading File
――――――――――――
• ⏳ Attempting to read: routes.csv
✓ ✅ Successfully read 2 lines

Reading File
――――――――――――
• ⏳ Attempting to read: routes.csv
✓ ✅ Successfully read 2 lines
📋 Flight 
{
id: a8e1b03e-693a-4d29-827c-6a896da2f9db,
 flightNo: LSX894,
 depAirport: ABN,
 arrAirport: MIL,
 depTime: 2025-05-10 10:00,
 arrTime: 2025-05-11 16:40,
 routeId: 963247f7-1fc9-412f-bc21-ab02d075022e}


Flight Data Menu
――――――――――――――――
1. Read flight data
2. Write flight data
3. Return to main menu
• 🔄 Choose an option: 
3

✈️ Airline Online Booking System
━━━━━━━━━━━━━━━━━━━━━━━
1. Manage Customer Data
2. Manage Booking Data
3. Manage Flight Data
4. Manage Route Data
5. Exit
🔄 Choose an option: 4

Route Data Menu
―――――――――――――――
1. Read route data
2. Write route data
3. Return to main menu
• 🔄 Choose an option: 
1

Read Route Data
―――――――――――――――
• 📂 Enter file path (e.g., routes.csv): 
routes.csv
• ⏳ Loading route data...

Reading File
――――――――――――
• ⏳ Attempting to read: routes.csv
✓ ✅ Successfully read 2 lines

Route Data
――――――――――
📋 Route 
{
id: 963247f7-1fc9-412f-bc21-ab02d075022e,
 name: SCO-ITA
}


Route Data Menu
―――――――――――――――
1. Read route data
2. Write route data
3. Return to main menu
• 🔄 Choose an option: 
3

✈️ Airline Online Booking System
━━━━━━━━━━━━━━━━━━━━━━━
1. Manage Customer Data
2. Manage Booking Data
3. Manage Flight Data
4. Manage Route Data
5. Exit
🔄 Choose an option: 2

Booking Data Menu
―――――――――――――――――
1. Read booking data
2. Write booking data
3. Return to main menu
• 🔄 Choose an option: 
2

Save Booking Data
―――――――――――――――――
• 📂 Enter save path (e.g., bookings.csv): 
bookings.csv
• ➕ Add bookings (press enter after each, blank line to finish):

Add New Booking
―――――――――――――――
• 📛 Auto-generated ID: 143c20d3-b2a1-4a0a-90dc-b5d495bb0ee2
• 📅 Enter booking date (yyyy-MM-dd): 
2025-05-06
• 🧑 Enter customer ID: 
aada4f8f-4d00-40c8-8fbc-e4ae296e1290

Reading File
――――――――――――
• ⏳ Attempting to read: customers.csv
✓ ✅ Successfully read 2 lines
• ✈️ Enter flight ID: 
a8e1b03e-693a-4d29-827c-6a896da2f9db

Reading File
――――――――――――
• ⏳ Attempting to read: flights.csv
✓ ✅ Successfully read 2 lines

Reading File
――――――――――――
• ⏳ Attempting to read: customers.csv
✓ ✅ Successfully read 2 lines

Reading File
――――――――――――
• ⏳ Attempting to read: flights.csv
✓ ✅ Successfully read 2 lines
• ➕ Add another booking? (y/n): 
n
• 📋 Entities to save: 1
• • Booking 
{
id: 143c20d3-b2a1-4a0a-90dc-b5d495bb0ee2,
 date: 2025-05-06,
 customerId: aada4f8f-4d00-40c8-8fbc-e4ae296e1290,
 flightId: a8e1b03e-693a-4d29-827c-6a896da2f9db
}
• 🔄 Append to existing file? (y/n, append adds to existing data): 
y

Reading File
――――――――――――
• ⏳ Attempting to read: bookings.csv
✓ ✅ Successfully read 0 lines
• ⏳ Saving booking data...

CSV Export
――――――――――
• Generated CSV preview (first 5 lines):
━━━━━━━━━━━━━━━━━━━━
│ id,date,customerId,flightId
│ 143c20d3-b2a1-4a0a-90dc-b5d495bb0ee2,2025-05-06,aada4f8f-4d00-40c8-8fbc-e4ae296e1290,a8e1b03e-693a-4d29-827c-6a896da2f9db
━━━━━━━━━━━━━━━━━━━━
• 📋 CSV content length: 150 characters
Proceed with saving? (y/n): y

Writing File
――――――――――――
• 📄 Existing file detected: 0 bytes
✓ ✅ Successfully wrote 150 characters
✓ Booking data saved successfully!

Saved Booking Data
――――――――――――――――――
📋 Booking 
{
id: 143c20d3-b2a1-4a0a-90dc-b5d495bb0ee2,
 date: 2025-05-06,
 customerId: aada4f8f-4d00-40c8-8fbc-e4ae296e1290,
 flightId: a8e1b03e-693a-4d29-827c-6a896da2f9db
}
• 📄 Check bookings.csv for saved data

Booking Data Menu
―――――――――――――――――
1. Read booking data
2. Write booking data
3. Return to main menu
• 🔄 Choose an option: 
1

Read Booking Data
―――――――――――――――――
• 📂 Enter file path (e.g., bookings.csv): 
bookings.csv
• ⏳ Loading booking data...

Reading File
――――――――――――
• ⏳ Attempting to read: bookings.csv
✓ ✅ Successfully read 2 lines

Booking Data
――――――――――――
📋 Booking 
{
id: 143c20d3-b2a1-4a0a-90dc-b5d495bb0ee2,
 date: 2025-05-06,
 customerId: aada4f8f-4d00-40c8-8fbc-e4ae296e1290,
 flightId: a8e1b03e-693a-4d29-827c-6a896da2f9db
}

Booking Data Menu
―――――――――――――――――
1. Read booking data
2. Write booking data
3. Return to main menu
• 🔄 Choose an option: 
3

✈️ Airline Online Booking System
━━━━━━━━━━━━━━━━━━━━━━━
1. Manage Customer Data
2. Manage Booking Data
3. Manage Flight Data
4. Manage Route Data
5. Exit
🔄 Choose an option: 5
👋 Exiting system. Goodbye!

Process finished with exit code 0
```
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