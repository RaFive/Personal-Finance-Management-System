/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personalfinancemanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RaFiveSRD
 */
public class DBK {
    private final String dbUrl;
    private static DBK instance;
    public int id;
    public int uangMasuk;
    public int uangKeluar;
    public int uangSekarang;
    public String tanggal;
    public String keterangan;

    public DBK(String userDbPath) {
        this.dbUrl = "jdbc:sqlite:" + userDbPath;
    }

    public static void setInstance(DBK db) {
        instance = db;
    }

    public static DBK getInstance() {
        return instance;
    }

    DBK() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public DBK getData() {
        DBK d = new DBK(this.dbUrl.replace("jdbc:sqlite:", ""));

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM Keuangan ORDER BY ID DESC LIMIT 1");
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                d.id = rs.getInt("ID");
                d.uangMasuk = rs.getInt("Umasuk");
                d.uangKeluar = rs.getInt("Ukeluar");
                d.uangSekarang = rs.getInt("Ujumlah");
                d.tanggal = rs.getString("Tanggal");
                d.keterangan = rs.getString("Keterangan");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return d;
    }

    public static DBK getDataStatic() {
        if (instance == null) return null;
        return instance.getData();
    }

    public List<DBK> getAll() {
        List<DBK> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Keuangan ORDER BY ID ASC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DBK d = new DBK(this.dbUrl.replace("jdbc:sqlite:", ""));
                d.id = rs.getInt("ID");
                d.uangMasuk = rs.getInt("Umasuk");
                d.uangKeluar = rs.getInt("Ukeluar");
                d.uangSekarang = rs.getInt("Ujumlah");
                d.tanggal = rs.getString("Tanggal");
                d.keterangan = rs.getString("Keterangan");
                list.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insertData(int masuk, int keluar, String tanggal, String ket) {

        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            conn.setAutoCommit(false);

            List<DBK> semua = getAll();
            int saldoTerakhir = semua.isEmpty() ? 0 : semua.get(semua.size() - 1).uangSekarang;

            int saldoBaru = saldoTerakhir + masuk - keluar;

            String sql = "INSERT INTO Keuangan (Ujumlah, Umasuk, Ukeluar, Tanggal, Keterangan) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, saldoBaru);
                ps.setInt(2, masuk);
                ps.setInt(3, keluar);
                ps.setString(4, tanggal);
                ps.setString(5, ket);
                ps.executeUpdate();
            }

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteDataById(int id) {
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            conn.setAutoCommit(false);

            String sql = "DELETE FROM Keuangan WHERE ID = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            recalculateSaldo(conn);
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDataById(int id, int masukBaru, int keluarBaru, String tanggalBaru, String ketBaru) {

        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            conn.setAutoCommit(false);

            String sql = "UPDATE Keuangan SET Umasuk = ?, Ukeluar = ?, Tanggal = ?, Keterangan = ? WHERE ID = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, masukBaru);
                ps.setInt(2, keluarBaru);
                ps.setString(3, tanggalBaru);
                ps.setString(4, ketBaru);
                ps.setInt(5, id);
                ps.executeUpdate();
            }

            recalculateSaldo(conn);
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recalculateSaldo(Connection conn) throws SQLException {
        List<DBK> semua = getAll();
        int saldo = 0;

        String sql = "UPDATE Keuangan SET Ujumlah = ? WHERE ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (DBK d : semua) {
                saldo = saldo + d.uangMasuk - d.uangKeluar;
                ps.setInt(1, saldo);
                ps.setInt(2, d.id);
                ps.executeUpdate();
            }
        }
    }

    public DBK getSummaryBulanIni() {

        DBK summary = new DBK(this.dbUrl.replace("jdbc:sqlite:", ""));

        try (Connection conn = DriverManager.getConnection(dbUrl)) {

            String bulanIni = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));

            String sql = "SELECT SUM(Umasuk) AS totalMasuk, SUM(Ukeluar) AS totalKeluar " +
                         "FROM Keuangan WHERE substr(Tanggal,1,7) = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, bulanIni);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        summary.uangMasuk = rs.getInt("totalMasuk");
                        summary.uangKeluar = rs.getInt("totalKeluar");
                    }
                }
            }

            summary.uangSekarang = getData().uangSekarang;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return summary;
    }

    public static void createNewUserDatabase(String dbPath) {

        String url = "jdbc:sqlite:" + dbPath;

        String sql = "CREATE TABLE IF NOT EXISTS Keuangan (" +
                     "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "Ujumlah INTEGER," +
                     "Umasuk INTEGER," +
                     "Ukeluar INTEGER," +
                     "Tanggal TEXT," +
                     "Keterangan TEXT" +
                     ");";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}   