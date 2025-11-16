# Personal Finance Management System

A **desktop application** designed to help users efficiently manage their personal finances. This project demonstrates practical implementation using **Java Swing**, **SQLite**, and **XChart** for data visualization.

---

## Features

- User **Sign Up** and **Login**
- Dashboard showing **financial summary**
- Record **Income** and **Expenses**
- View **Monthly Summary** in a **Donut Chart**
- Database persistence using **SQLite**
- Simple and intuitive GUI

---

## Installation

1. **Clone the repository**
   ```bash
   git clone <repository_url>
Open the project in NetBeans

Add required libraries (JARs) to your project:

sqlite-jdbc-3.51.0.0.jar

xchart-3.8.8.jar

Build and run the project

How to Use
1. Launch Application
Open NetBeans → Run Project → Login.java

2. Sign Up for a New User
Click Sign Up if you don’t have an account

Fill in your details

Database file will be created automatically

3. Login
Enter your credentials to access the dashboard

4. Dashboard Overview
View current Income, Expenses, and Balance

Add transactions via Income or Expense buttons

View monthly breakdown with a Donut Chart

5. Change Database Directory (Optional)
The default database location is:

makefile
Copy code
C:\Users\<Username>\Documents\NetBeansProjects\PersonalFinanceManagementSystem\DataBase\
To change:

Open DBK.java (or UserManager.java)

Locate the folder variable:

java
Copy code
private static final String DB_FOLDER = "C:\\Users\\<Username>\\Documents\\NetBeansProjects\\PersonalFinanceManagementSystem\\DataBase\\";
Update it to your preferred folder, e.g.:

java
Copy code
private static final String DB_FOLDER = "D:\\MyFinanceDB\\";
Make sure the folder exists, save changes, and rebuild the project

6. Backup Database
Regularly copy the database folder for backup

Dependencies
Java 8+

NetBeans IDE (or any Java IDE)

Libraries:

SQLite JDBC 3.51.0.0

XChart 3.8.8

Preview
Login & Sign Up Screen

Dashboard with Donut Chart

License
This project is for educational purposes. Feel free to modify and improve.
