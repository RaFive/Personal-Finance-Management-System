# Personal Finance Management System

The **Personal Finance Management System** is a desktop application designed to help users efficiently manage their personal finances. This project was created as a college assignment, showcasing a practical implementation of **Java Swing** with **SQLite database** integration.  

---

## Features
- **Login Screen**  
  A secure login interface that authenticates users before accessing the application. Provides a simple and clean UI for user authentication.  

- **Sign Up Screen**  
  Easy registration for new users with validation to ensure account security. Users can quickly create an account and start managing their finances.  

- **Home Dashboard**  
  The main interface showing an overview of your finances, including income, expenses, and current balance. Includes visual **Donut Charts** for quick insights.  

- **Transaction Management**  
  Add, edit, or delete transactions seamlessly. Keep track of your financial activities in one place.  

- **Transaction History**  
  View a detailed history of all past transactions for better financial planning and tracking.  

---

## Preview

<div align="center">

### **LOGIN**
![Login Preview](https://github.com/user-attachments/assets/a28e6dea-c5ee-4a02-b9f1-b94dd6579dee)

### **SIGN UP**
![Sign Up Preview](https://github.com/user-attachments/assets/7c432c34-0d35-4349-9145-d1bcd14151a1)

### **HOME DASHBOARD**
![Home Preview](https://github.com/user-attachments/assets/f0cf7757-8620-4538-9241-06605fc21290)

</div>

---

## How to Use

1. **Clone or Download the Project**  
   Make sure you have Java and NetBeans IDE installed. Open the project in NetBeans.  

2. **Configure the Database Directory**  
   By default, the application stores SQLite databases in:  
   C:\Users\YourUsername\Documents\NetBeansProjects\PersonalFinanceManagementSystem\DataBase\
   To change the directory:  
- Open `UserManager.java` (or `DBK.java` in some versions).  
- Update the `DB_FOLDER` path to your preferred location:
  ```java
  private static final String DB_FOLDER = "D:\\MyFinanceApp\\Database\\";
  ```

3. **Run the Application**  
- Start the project in NetBeans.  
- Use the **Sign Up** screen to create a new account.  
- Login using your credentials to access the **Home Dashboard**.  

4. **Using the Features**  
- Add, edit, or delete financial transactions.  
- View detailed transaction history.  
- Check your income, expenses, and balance in the dashboard charts.  

---

## Dependencies
- **SQLite JDBC 3.51.0.0** – For local database management.  
- **XChart 3.8.8** – For displaying charts in the dashboard.
