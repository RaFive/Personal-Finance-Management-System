# Personal Finance Management System (Sistem Manajemen Keuangan Pribadi)

The **Personal Finance Management System** is a desktop application designed to help users efficiently manage their personal finances. This project was created as a college assignment, showcasing a practical implementation of Java Swing with SQLite database integration. During the development process, I used **AI assistance** to help with coding, documentation, and exploring implementation ideas.  

**Sistem Manajemen Keuangan Pribadi** adalah aplikasi desktop yang dirancang untuk membantu pengguna mengelola keuangan pribadi secara efisien. Proyek ini dibuat sebagai tugas kuliah dan menampilkan implementasi praktis dari Java Swing yang terintegrasi dengan database SQLite. Selama proses pengembangan, saya menggunakan **bantuan AI** untuk membantu dalam penulisan kode, dokumentasi, serta eksplorasi ide-ide implementasi.

## ✨ Features (Fitur)

- 🔐 **Login Screen**  
  Secure login interface for user authentication with a clean and simple UI.  
  (Antarmuka login yang aman untuk autentikasi pengguna dengan tampilan yang bersih dan sederhana.)

- 📝 **Sign Up Screen**  
  Easy registration for new users with proper validation to ensure account safety.
  (Pendaftaran mudah untuk pengguna baru dengan validasi yang tepat guna menjaga keamanan akun.)

- 📊 **Home Dashboard**  
  Overview of finances including income, expenses, and current balance.
  (Menampilkan ringkasan keuangan termasuk pemasukan, pengeluaran, dan saldo saat ini.)

- 🥧 **Pie Charts Visualization**  
  Visual representation of spending and income categories using XChart.
  (Representasi visual kategori pengeluaran dan pemasukan menggunakan XChart.)

- 💾 **Local Database Storage**  
  Data stored using SQLite for fast, lightweight, file-based access.  
  (Data disimpan menggunakan SQLite untuk akses cepat, ringan, dan berbasis file.)

- 🧾 **Print & Export Report**  
  Generate PDF financial reports using iTextPDF.
  (Menghasilkan laporan keuangan dalam format PDF menggunakan iTextPDF.)

# Preview

<div align="center">

### <b>LOGIN</b>
<img width="798" height="598" alt="Screenshot (148)" src="https://github.com/user-attachments/assets/1228b80b-d3ae-4964-9d9a-4894f9e0baf6" />

### <b>SIGN UP</b>
<img width="797" height="598" alt="Screenshot (150)" src="https://github.com/user-attachments/assets/7a7d9360-a60d-4c24-bd2d-5bdf4caaecc0" />
<img width="1077" height="726" alt="Screenshot (210)" src="https://github.com/user-attachments/assets/48a34d50-6bf3-4c5b-bd49-b94862f15c77" />
### <b>HOME DASHBOARD</b>

</div>

---

## How To Use (Cara Menggunakan)

### 1. Clone or Download the Project

Clone the repository or download the ZIP file & open the project in **NetBeans IDE**.

### 2. Update Database Path

The application uses SQLite databases for storing user accounts and personal finance data. By default, the paths in the Java files point to:

C:\Users\RaFiveSRD\Documents\NetBeansProjects\PersonalFinanceManagementSystem\DataBase\

You need to update this path to match your own project folder. 

**Files to update:**

`DBA.java`

```java
String url = "jdbc:sqlite:C:\\Users\\RaFiveSRD\\Documents\\NetBeansProjects\\PersonalFinanceManagementSystem\\DataBase\\Account.db";
```

`Login.java`

```java
String userDbPath = "C:\\Users\\RaFiveSRD\\Documents\\NetBeansProjects\\PersonalFinanceManagementSystem\\DataBase\\Finance_" + username + ".db";
```

`SignUp.java`

```java
String userDbPath = "C:\\Users\\RaFiveSRD\\Documents\\NetBeansProjects\\PersonalFinanceManagementSystem\\DataBase\\" + "Finance_" + username + ".db";
```

`UserManager.java`

```java
private static final String DB_FOLDER = "C:\\Users\\RaFiveSRD\\Documents\\NetBeansProjects\\PersonalFinanceManagementSystem\\DataBase\\";
```

Replace `C:\\Users\\RaFiveSRD\\Documents\\NetBeansProjects\\PersonalFinanceManagementSystem\\DataBase\\` with the folder where you will store your databases.

### 3. Install Required JAR Libraries

This project uses several external libraries to enable database access, chart visualization, and PDF generation.

#### **Database: SQLite JDBC**
- File: **sqlite-jdbc-3.51.0.0.jar**
- Purpose: Connects Java to the SQLite database.

#### **Charts & Visualization**
- File: **xchart-3.8.8.jar**
- Purpose: Displays charts for financial data (income/expense).

#### **PDF Generator**
- File: **itextpdf-5.5.4.jar**
- Purpose: Generates PDF reports from financial data.

### How to Add JAR Files in NetBeans

