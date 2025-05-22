package Gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

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

    private void createAndShowGUI() {
        mainFrame = new JFrame("Forside");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 300);
        mainFrame.setLocationRelativeTo(null);

        ImageIcon icon = loadIcon("/Gui/resources/forsidebillede.png", 300, 100);
        JLabel billedeLabel = new JLabel(icon);
        billedeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnSogProdukt   = new JButton("Søg/Reservér Produkt");
        JButton btnOpretProdukt = new JButton("Opret Produkt");
        JButton btnOpretOrdre   = new JButton("Opret Ordre");

        btnSogProdukt.addActionListener(e -> openProductSearchWindow());
        btnOpretProdukt.addActionListener(e -> openCreateProductWindow());
        btnOpretOrdre.addActionListener(e -> openCreateOrderWindow());

        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(btnSogProdukt);
        panel.add(btnOpretProdukt);
        panel.add(btnOpretOrdre);

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

        gbc.gridx=0; gbc.gridy=0; gbc.anchor=GridBagConstraints.LINE_END;
        win.add(lblName, gbc);
        gbc.gridx=1; gbc.anchor=GridBagConstraints.LINE_START;
        win.add(tfName, gbc);

        gbc.gridx=0; gbc.gridy=1; gbc.anchor=GridBagConstraints.LINE_END;
        win.add(lblLoc, gbc);
        gbc.gridx=1; gbc.anchor=GridBagConstraints.LINE_START;
        win.add(tfLoc, gbc);

        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=2; gbc.anchor=GridBagConstraints.CENTER;
        win.add(btnOpret, gbc);

        gbc.gridy=3;
        win.add(lblResult, gbc);

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
        JButton btnSog      = new JButton("Søg");
        JLabel lblResult    = new JLabel(" ");
        JButton btnReserver = new JButton("Reserver Produkt");
        JButton btnFortryd  = new JButton("Fortryd Reservation");
        JButton btnTilbage  = new JButton("Tilbage");

        btnReserver.setEnabled(false);
        btnFortryd.setEnabled(false);

        gbc.gridx=0; gbc.gridy=0; gbc.anchor=GridBagConstraints.LINE_END;
        frame.add(label, gbc);
        gbc.gridx=1; gbc.anchor=GridBagConstraints.LINE_START;
        frame.add(field, gbc);

        gbc.gridx=0; gbc.gridy=1; gbc.gridwidth=2; gbc.anchor=GridBagConstraints.CENTER;
        frame.add(btnSog, gbc);

        gbc.gridy=2;
        frame.add(lblResult, gbc);

        gbc.gridy=3; gbc.gridwidth=1; gbc.anchor=GridBagConstraints.CENTER;
        frame.add(btnReserver, gbc);
        gbc.gridx=1;
        frame.add(btnFortryd, gbc);

        gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=2;
        frame.add(btnTilbage, gbc);

        final Product[] current = new Product[1];

        btnSog.addActionListener(e -> {
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

        btnTilbage.addActionListener(e -> frame.dispose());
        frame.setVisible(true);
    }

    private void openCreateOrderWindow() {
        JFrame frame = new JFrame("Opret Ordre");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(mainFrame);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        List<OrderLine> orderLines = new ArrayList<>();

        JLabel lblSearch   = new JLabel("Produkt-ID:");
        JTextField tfId    = new JTextField(6);
        JLabel lblQty      = new JLabel("Antal:");
        JTextField tfQty   = new JTextField(4);
        JButton btnAdd     = new JButton("Tilføj til ordre");
        JTextArea taOrder  = new JTextArea(10, 30);
        taOrder.setEditable(false);
        JButton btnGem     = new JButton("Gem Ordre");
        JButton btnTilbage = new JButton("Tilbage");
        JLabel lblStatus   = new JLabel(" ");

        gbc.gridx = 0; gbc.gridy = 0;
        frame.add(lblSearch, gbc);
        gbc.gridx = 1;
        frame.add(tfId, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        frame.add(lblQty, gbc);
        gbc.gridx = 1;
        frame.add(tfQty, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        frame.add(btnAdd, gbc);
        gbc.gridy = 3;
        frame.add(new JScrollPane(taOrder), gbc);
        gbc.gridy = 4;
        frame.add(btnGem, gbc);
        gbc.gridy = 5;
        frame.add(lblStatus, gbc);
        gbc.gridy = 6;
        frame.add(btnTilbage, gbc);

        btnAdd.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tfId.getText().trim());
                int qty = Integer.parseInt(tfQty.getText().trim());
                if (qty <= 0) {
					throw new NumberFormatException("Antal skal være > 0");
				}

                if (!productDB.productExists(id)) {
                    lblStatus.setText("Produkt ikke fundet.");
                    return;
                }

                Product p = productDB.findById(id);
                OrderLine ol = new OrderLine(p, qty);
                orderLines.add(ol);
                taOrder.append(ol + "\n");

                tfId.setText("");
                tfQty.setText("");
                lblStatus.setText("Produkt tilføjet.");

            } catch (NumberFormatException ex) {
                lblStatus.setText("Ugyldigt ID eller antal.");
            } catch (SQLException ex) {
                lblStatus.setText("DB-fejl: " + ex.getMessage());
            }
        });

        btnGem.addActionListener(e -> {
            if (orderLines.isEmpty()) {
                lblStatus.setText("Ordren er tom.");
                return;
            }
            JOptionPane.showMessageDialog(frame,
                "Ordre gemt med " + orderLines.size() + " produkt(er).",
                "Ordre afsluttet", JOptionPane.INFORMATION_MESSAGE);
            orderLines.clear();
            taOrder.setText("");
            lblStatus.setText(" ");
        });

        btnTilbage.addActionListener(e -> frame.dispose());
        frame.setVisible(true);
    }

    private static class OrderLine {
        Product product;
        int quantity;
        OrderLine(Product p, int q) {
            this.product = p;
            this.quantity = q;
        }
        @Override
		public String toString() {
            return product.getProductName() + " x " + quantity;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gui().createAndShowGUI());
    }
}
