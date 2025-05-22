package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.SalgOrdre;

public class SalgOrdreDB implements SalgOrdreDBIF {
    private DBConnection dbconnection;

    public SalgOrdreDB() {
        dbconnection = DBConnection.getInstance();
    }

    @Override
    public SalgOrdre findOrdreByOrdreId(int orderId, boolean retrieveAssociation) {
        String sql = "SELECT * FROM SalesOrder WHERE id = ?";
        try (PreparedStatement ps = dbconnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String kundeNavn = rs.getString("customer_name");
                LocalDate ordreDato = rs.getDate("order_date").toLocalDate();

                return new SalgOrdre(id, kundeNavn, ordreDato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SalgOrdre> findAllOrders(boolean retrieveAssociation) {
        List<SalgOrdre> orders = new ArrayList<>();
        String sql = "SELECT * FROM SalesOrder";

        try (Statement stmt = dbconnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String kundeNavn = rs.getString("customer_name");
                LocalDate ordreDato = rs.getDate("order_date").toLocalDate();

                orders.add(new SalgOrdre(id, kundeNavn, ordreDato));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
