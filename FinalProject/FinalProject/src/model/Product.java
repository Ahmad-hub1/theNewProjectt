package model;

import java.sql.Date;

public class Product {
    private int productId;
    private String productName;
    private String location;
    private boolean isReserved;
    private Date reservationDate;


    public Product(int productId, String productName, String location, boolean isReserved, Date reservationDate) {
        this.productId = productId;
        this.productName = productName;
        this.location = location;
        this.isReserved = isReserved;
        this.reservationDate = reservationDate;
    }


    public Product(int productId, String productName, String location, boolean isReserved) {
        this(productId, productName, location, isReserved, null);
    }


    public Product(int productId, String productName, String location) {
        this(productId, productName, location, false, null);
    }

    // getters

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getLocation() {
        return location;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    // Setters

    public void setReserved(boolean reserved) {
        this.isReserved = reserved;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    @Override
    public String toString() {
        return "Product [ID=" + productId +
               ", Name=" + productName +
               ", Location=" + location +
               ", Reserved=" + isReserved +
               (reservationDate != null ? ", ReservationDate=" + reservationDate : "") +
               "]";
    }
}
