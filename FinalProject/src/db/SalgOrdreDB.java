package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;



public class SalgOrdreDB<SalesOrderDB> implements SalgOrdreDBIF {

	private static final String AddCustomerById = "createSalesOrder(?,?,?,?,?,?,?,?,?)";
	private static final String addProductByProductId1 ="select from Order where  = ?";

	private PreparedStatement AddCustomerByPhoneNo;
	private PreparedStatement addProductByProductId;

	private DBConnection dbconnection;

	public SalgOrdreDB() {
		dbconnection = DBConnection.getInstance();
		try {
			dbconnection.getConnection().prepareStatement(AddCustomerById, Statement.KEEP_CURRENT_RESULT);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@Override
	public SalgOrdreDB findOrdreByOrdreId(int orderId, boolean retrieveAssociation) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<SalgOrdreDB> findAllOrders(boolean retrieveAssociation) {
		// TODO Auto-generated method stub
		return null;
	}


	public static String getAddproductbyproductid1() {
		return addProductByProductId1;
	}


	public PreparedStatement getAddCustomerByPhoneNo() {
		return AddCustomerByPhoneNo;
	}


	public void setAddCustomerByPhoneNo(PreparedStatement addCustomerByPhoneNo) {
		AddCustomerByPhoneNo = addCustomerByPhoneNo;
	}


	public PreparedStatement getAddProductByProductId() {
		return addProductByProductId;
	}


	public void setAddProductByProductId(PreparedStatement addProductByProductId) {
		this.addProductByProductId = addProductByProductId;
	}




}
