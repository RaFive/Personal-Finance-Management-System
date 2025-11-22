/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personalfinancemanagementsystem;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author RaFiveSRD
 */
public class Laporan {
    
    private final DBK userDBK;
    private final JFrame parentFrame;
    private final DecimalFormat formatter;
    
    public Laporan(DBK userDBK, JFrame parentFrame) {
        this.userDBK = userDBK;
        this.parentFrame = parentFrame;
        this.formatter = new DecimalFormat("#,###");
    }
    
    public void showLaporanDialog() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new java.awt.GridLayout(0, 1, 10, 10));

        JLabel labelPeriode = new JLabel("Pilih Periode Laporan:");

        List<Integer> availableYears = getAvailableYears();

        List<String> periodList = new ArrayList<>();
        periodList.add("Bulan Ini");

        for (Integer tahun : availableYears) {
            periodList.add("Januari " + tahun);
            periodList.add("Februari " + tahun);
            periodList.add("Maret " + tahun);
            periodList.add("April " + tahun);
            periodList.add("Mei " + tahun);
            periodList.add("Juni " + tahun);
            periodList.add("Juli " + tahun);
            periodList.add("Agustus " + tahun);
            periodList.add("September " + tahun);
            periodList.add("Oktober " + tahun);
            periodList.add("November " + tahun);
            periodList.add("Desember " + tahun);
        }
        
        periodList.add("Semua Data");
        
        String[] periodOptions = periodList.toArray(new String[0]);
        JComboBox<String> comboPeriode = new JComboBox<>(periodOptions);
 
        JLabel labelAksi = new JLabel("Pilih Aksi:");
        String[] aksiOptions = {"Simpan sebagai PDF", "Cetak Langsung"};
        JComboBox<String> comboAksi = new JComboBox<>(aksiOptions);
        
        mainPanel.add(labelPeriode);
        mainPanel.add(comboPeriode);
        mainPanel.add(labelAksi);
        mainPanel.add(comboAksi);
        
        int result = JOptionPane.showConfirmDialog(
            parentFrame, 
            mainPanel, 
            "Generate Laporan Keuangan", 
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            String periode = (String) comboPeriode.getSelectedItem();
            String aksi = (String) comboAksi.getSelectedItem();
            
            if (aksi.equals("Simpan sebagai PDF")) {
                simpanLaporanPDF(periode);
            } else {
                cetakLaporan(periode);
            }
        }
    }
    
    private void simpanLaporanPDF(String periode) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Simpan Laporan PDF");

            String defaultName = "Laporan_Keuangan_" + 
                new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date()) + ".pdf";
            fileChooser.setSelectedFile(new File(defaultName));

            javax.swing.filechooser.FileNameExtensionFilter filter = 
                new javax.swing.filechooser.FileNameExtensionFilter("PDF Files", "pdf");
            fileChooser.setFileFilter(filter);
            
            int userSelection = fileChooser.showSaveDialog(parentFrame);
            
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
                
                if (!filePath.toLowerCase().endsWith(".pdf")) {
                    filePath += ".pdf";
                }

                generatePDF(filePath, periode);
                
                JOptionPane.showMessageDialog(parentFrame, 
                    "Laporan berhasil disimpan di:\n" + filePath,
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, 
                "Gagal menyimpan laporan!\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generatePDF(String filePath, String periode) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        
        document.open();

        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        
        Font titleFont = new Font(bf, 18, Font.BOLD);
        Font headerFont = new Font(bf, 12, Font.BOLD);
        Font normalFont = new Font(bf, 10, Font.NORMAL);

        Paragraph title = new Paragraph("LAPORAN KEUANGAN", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph periodePara = new Paragraph("Periode: " + sanitizeText(periode), headerFont);
        periodePara.setAlignment(Element.ALIGN_CENTER);
        periodePara.setSpacingBefore(10);
        periodePara.setSpacingAfter(20);
        document.add(periodePara);

        String tanggalCetak = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        document.add(new Paragraph("Tanggal Cetak: " + tanggalCetak, normalFont));
        document.add(new Paragraph(" "));

        addRingkasanKeuangan(document, periode, headerFont, normalFont);
        
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        addDetailTransaksi(document, periode, headerFont, normalFont);
        
        document.close();
        writer.close();
    }

    private String sanitizeText(String text) {
        if (text == null) return "";
        return text.replaceAll("[^\\x00-\\x7F]", "?");
    }

    private void addRingkasanKeuangan(Document document, String periode, Font headerFont, Font normalFont) throws DocumentException {
        DBK summary = getSummaryByPeriode(periode);
        
        document.add(new Paragraph("RINGKASAN KEUANGAN", headerFont));
        document.add(new Paragraph(" "));
        
        PdfPTable summaryTable = new PdfPTable(2);
        summaryTable.setWidthPercentage(100);
        summaryTable.setWidths(new float[]{2, 2});
        
        addTableCell(summaryTable, "Uang Sekarang", headerFont);
        addTableCell(summaryTable, "Rp " + formatter.format(summary.uangSekarang), normalFont);
        
        addTableCell(summaryTable, "Total Uang Masuk", headerFont);
        addTableCell(summaryTable, "Rp " + formatter.format(summary.uangMasuk), normalFont);
        
        addTableCell(summaryTable, "Total Uang Keluar", headerFont);
        addTableCell(summaryTable, "Rp " + formatter.format(summary.uangKeluar), normalFont);
        
        int selisih = summary.uangMasuk - summary.uangKeluar;
        addTableCell(summaryTable, "Selisih Periode Ini", headerFont);
        String selisihText = (selisih >= 0 ? "+ " : "- ") + "Rp " + formatter.format(Math.abs(selisih));
        addTableCell(summaryTable, selisihText, normalFont);
        
        document.add(summaryTable);
    }

    private void addDetailTransaksi(Document document, String periode, Font headerFont, Font normalFont) throws DocumentException {
        document.add(new Paragraph("DETAIL TRANSAKSI", headerFont));
        document.add(new Paragraph(" "));

        List<DBK> dataList = getDataByPeriode(periode);
        
        if (dataList.isEmpty()) {
            document.add(new Paragraph("Tidak ada data transaksi.", normalFont));
        } else {
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 3, 2, 3});

            addTableCell(table, "No", headerFont);
            addTableCell(table, "Tanggal", headerFont);
            addTableCell(table, "Jumlah", headerFont);
            addTableCell(table, "Keterangan", headerFont);

            int no = 1;
            for (DBK d : dataList) {
                addTableCell(table, String.valueOf(no++), normalFont);

                String tanggal = d.tanggal != null ? sanitizeText(d.tanggal) : "";
                addTableCell(table, tanggal, normalFont);
                
                String jumlahText;
                if (d.uangMasuk > 0) {
                    jumlahText = "+ Rp " + formatter.format(d.uangMasuk);
                } else if (d.uangKeluar > 0) {
                    jumlahText = "- Rp " + formatter.format(d.uangKeluar);
                } else {
                    jumlahText = "Rp 0";
                }
                addTableCell(table, jumlahText, normalFont);

                String keterangan = d.keterangan != null ? sanitizeText(d.keterangan) : "";
                addTableCell(table, keterangan, normalFont);
            }
            
            document.add(table);
        }
    }

    private void addTableCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(sanitizeText(text), font));
        cell.setPadding(5);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
    }

    private void cetakLaporan(String periode) {
        try {
            JPanel printPanel = createPrintPanel(periode);

            PrinterJob pj = PrinterJob.getPrinterJob();
            pj.setJobName("Laporan Keuangan");
            
            pj.setPrintable(new Printable() {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                    if (pageIndex > 0) {
                        return Printable.NO_SUCH_PAGE;
                    }
                    
                    Graphics2D g2d = (Graphics2D) graphics;
                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                    double scaleX = pageFormat.getImageableWidth() / printPanel.getWidth();
                    double scaleY = pageFormat.getImageableHeight() / printPanel.getHeight();
                    double scale = Math.min(scaleX, scaleY);
                    g2d.scale(scale, scale);
                    
                    printPanel.printAll(graphics);
                    
                    return Printable.PAGE_EXISTS;
                }
            });
            
            if (pj.printDialog()) {
                pj.print();
                JOptionPane.showMessageDialog(parentFrame, 
                    "Laporan berhasil dikirim ke printer!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, 
                "Gagal mencetak laporan!\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private JPanel createPrintPanel(String periode) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("LAPORAN KEUANGAN");
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        
        panel.add(Box.createVerticalStrut(10));

        JLabel periodeLabel = new JLabel("Periode: " + periode);
        periodeLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        periodeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(periodeLabel);
        
        panel.add(Box.createVerticalStrut(5));

        String tanggalCetak = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        JLabel tanggalLabel = new JLabel("Tanggal Cetak: " + tanggalCetak);
        tanggalLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 10));
        tanggalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(tanggalLabel);
        
        panel.add(Box.createVerticalStrut(20));

        addRingkasanToPanel(panel, periode);
        
        panel.add(Box.createVerticalStrut(20));

        addDetailToPanel(panel, periode);
        
        return panel;
    }
    

    private void addRingkasanToPanel(JPanel panel, String periode) {
        DBK summary = getSummaryByPeriode(periode);
        
        JLabel summaryTitle = new JLabel("RINGKASAN KEUANGAN");
        summaryTitle.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        panel.add(summaryTitle);
        
        panel.add(Box.createVerticalStrut(10));
        
        panel.add(new JLabel("Uang Sekarang: Rp " + formatter.format(summary.uangSekarang)));
        panel.add(new JLabel("Total Uang Masuk: Rp " + formatter.format(summary.uangMasuk)));
        panel.add(new JLabel("Total Uang Keluar: Rp " + formatter.format(summary.uangKeluar)));
        
        int selisih = summary.uangMasuk - summary.uangKeluar;
        String selisihText = "Selisih Periode Ini: " + (selisih >= 0 ? "+ " : "- ") + 
            "Rp " + formatter.format(Math.abs(selisih));
        panel.add(new JLabel(selisihText));
    }

    private void addDetailToPanel(JPanel panel, String periode) {
        JLabel detailTitle = new JLabel("DETAIL TRANSAKSI");
        detailTitle.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        panel.add(detailTitle);
        
        panel.add(Box.createVerticalStrut(10));
        
        List<DBK> dataList = getDataByPeriode(periode);
        
        if (dataList.isEmpty()) {
            panel.add(new JLabel("Tidak ada data transaksi."));
        } else {
            String[] kolom = {"No", "Tanggal", "Jumlah", "Keterangan"};
            Object[][] rowData = new Object[dataList.size()][4];
            
            int no = 1;
            for (int i = 0; i < dataList.size(); i++) {
                DBK d = dataList.get(i);
                rowData[i][0] = no++;
                rowData[i][1] = d.tanggal != null ? d.tanggal : "";
                
                if (d.uangMasuk > 0) {
                    rowData[i][2] = "+ Rp " + formatter.format(d.uangMasuk);
                } else if (d.uangKeluar > 0) {
                    rowData[i][2] = "- Rp " + formatter.format(d.uangKeluar);
                } else {
                    rowData[i][2] = "Rp 0";
                }
                
                rowData[i][3] = d.keterangan != null ? d.keterangan : "";
            }
            
            DefaultTableModel model = new DefaultTableModel(rowData, kolom);
            JTable printTable = new JTable(model);
            printTable.setEnabled(false);
            
            JScrollPane scrollPane = new JScrollPane(printTable);
            scrollPane.setPreferredSize(new Dimension(700, 400));
            panel.add(scrollPane);
        }
    }

    private List<DBK> getDataByPeriode(String periode) {
        if (periode == null) {
            return new ArrayList<>();
        }
        
        if (periode.equals("Bulan Ini")) {
            return getDataBulanIni();
        } else if (periode.equals("Semua Data")) {
            return userDBK.getAll();
        } else {
            return getDataByBulanTahun(periode);
        }
    }
    
    private List<DBK> getDataBulanIni() {
        List<DBK> result = new ArrayList<>();
        String bulanIni = LocalDate.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM"));
        
        List<DBK> semua = userDBK.getAll();
        if (semua == null) {
            return result;
        }
        
        for (DBK d : semua) {
            if (d != null && d.tanggal != null && d.tanggal.startsWith(bulanIni)) {
                result.add(d);
            }
        }
        return result;
    }
    
    private List<DBK> getDataByBulanTahun(String periode) {
        List<DBK> result = new ArrayList<>();
        
        if (periode == null) {
            return result;
        }

        String[] parts = periode.split(" ");
        if (parts.length != 2) {
            return result;
        }
        
        String namaBulan = parts[0];
        String tahun = parts[1];

        int nomorBulan = getBulanNumber(namaBulan);
        if (nomorBulan == -1) {
            return result;
        }

        String formatBulan = String.format("%s-%02d", tahun, nomorBulan);
        
        List<DBK> semua = userDBK.getAll();
        if (semua == null) {
            return result;
        }
        
        for (DBK d : semua) {
            if (d != null && d.tanggal != null && d.tanggal.startsWith(formatBulan)) {
                result.add(d);
            }
        }
        
        return result;
    }
    
    private int getBulanNumber(String namaBulan) {
        if (namaBulan == null) {
            return -1;
        }
        
        switch (namaBulan) {
            case "Januari": return 1;
            case "Februari": return 2;
            case "Maret": return 3;
            case "April": return 4;
            case "Mei": return 5;
            case "Juni": return 6;
            case "Juli": return 7;
            case "Agustus": return 8;
            case "September": return 9;
            case "Oktober": return 10;
            case "November": return 11;
            case "Desember": return 12;
            default: return -1;
        }
    }
    
    private DBK getSummaryByPeriode(String periode) {
        List<DBK> dataList = getDataByPeriode(periode);

        DBK summary = userDBK.getSummaryBulanIni();
 
        if (periode != null && periode.equals("Bulan Ini")) {
            return summary;
        }

        if (summary != null) {
            summary.uangMasuk = 0;
            summary.uangKeluar = 0;
            
            if (dataList != null) {
                for (DBK d : dataList) {
                    if (d != null) {
                        summary.uangMasuk += d.uangMasuk;
                        summary.uangKeluar += d.uangKeluar;
                    }
                }
            }
        }
        
        return summary;
    }
    
    private DBK createEmptySummary() {
        return userDBK.getSummaryBulanIni();
    }

    private List<Integer> getAvailableYears() {
        List<Integer> years = new ArrayList<>();
        List<DBK> allData = userDBK.getAll();
        
        if (allData == null || allData.isEmpty()) {
            years.add(LocalDate.now().getYear());
            return years;
        }

        for (DBK d : allData) {
            if (d != null && d.tanggal != null && d.tanggal.length() >= 4) {
                try {
                    int year = Integer.parseInt(d.tanggal.substring(0, 4));
                    if (!years.contains(year)) {
                        years.add(year);
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }

        if (years.isEmpty()) {
            years.add(LocalDate.now().getYear());
        }

        years.sort((a, b) -> b.compareTo(a));
        
        return years;
    }
}