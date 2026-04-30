# 📊 Accounting Ledger

A command-line Java application that allows users to track financial transactions, including deposits and payments, and generate reports based on date ranges or vendors.

---

## 🚀 Features

- Add deposits and payments
- Automatically timestamps each transaction
- Stores data in a `.csv` file
- View:
    - All transactions
    - Deposits only
    - Payments only
- Generate reports:
    - Month-to-date
    - Previous month
    - Year-to-date
    - Previous year
    - Search by vendor
- Color-coded output:
    - 🟢 Deposits (green)
    - 🔴 Payments (red)

---

## 🛠️ Tech Stack

- Java
- File I/O (`BufferedReader`, `BufferedWriter`)
- Collections (`ArrayList`)
- Date/Time API (`LocalDate`, `LocalDateTime`)

---

## 📂 Project Structure


src/
└── main/
├── java/
│ └── com/pluralsight/
│ ├── AccountingLedger.java
│ └── Transaction.java
└── resources/
└── transactions.csv


---

## ⚙️ How It Works

- The app runs in a loop displaying a menu
- User selects options to add or view transactions
- Transactions are saved in `transactions.csv` using this format:


YYYY-MM-DD|HH:mm:ss|Description|Vendor|Amount


- Deposits are stored as positive values
- Payments are stored as negative values

---

## ▶️ Running the Application

### 1. Compile

```bash
javac com/pluralsight/*.java
2. Run
java com.pluralsight.AccountingLedger
🧪 Example Usage
What is your name? :
John

-----John's Accounting Ledger-----

1. Add a Deposit
2. Make a Payment
3. Display Ledger
4. Exit Application
📊 Sample Output
| DATE       | TIME     | DESCRIPTION | VENDOR   | PRICE  |
2026-04-30|12:30:15|Salary|Company|1500.00
2026-04-30|14:10:02|Groceries|Walmart|-120.50
⚠️ Input Validation
Ensures transaction amounts are valid numbers
Prompts user again on invalid input
🔮 Future Improvements
Add GUI (JavaFX or Swing)
Export reports to files
Improve error handling
Add unit tests
Sort transactions by date
📄 License

This project is open-source and available under the MIT License.