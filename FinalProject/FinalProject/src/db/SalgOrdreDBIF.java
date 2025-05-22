package db;

import java.util.List;

import model.SalgOrdre;

public interface SalgOrdreDBIF {
    List<SalgOrdre> findAllOrders(boolean retrieveAssociation);
    SalgOrdre findOrdreByOrdreId(int orderId, boolean retrieveAssociation);
}
