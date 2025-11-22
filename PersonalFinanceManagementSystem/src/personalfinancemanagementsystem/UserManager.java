/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personalfinancemanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author RaFiveSRD
 */
public class UserManager {

    private static final String DB_FOLDER = "C:\\Users\\RaFiveSRD\\Documents\\NetBeansProjects\\PersonalFinanceManagementSystem\\DataBase\\";

    public static boolean createNewUserDatabase(String username) {
        String dbPath = DB_FOLDER + username + ".db";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath)) {
            if (conn != null) {
                String sql = "CREATE TABLE IF NOT EXISTS Keuangan (" +
                             "Ujumlah INTEGER," +
                             "Umasuk INTEGER," +
                             "Ukeluar INTEGER," +
                             "Tanggal TEXT," +
                             "Keterangan TEXT" +
                             ")";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.execute();
                ps.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
