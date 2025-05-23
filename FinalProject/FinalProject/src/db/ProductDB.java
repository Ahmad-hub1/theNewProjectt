package db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Product;

public class ProductDB {
    private Connection connection;

    public ProductDB() throws SQLException {
        this.connection = DBConnection.getInstance().getConnection();
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Connection not available");
        }
        ensureLocationColumnExists();
        ensureReservationColumnsExist();
    }

    private void ensureLocationColumnExists() {
        String check  = "SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS "
                      + "WHERE TABLE_NAME='Product' AND COLUMN_NAME='Location'";
        String alter  = "ALTER TABLE dbo.Product ADD Location NVARCHAR(100)";
        try (PreparedStatement ps = connection.prepareStatement(check);
             ResultSet rs      = ps.executeQuery()) {
            if (!rs.next()) {
                try (PreparedStatement pst = connection.prepareStatement(alter)) {
                    pst.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ensureReservationColumnsExist() {
        String check1 = "SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS "
                      + "WHERE TABLE_NAME='Product' AND COLUMN_NAME='IsReserved'";
        String alter1 = "ALTER TABLE dbo.Product ADD IsReserved BIT DEFAULT 0";
        String check2 = "SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS "
                      + "WHERE TABLE_NAME='Product' AND COLUMN_NAME='ReservationDate'";
        String alter2 = "ALTER TABLE dbo.Product ADD ReservationDate DATE NULL";

        // Opret IsReserved
        try (PreparedStatement ps1 = connection.prepareStatement(check1);
             ResultSet rs1      = ps1.executeQuery()) {
            if (!rs1.next()) {
                try (PreparedStatement pst = connection.prepareStatement(alter1)) {
                    pst.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Opret ReservationDate
        try (PreparedStatement ps2 = connection.prepareStatement(check2);
             ResultSet rs2      = ps2.executeQuery()) {
            if (!rs2.next()) {
                try (PreparedStatement pst = connection.prepareStatement(alter2)) {
                    pst.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> findAll() throws SQLException {
        String sql = "SELECT TOP (1000) ProductID, ProductName, Location, IsReserved, ReservationDate "
                   + "FROM dbo.Product";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs      = ps.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(
                    rs.getInt("ProductID"),
                    rs.getString("ProductName"),
                    rs.getString("Location"),
                    rs.getBoolean("IsReserved"),
                    rs.getDate("ReservationDate")
                ));
            }
        }
        return products;
    }

    public Product findById(int productId) throws SQLException {
        String sql = "SELECT ProductID, ProductName, Location, IsReserved, ReservationDate "
                   + "FROM dbo.Product WHERE ProductID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Location"),
                        rs.getBoolean("IsReserved"),
                        rs.getDate("ReservationDate")
                    );
                }
            }
        }
        return null;
    }

    public boolean productExists(int productId) throws SQLException {
        String sql = "SELECT 1 FROM dbo.Product WHERE ProductID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void reserveProduct(int productId) throws SQLException {
        String sql = "UPDATE dbo.Product "
                   + "SET IsReserved = 1, ReservationDate = GETDATE() "
                   + "WHERE ProductID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Produkt ikke fundet eller ikke opdateret");
            }
        }
    }

    public void unreserveProduct(int productId) throws SQLException {
        String sql = "UPDATE dbo.Product "
                   + "SET IsReserved = 0, ReservationDate = NULL "
                   + "WHERE ProductID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Produkt ikke fundet");
            }
        }
    }


    public int createProduct(String productName, String location) throws SQLException {
        String sql = "INSERT INTO dbo.Product (ProductName, Location) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, productName);
            ps.setString(2, location);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Kan ikke oprette produkt");
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                } else {
                    throw new SQLException("Ingen nøgle genereret");
                }
            }
        }
    }
}
