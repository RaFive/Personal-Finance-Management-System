/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import personalfinancemanagementsystem.DBK;
/**
 *
 * @author RaFiveSRD
 */
public class Home extends javax.swing.JFrame {
    private DBK userDBK;          
    private String userDbPath;     

    public Home(String userDbPath) {
        initComponents();
        this.userDbPath = userDbPath;
        this.userDBK = new DBK(userDbPath);
        loadTable();
        setLocationRelativeTo(null);
        this.setResizable(false);

        DonutChart.setSize(450, 300);
        DonutChart.setPreferredSize(new java.awt.Dimension(450, 300));
        DonutChart.setLayout(new BorderLayout());

        tampilkanChart();
        updateDisplayKeuangan();
    }

    public Home() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private void updateDisplayKeuangan() {
        try {
            DBK summary = userDBK.getSummaryBulanIni();

            java.text.DecimalFormat formatter = new java.text.DecimalFormat("#,###");

            kotakUS.setText("Rp " + formatter.format(summary.uangSekarang));
            kotakUS.setEditable(false);

            kotakUM.setText("Rp " + formatter.format(summary.uangMasuk));
            kotakUM.setEditable(false);

            kotakUK.setText("Rp " + formatter.format(summary.uangKeluar));
            kotakUK.setEditable(false);
            
            int uangBulanIni = summary.uangMasuk - summary.uangKeluar;
        
            if (uangBulanIni > 0) {
                kotakUBI.setText("+ Rp " + formatter.format(uangBulanIni));
                kotakUBI.setForeground(new Color(0, 128, 0));
            } else if (uangBulanIni < 0) {
                kotakUBI.setText("- Rp " + formatter.format(Math.abs(uangBulanIni)));
                kotakUBI.setForeground(new Color(200, 0, 0));
            } else {
                kotakUBI.setText("Rp 0");
                kotakUBI.setForeground(Color.BLACK);
            }
            kotakUBI.setEditable(false);
            
        } catch (Exception e) {
            e.printStackTrace();
            kotakUS.setText("Rp 0");
            kotakUM.setText("Rp 0");
            kotakUK.setText("Rp 0");
            kotakUBI.setText("Rp 0");
            kotakUBI.setForeground(Color.BLACK);
        }
    }
    
    public JPanel createDonutChart(DBK userDBK) {
        DBK d = userDBK.getSummaryBulanIni();

        PieChart chart = new PieChartBuilder()
                .width(600)
                .height(400)
                .title("Keuangan Bulan Ini")
                .build();

        chart.addSeries("Uang Masuk", d.uangMasuk);
        chart.addSeries("Uang Keluar", d.uangKeluar);
        chart.addSeries("Uang Sekarang", d.uangSekarang);

        Color[] colors = new Color[]{
                new Color(0, 200, 0),
                new Color(255, 0, 0),
                new Color(0, 100, 255)
        };
        chart.getStyler().setSeriesColors(colors);
        chart.getStyler().setDonutThickness(0.5);      
        chart.getStyler().setPlotContentSize(0.7);     
        chart.getStyler().setLegendVisible(true);

        return new XChartPanel<>(chart);
    }

    private void tampilkanChart() {
        DonutChart.removeAll();
        DonutChart.setLayout(new BorderLayout());
        DonutChart.add(createDonutChart(userDBK), BorderLayout.CENTER);
        DonutChart.revalidate();
        DonutChart.repaint();
    }
      public void loadTable() {
    List<DBK> data = userDBK.getAll();

    String[] kolom = { "Jumlah", "Tanggal", "Keterangan", "ID" };
    Object[][] rowData = new Object[data.size()][4];

    for (int i = 0; i < data.size(); i++) {
        DBK d = data.get(i);

        if (d.uangMasuk > 0) {
            rowData[i][0] = "+ " + d.uangMasuk;
        } else if (d.uangKeluar > 0) {
            rowData[i][0] = "- " + d.uangKeluar;
        } else {
            rowData[i][0] = "0";
        }

        rowData[i][1] = d.tanggal;
        rowData[i][2] = d.keterangan;
        rowData[i][3] = d.id;
    }

    DefaultTableModel model = new DefaultTableModel(rowData, kolom);
    Table.setModel(model);

    Table.getColumnModel().getColumn(3).setMinWidth(0);
    Table.getColumnModel().getColumn(3).setMaxWidth(0);
    Table.getColumnModel().getColumn(3).setWidth(0);

    applyColorRenderer();
}

