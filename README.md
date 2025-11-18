# Personal Finance Management System (Sistem Manajemen Keuangan Pribadi)

The **Personal Finance Management System** is a desktop application designed to help users efficiently manage their personal finances. This project was created as a college assignment, showcasing a practical implementation of Java Swing with SQLite database integration.

## ✨ Features

- 🔐 **Login Screen**  
  Secure login interface for user authentication with a clean and simple UI.

- 📝 **Sign Up Screen**  
  Easy registration for new users with proper validation to ensure account safety.

- 📊 **Home Dashboard**  
  Overview of finances including income, expenses, and current balance.

- 🥧 **Pie Charts Visualization**  
  Visual representation of spending and income categories using XChart.

- 💾 **Local Database Storage**  
  Data stored using SQLite for fast, lightweight, file-based access.

- 🧾 **Print & Export Report**  
  Generate PDF financial reports using iTextPDF.

- 🎨 **Improved UI Layout**  
  Updated interface for a more modern and user-friendly experience.

# Preview

<div align="center">

### <b>LOGIN</b>
<img width="798" height="598" alt="Screenshot (148)" src="https://github.com/user-attachments/assets/1228b80b-d3ae-4964-9d9a-4894f9e0baf6" />

### <b>SIGN UP</b>
<img width="797" height="598" alt="Screenshot (150)" src="https://github.com/user-attachments/assets/7a7d9360-a60d-4c24-bd2d-5bdf4caaecc0" />

### <b>HOME DASHBOARD</b>
<img width="1073" height="720" alt="Screenshot (152)" src="https://github.com/user-attachments/assets/6cf44401-b5e4-4e03-ad09-7d4af6382aed" />
</div>

---

## How to Use

### 1. Clone or Download the Project

Clone the repository or download the ZIP file & open the project in **NetBeans IDE** (or your preferred Java IDE).

### 2. Update Database Path

The application uses SQLite databases for storing user accounts and personal finance data. By default, the paths in the Java files point to:

C:\Users\RaFiveSRD\Documents\NetBeansProjects\PersonalFinanceManagementSystem\DataBase\

You need to update this path to match your own project folder. 

**Files to update:**

`Login.java`

```java
String userDbPath = "C:\\Users\\YourUsername\\Path\\To\\PersonalFinanceManagementSystem\\DataBase\\Finance_"
                    + username + ".db";
DBK.createNewUserDatabase(userDbPath);
```

`SignUp.java`

```java
String userDbPath = "C:\\Users\\YourUsername\\Path\\To\\PersonalFinanceManagementSystem\\DataBase\\" + username + ".db";
DBK.createNewUserDatabase(userDbPath);
```

`UserManager.java` (previously `DBA.java`)

```java
private static final String DB_FOLDER = "C:\\Users\\YourUsername\\Path\\To\\PersonalFinanceManagementSystem\\DataBase\\";
```
Replace C:\\Users\\YourUsername\\Path\\To\\PersonalFinanceManagementSystem\\DataBase\\ with the folder where you will store your databases.

### 3. Run the Application

Open **NetBeans IDE** and ensure all `.java` files are in the correct packages:

**Packages and Files:**

- `LoginSignup` → `Login.java`, `SignUp.java`  
- `personalfinancemanagementsystem` → `UserManager.java` (or `DBA.java`), `DBK.java`  
- `Page` → `Home.java`  

**Clean and Build the Project**  
   - Right-click the project in the Projects panel.  
   - Select **Clean and Build** to compile all files.

**Run the Application**  
   - Right-click `Login.java` in the `LoginSignup` package.  
   - Select **Run File** to start the application.

## 🛠 Tools & Technologies

- **Programming Language:** Java (Java Swing for GUI) – **JDK 1.8**  
- **IDE:** Apache NetBeans – **Apache-NetBeans-14-bin-windows-x64**  
- **Database:** SQLite (lightweight, file-based) – **sqlite-jdbc-3.51.0.0**  
- **Charts & Visualization:** XChart (Chart) – **xchart-3.8.8**  
- **PDF Generator:** iTextPDF – **itextpdf-5.5.4**


## 📄 License

This project is **open-source** and created for **educational purposes**.  
You are free to use, modify, and improve the code as part of learning or personal development.  
Feel free to explore, experiment, and build upon it 🤝
