package Model;

public class orderLine {
	private int orderLineId;
	private int quantity;
	private double linePrice;

	public orderLine(int orderLineId, int quantity, double linePrice) {
		this.orderLineId = orderLineId;
		this.quantity = quantity;
		this.linePrice = linePrice;
	}

	public int getOrderLineId() {
		return orderLineId;
	}

	public void setOrderLineId(int orderLineId) {
		this.orderLineId = orderLineId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getLinePrice() {
		return linePrice;
	}

	public void setLinePrice(double linePrice) {
		this.linePrice = linePrice;
	}

	@Override
	public String toString() {
		return "orderLine [orderLineId=" + orderLineId + ", quantity=" + quantity + ", linePrice=" + linePrice + "]";
	}
}
