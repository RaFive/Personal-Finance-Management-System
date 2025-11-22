/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personalfinancemanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author RaFiveSRD
 */
public class DBA {
    private static Connection conn = null;

    public static Connection DBACC() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:C:\\Users\\RaFiveSRD\\Documents\\NetBeansProjects\\PersonalFinanceManagementSystem\\DataBase\\Account.db";
            conn = DriverManager.getConnection(url);
            return conn;
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver SQLite tidak ditemukan: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal koneksi ke database: " + e.getMessage());
            return null;
        }
    }

    public DBA() {}
}