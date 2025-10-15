# Tiny Ledger API

A simple, in-memory ledger application implemented using Spring Boot (Java) to expose RESTful APIs for recording deposits, withdrawals, viewing the current balance, and retrieving transaction history.

## ⚙️ Technical Details

* **Technology:** Java, Spring Boot, Maven (for dependency management)
* **Data Storage:** In-memory `List` 
* **Monetary Handling:** Uses `BigDecimal` for precision, accepts/returns amounts as **String**.
* **Assumption:** The code implementation is primarily focused on the correctness with default thread safety mechanisms. For better scalability/performance, Granular/external locks can be added.  
## ▶️ How to Run Locally

### Prerequisites

1.  **Java Development Kit (JDK) 17+**
2.  **Maven 3.6.0 or higher**

### Steps
1. **Run the Application:**
    Clone the repository:
   ```bash
   git clone https://github.com/Manjkumar/TinyLedger.git
   ```
   ```bash
   cd TinyLedger
    ```
   ```bash
   cd TinyLedger
   ```
   ```bash
   mvn clean install
   mvn spring-boot:run
   # Or Execute the Jar file
   java -jar target/tinyledger-1.0-SNAPSHOT.jar
   ```
2. The application will start on http://localhost:8080

## 🧪 How to Execute Features (API Examples)

Use a tool like cURL to test the endpoints.

**Base URL:** `http://localhost:8080/api/ledger`

### 3. Record a Deposit

**Endpoint:** `POST /api/ledger/deposit`
**Headers:** `Content-Type: application/json`
### Example: Deposit 500.00
```bash
curl -X POST http://localhost:8080/api/ledger/deposit \
-H "Content-Type: application/json" \
-d '{"amount": "500.00", "description": "Initial funding"}'
```
```bash
#Expected Response
{
"id": "1",
"timestamp": "2025-10-15T12:00:00.123456",
"type": "DEPOSIT",
"amount": "500.00",
"description": "Initial funding",
"runningBalance": "500.00"
}
```
### 4. Record a Withdrawal
**Endpoint:** `POST /api/ledger/withdrawal`
**Headers:** `Content-Type: application/json`
### Example: Withdraw 100.00
```bash
curl -X POST http://localhost:8080/api/ledger/withdrawal \
-H "Content-Type: application/json" \
-d '{"amount": "100.00", "description": "withdraw"}'
```
```bash
#Expected Response
{
"id": "1",
"timestamp": "2025-10-15T12:00:00.123456",
"type": "WITHDRAWAL",
"amount": "100.00",
"description": "withdraw",
"runningBalance": "400.00"
}
```
### 5. View balance
GET /api/ledger/balance
```bash
curl http://localhost:8080/api/ledger/balance
```
```bash
#Expected Response
{
    "currentBalance": "400.00"
}
```
### 6. View transaction history
GET /api/ledger/history
```bash
curl http://localhost:8080/api/ledger/history
```
```bash
#Expected Response
[
    { /* Transaction 1 json details */ },
    { /* Transaction n json details */ }
]

```
