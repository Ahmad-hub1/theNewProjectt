package Gui;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import db.ProductDB;
import model.Product;

public class Gui {

    private JFrame mainFrame;
    private ProductDB productDB;

    public Gui() {
        try {
            productDB = new ProductDB();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Kan ikke forbinde til databasen: " + e.getMessage(),
                "Databasefejl", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    //btn = button
    private void createAndShowGUI() {
        mainFrame = new JFrame("Forside");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);
        mainFrame.setLocationRelativeTo(null);

        // Load and show image
        ImageIcon icon = loadIcon("/Gui/resources/forsidebillede.png", 300, 100);
        JLabel billedeLabel = new JLabel(icon);
        billedeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnSøgProdukt   = new JButton("Søg/Reservér Produkt");
        JButton btnOpretProdukt = new JButton("Opret Produkt");

        btnSøgProdukt.addActionListener(e -> openProductSearchWindow());
        btnOpretProdukt.addActionListener(e -> openCreateProductWindow());

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(btnSøgProdukt);
        panel.add(btnOpretProdukt);

        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(billedeLabel, BorderLayout.NORTH);
        mainFrame.add(panel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private ImageIcon loadIcon(String path, int w, int h) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            ImageIcon ori = new ImageIcon(imgURL);
            Image img = ori.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return new ImageIcon();
    }

    private void openCreateProductWindow() {
        JFrame win = new JFrame("Opret Produkt");
        win.setSize(350, 250);
        win.setLocationRelativeTo(mainFrame);
        win.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        JLabel lblName     = new JLabel("Produktnavn:");
        JTextField tfName  = new JTextField(15);
        JLabel lblLoc      = new JLabel("Location:");
        JTextField tfLoc   = new JTextField(10);
        JButton btnOpret   = new JButton("Opret");
        JLabel lblResult   = new JLabel(" ");
        JButton btnTilbage = new JButton("Tilbage");

        // Layout fields
        gbc.gridx=0; gbc.gridy=0; gbc.anchor=GridBagConstraints.LINE_END;
        win.add(lblName, gbc);
        gbc.gridx=1; gbc.anchor=GridBagConstraints.LINE_START;
        win.add(tfName, gbc);

        gbc.gridx=0; gbc.gridy=1; gbc.anchor=GridBagConstraints.LINE_END;
        win.add(lblLoc, gbc);
        gbc.gridx=1; gbc.anchor=GridBagConstraints.LINE_START;
        win.add(tfLoc, gbc);

        // Opret button
        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=2; gbc.anchor=GridBagConstraints.CENTER;
        win.add(btnOpret, gbc);

        // Result label
        gbc.gridy=3;
        win.add(lblResult, gbc);

        // Tilbage button
        gbc.gridy=4;
        win.add(btnTilbage, gbc);

        btnOpret.addActionListener(e -> {
            String name = tfName.getText().trim();
            String loc  = tfLoc.getText().trim();
            if (name.isEmpty() || loc.isEmpty()) {
                lblResult.setText("Udfyld både navn og location.");
                return;
            }
            try {
                int newId = productDB.createProduct(name, loc);
                lblResult.setText("Oprettet! Ny ID = " + newId);
                tfName.setText("");
                tfLoc.setText("");
            } catch (SQLException ex) {
                lblResult.setText("Fejl: " + ex.getMessage());
            }
        });

        btnTilbage.addActionListener(e -> win.dispose());

        win.setVisible(true);
    }

    private void openProductSearchWindow() {
        JFrame frame = new JFrame("Søg efter Produkt");
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(mainFrame);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        JLabel label        = new JLabel("Indtast ProductID:");
        JTextField field    = new JTextField(10);
        JButton btnSøg      = new JButton("Søg");
        JLabel lblResult    = new JLabel(" ");
        JButton btnReserver = new JButton("Reserver Produkt");
        JButton btnFortryd  = new JButton("Fortryd Reservation");
        JButton btnTilbage  = new JButton("Tilbage");

        btnReserver.setEnabled(false);
        btnFortryd.setEnabled(false);

        // Layout
        gbc.gridx=0; gbc.gridy=0; gbc.anchor=GridBagConstraints.LINE_END;
        frame.add(label, gbc);
        gbc.gridx=1; gbc.anchor=GridBagConstraints.LINE_START;
        frame.add(field, gbc);

        gbc.gridx=0; gbc.gridy=1; gbc.gridwidth=2; gbc.anchor=GridBagConstraints.CENTER;
        frame.add(btnSøg, gbc);

        gbc.gridy=2;
        frame.add(lblResult, gbc);

        gbc.gridy=3; gbc.gridwidth=1; gbc.anchor=GridBagConstraints.CENTER;
        frame.add(btnReserver, gbc);
        gbc.gridx=1;
        frame.add(btnFortryd, gbc);

        gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=2;
        frame.add(btnTilbage, gbc);

        final Product[] current = new Product[1];

        // Søg-knap
        btnSøg.addActionListener(e -> {
            lblResult.setText(" ");
            btnReserver.setEnabled(false);
            btnFortryd.setEnabled(false);
            try {
                int id = Integer.parseInt(field.getText().trim());
                if (!productDB.productExists(id)) {
                    lblResult.setText("Produkt med ID " + id + " findes ikke.");
                    return;
                }
                Product p = productDB.findById(id);
                current[0] = p;
                String loc = p.getLocation();
                if (loc != null && loc.toLowerCase().startsWith("hal ")) {
                    loc = loc.substring(4);
                }
                StringBuilder html = new StringBuilder("<html>")
                    .append(p.getProductName())
                    .append("<br>Hal: ").append(loc)
                    .append("<br>Reserveret: ").append(p.isReserved() ? "Ja" : "Nej");
                if (p.isReserved() && p.getReservationDate() != null) {
                    html.append("<br>Dato: ").append(p.getReservationDate());
                }
                html.append("</html>");
                lblResult.setText(html.toString());

                btnReserver.setEnabled(!p.isReserved());
                btnFortryd.setEnabled(p.isReserved());
            } catch (NumberFormatException ex) {
                lblResult.setText("ProductID skal være et helt tal.");
            } catch (SQLException ex) {
                lblResult.setText("DB-fejl: " + ex.getMessage());
            }
        });

        // Reserver-knap
        btnReserver.addActionListener(e -> {
            try {
                productDB.reserveProduct(current[0].getProductId());
                lblResult.setText("Produktet er nu reserveret.");
                btnReserver.setEnabled(false);
                btnFortryd.setEnabled(true);
            } catch (SQLException ex) {
                lblResult.setText("Fejl ved reservation: " + ex.getMessage());
            }
        });

        // Fortryd-knap
        btnFortryd.addActionListener(e -> {
            try {
                productDB.unreserveProduct(current[0].getProductId());
                lblResult.setText("Reservationen er fortrudt.");
                btnReserver.setEnabled(true);
                btnFortryd.setEnabled(false);
            } catch (SQLException ex) {
                lblResult.setText("Fejl ved fortryd: " + ex.getMessage());
            }
        });

        // Tilbage-knap
        btnTilbage.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gui().createAndShowGUI());
    }
}
