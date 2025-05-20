package Model;

public class stock {
	private boolean isAvalable;
	private int quantity;

	public stock (boolean isAvalable, int quantity) {
		this.isAvalable = isAvalable;
		this.quantity = quantity;
	}

	public boolean isAvalable() {
		return isAvalable;
	}

	public void setAvalable(boolean isAvalable) {
		this.isAvalable = isAvalable;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "stock [isAvalable=" + isAvalable + ", quantity=" + quantity + "]";
	}
}