1. Right-click the project → **Properties**
2. Open the **Libraries** section
3. Click **Add JAR/Folder**
4. Select the following files:
   - `sqlite-jdbc-3.51.0.0.jar`
   - `xchart-3.8.8.jar`
   - `itextpdf-5.5.4.jar`
5. Click **OK**

Your project is now ready to use all the required libraries.

### 4. Run the Application

Before running the application, ensure that all `.java` files are placed in the correct packages:

#### **Package Structure:**

- `LoginSignup`  
  - `Login.java`  
  - `SignUp.java`

- `personalfinancemanagementsystem`  
  - `UserManager.java`  
  - `DBK.java`

- `Page`  
  - `Home.java`

### Run Steps

#### **A. Clean and Build the Project**
1. Right-click the project in the **Projects** panel.
2. Select **Clean and Build** to compile all source files.

#### **B. Run the Application**
1. Navigate to the `LoginSignup` package.
2. Right-click `Login.java`.
3. Select **Run File** to start the application.

The login window should now appear, and you can begin using the system.

---

## Indonesia:

### 1. Clone atau Download Proyek

Clone repository atau unduh file ZIP, lalu buka proyek tersebut di **NetBeans IDE**.

### 2. Perbarui Path Database

Aplikasi ini menggunakan database SQLite untuk menyimpan akun pengguna dan data keuangan pribadi. Secara default, path pada file Java mengarah ke:

C:\Users\RaFiveSRD\Documents\NetBeansProjects\PersonalFinanceManagementSystem\DataBase\

Anda perlu memperbarui path tersebut agar sesuai dengan folder proyek Anda sendiri.

**File yang perlu diperbarui:**

`DBA.java`

```java
String url = "jdbc:sqlite:C:\\Users\\RaFiveSRD\\Documents\\NetBeansProjects\\PersonalFinanceManagementSystem\\DataBase\\Account.db";
```

`Login.java`

```java
String userDbPath = "C:\\Users\\RaFiveSRD\\Documents\\NetBeansProjects\\PersonalFinanceManagementSystem\\DataBase\\Finance_" + username + ".db";
```

`SignUp.java`

```java
String userDbPath = "C:\\Users\\RaFiveSRD\\Documents\\NetBeansProjects\\PersonalFinanceManagementSystem\\DataBase\\" + "Finance_" + username + ".db";
```

`UserManager.java`

```java
private static final String DB_FOLDER = "C:\\Users\\RaFiveSRD\\Documents\\NetBeansProjects\\PersonalFinanceManagementSystem\\DataBase\\";
```

Ganti `C:\\Users\\RaFiveSRD\\Documents\\NetBeansProjects\\PersonalFinanceManagementSystem\\DataBase\\` dengan folder tempat Anda akan menyimpan database Anda.

### 3. Install Library JAR yang Dibutuhkan

Proyek ini menggunakan beberapa library eksternal untuk mengaktifkan akses database, visualisasi grafik, dan pembuatan PDF.

#### **Database: SQLite JDBC**
- File: **sqlite-jdbc-3.51.0.0.jar**
- Fungsi: Menghubungkan Java dengan database SQLite.

#### **Grafik & Visualisasi**
- File: **xchart-3.8.8.jar**
- Fungsi: Menampilkan grafik data keuangan (pemasukan/pengeluaran).

#### **Generator PDF**
- File: **itextpdf-5.5.4.jar**
- Fungsi: Membuat laporan PDF dari data keuangan.

### Cara Menambahkan File JAR di NetBeans

1. Klik kanan pada proyek → **Properties**
2. Buka bagian **Libraries**
3. Klik **Add JAR/Folder**
4. Pilih file berikut:
   - `sqlite-jdbc-3.51.0.0.jar`
   - `xchart-3.8.8.jar`
   - `itextpdf-5.5.4.jar`
5. Klik **OK**

Sekarang proyek Anda siap menggunakan semua library yang diperlukan.

### 4. Jalankan Aplikasi

Sebelum menjalankan aplikasi, pastikan semua file `.java` berada pada package yang benar:

#### **Struktur Package:**

- `LoginSignup`  
  - `Login.java`  
  - `SignUp.java`

- `personalfinancemanagementsystem`  
  - `UserManager.java`  
  - `DBK.java`

- `Page`  
  - `Home.java`

### Langkah Menjalankan

#### **A. Clean dan Build Proyek**
1. Klik kanan proyek pada panel **Projects**.
2. Pilih **Clean and Build** untuk mengompilasi semua file sumber.

#### **B. Jalankan Aplikasinya**
1. Masuk ke package `LoginSignup`.
2. Klik kanan `Login.java`.
3. Pilih **Run File** untuk memulai aplikasi.

Jendela login kini akan muncul, dan Anda dapat mulai menggunakan sistem.

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

Proyek ini bersifat **open-source** dan dibuat untuk **keperluan edukasi**.  
Anda bebas menggunakan, memodifikasi, dan mengembangkan kode ini sebagai bagian dari pembelajaran atau pengembangan pribadi.  
Jangan ragu untuk mengeksplorasi, bereksperimen, dan membangunnya lebih lanjut 🤝
