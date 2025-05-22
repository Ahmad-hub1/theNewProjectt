package controller;
import java.util.List;

import db.SalgOrdreDB;
import model.customer;

public class SalgOrdreController {

	private ProductController productController;
	private CustomerController customerController;

	public SalgOrdreController() {
		setProductController(new ProductController());
		setCustomerController(new CustomerController());
	}

	public static List<SalgOrdreDB> getSalesOrderList(customer customer, boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProductController getProductController() {
		return productController;
	}

	public void setProductController(ProductController productController) {
		this.productController = productController;
	}

	public CustomerController getCustomerController() {
		return customerController;
	}

	public void setCustomerController(CustomerController customerController) {
		this.customerController = customerController;
	}

	}


