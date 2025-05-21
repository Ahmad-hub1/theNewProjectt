package db;


import java.util.List;

public interface SalgOrdreDBIF {

	List<SalgOrdreDB> findAllOrders(boolean retrieveAssociation);



	SalgOrdreDB findOrdreByOrdreId(int orderId, boolean retrieveAssociation);

}