    public void applyColorRenderer() {
        Table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {

            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                java.awt.Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    c.setBackground(new Color(51, 153, 255));
                    c.setForeground(Color.WHITE);
                    return c;
                }

                if (column == 0 && value != null) {
                    String text = value.toString();
                    if (text.startsWith("+")) c.setForeground(new Color(0, 128, 0));
                    else if (text.startsWith("-")) c.setForeground(new Color(200, 0, 0));
                    else c.setForeground(Color.BLACK);
                } else {
                    c.setForeground(Color.BLACK);
                }

                c.setBackground(Color.WHITE);
                return c;
            }
        });
    }
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jColorChooser1 = new javax.swing.JColorChooser();
        jPanel1 = new javax.swing.JPanel();
        Menghapus = new javax.swing.JButton();
        Mengubah = new javax.swing.JButton();
        Segar = new javax.swing.JButton();
        DonutChart = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        Menambahkan = new javax.swing.JButton();
        Laporan = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        kotakUM = new javax.swing.JTextField();
        teksUM = new javax.swing.JLabel();
        iconUM = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        kotakUS = new javax.swing.JTextField();
        teksUS = new javax.swing.JLabel();
        iconUS = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        kotakUK = new javax.swing.JTextField();
        teksUK1 = new javax.swing.JLabel();
        iconUK1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        kotakUBI = new javax.swing.JTextField();
        teksUBI = new javax.swing.JLabel();
        iconUBI = new javax.swing.JLabel();

        jScrollPane2.setViewportView(jEditorPane1);

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel2.setText("jLabel2");

        jLabel3.setText("jLabel3");

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        Menghapus.setText("DELETE");
        Menghapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenghapusActionPerformed(evt);
            }
        });

        Mengubah.setText("EDIT");
        Mengubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MengubahActionPerformed(evt);
            }
        });

        Segar.setText("REFRESH");
        Segar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SegarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DonutChartLayout = new javax.swing.GroupLayout(DonutChart);
        DonutChart.setLayout(DonutChartLayout);
        DonutChartLayout.setHorizontalGroup(
            DonutChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        DonutChartLayout.setVerticalGroup(
            DonutChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Jumlah", "Tanggal", "Keterangan"
            }
        ));
        jScrollPane1.setViewportView(Table);

        Menambahkan.setText("ADD");
        Menambahkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenambahkanActionPerformed(evt);
            }
        });

        Laporan.setText("LAPORAN");
        Laporan.setPreferredSize(new java.awt.Dimension(77, 22));
        Laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LaporanActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(0, 255, 153));

        kotakUM.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        kotakUM.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        kotakUM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kotakUMActionPerformed(evt);
            }
        });

        teksUM.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        teksUM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        teksUM.setText("UANG MASUK");

        iconUM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconUM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/ICON02.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kotakUM)
                    .addComponent(teksUM, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(iconUM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconUM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(teksUM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kotakUM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jPanel6.setBackground(new java.awt.Color(0, 204, 255));

        kotakUS.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        kotakUS.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        kotakUS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kotakUSActionPerformed(evt);
            }
        });

        teksUS.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        teksUS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        teksUS.setText("UANG SEKARANG");

        iconUS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconUS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/ICON01.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kotakUS)
                    .addComponent(teksUS, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(iconUS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconUS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(teksUS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kotakUS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jPanel8.setBackground(new java.awt.Color(255, 0, 0));

        kotakUK.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        kotakUK.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        kotakUK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kotakUKActionPerformed(evt);
            }
        });

        teksUK1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        teksUK1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        teksUK1.setText("UANG KELUAR");

        iconUK1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconUK1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/ICON03.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kotakUK)
                    .addComponent(teksUK1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(iconUK1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconUK1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(teksUK1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kotakUK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jPanel3.setBackground(new java.awt.Color(255, 153, 0));

        kotakUBI.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        kotakUBI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        kotakUBI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kotakUBIActionPerformed(evt);
            }
        });

        teksUBI.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        teksUBI.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        teksUBI.setText("UANG BULAN INI");

        iconUBI.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconUBI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/ICON04.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kotakUBI)
                    .addComponent(teksUBI, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(iconUBI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconUBI, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(teksUBI)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kotakUBI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(Menambahkan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Menghapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Mengubah, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Segar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Laporan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(DonutChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(DonutChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(Menambahkan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Menghapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Mengubah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Segar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Laporan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)))
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SegarActionPerformed
        try {
        loadTable();
        tampilkanChart();
        updateDisplayKeuangan();

        JOptionPane.showMessageDialog(this, "Berhasil merefresh!");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat refresh.");
    }
    }//GEN-LAST:event_SegarActionPerformed

    private void MengubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MengubahActionPerformed
        int row = Table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit!");
            return;
        }

        int id = (int) Table.getValueAt(row, 3);

        String jumlahText = Table.getValueAt(row, 0).toString();
        int masukLama = 0, keluarLama = 0;
        if (jumlahText.startsWith("+")) masukLama = Integer.parseInt(jumlahText.substring(2).trim());
        else if (jumlahText.startsWith("-")) keluarLama = Integer.parseInt(jumlahText.substring(2).trim());

        String tanggalLama = Table.getValueAt(row, 1).toString();
        String ketLama = Table.getValueAt(row, 2).toString();

        JTextField txtMasuk = new JTextField(String.valueOf(masukLama));
        JTextField txtKeluar = new JTextField(String.valueOf(keluarLama));
        JTextField txtTanggal = new JTextField(tanggalLama);
        JTextField txtKet = new JTextField(ketLama);

        Object[] form = {
            "Uang Masuk:", txtMasuk,
            "Uang Keluar:", txtKeluar,
            "Tanggal:", txtTanggal,
            "Keterangan:", txtKet
        };

        int hasil = JOptionPane.showConfirmDialog(this, form, "Edit Data", JOptionPane.OK_CANCEL_OPTION);
        if (hasil != JOptionPane.OK_OPTION) return;

        try {
            int masukBaru = Integer.parseInt(txtMasuk.getText().trim());
            int keluarBaru = Integer.parseInt(txtKeluar.getText().trim());
            String tanggalBaru = txtTanggal.getText().trim();
            String ketBaru = txtKet.getText().trim();

            userDBK.updateDataById(id, masukBaru, keluarBaru, tanggalBaru, ketBaru);

            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
            loadTable();
            tampilkanChart();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengubah data!\n" + ex.getMessage());
        }
    }//GEN-LAST:event_MengubahActionPerformed

    private void MenghapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenghapusActionPerformed
        int row = Table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dulu!");
            return;
        }

        int id = (int) Table.getValueAt(row, 3);

        int ok = JOptionPane.showConfirmDialog(this, "Hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            userDBK.deleteDataById(id);
            loadTable();
            tampilkanChart();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
        }
    }//GEN-LAST:event_MenghapusActionPerformed

    private void MenambahkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenambahkanActionPerformed
        JPanel panel = new JPanel();
        panel.setLayout(new java.awt.GridLayout(0, 1));

        String[] jenis = {"Uang Masuk", "Uang Keluar"};
        JComboBox<String> boxJenis = new JComboBox<>(jenis);

        String tanggalHariIni = new java.text.SimpleDateFormat("yyyy-MM-dd")
        .format(new java.util.Date());
        JTextField txtTanggal = new JTextField(tanggalHariIni);
        JTextField txtJumlah = new JTextField();
        JTextField txtKet = new JTextField();

        panel.add(new JLabel("Jenis Transaksi:"));
        panel.add(boxJenis);

        panel.add(new JLabel("Jumlah Uang:"));
        panel.add(txtJumlah);

        panel.add(new JLabel("Tanggal:"));
        panel.add(txtTanggal);

        panel.add(new JLabel("Keterangan:"));
        panel.add(txtKet);

        int result = JOptionPane.showConfirmDialog(
            this, panel,
            "Tambah Data Keuangan",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                int jumlah = Integer.parseInt(txtJumlah.getText());
                String tanggal = txtTanggal.getText();
                String ket = txtKet.getText();

                int masuk = 0;
                int keluar = 0;

                if (boxJenis.getSelectedItem().equals("Uang Masuk")) {
                    masuk = jumlah;
                } else {
                    keluar = jumlah;
                }

                userDBK.insertData(masuk, keluar, tanggal, ket);

                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");

                loadTable();        
                tampilkanChart();   

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Jumlah uang harus berupa angka!\n" + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Terjadi kesalahan!\n" + ex.getMessage());
            }
        }
    }//GEN-LAST:event_MenambahkanActionPerformed

    private void kotakUSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kotakUSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kotakUSActionPerformed

    private void kotakUMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kotakUMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kotakUMActionPerformed

    private void kotakUBIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kotakUBIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kotakUBIActionPerformed

    private void kotakUKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kotakUKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kotakUKActionPerformed

    private void LaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LaporanActionPerformed
        try {
            personalfinancemanagementsystem.Laporan laporan = 
                new personalfinancemanagementsystem.Laporan(userDBK, this);
            laporan.showLaporanDialog();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Gagal membuka dialog laporan!\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_LaporanActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Home().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DonutChart;
    private javax.swing.JButton Laporan;
    private javax.swing.JButton Menambahkan;
    private javax.swing.JButton Menghapus;
    private javax.swing.JButton Mengubah;
    private javax.swing.JButton Segar;
    private javax.swing.JTable Table;
    private javax.swing.JLabel iconUBI;
    private javax.swing.JLabel iconUK1;
    private javax.swing.JLabel iconUM;
    private javax.swing.JLabel iconUS;
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField kotakUBI;
    private javax.swing.JTextField kotakUK;
    private javax.swing.JTextField kotakUM;
    private javax.swing.JTextField kotakUS;
    private javax.swing.JLabel teksUBI;
    private javax.swing.JLabel teksUK1;
    private javax.swing.JLabel teksUM;
    private javax.swing.JLabel teksUS;
    // End of variables declaration//GEN-END:variables
}
