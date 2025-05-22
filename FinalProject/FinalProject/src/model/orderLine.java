package model;

public class orderLine {

	Product product;
	int quantity;
	orderLine(Product p, int q) {
		this.product = p;
		this.quantity = q;
	}
	public String ToString() {
		return product.getProductName() + "x" + quantity;
	}
}
