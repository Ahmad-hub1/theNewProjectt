package Model;

import java.time.LocalDateTime;

public class Reservation {
	private int reservationId;
	private LocalDateTime fraDato;
	private LocalDateTime tilDato;
	private boolean status;

	public Reservation(int reservationId, LocalDateTime fraDato, LocalDateTime tilDato, boolean status) {
		this.reservationId = reservationId;
		this.fraDato = fraDato;
		this.tilDato = tilDato;
		this.status = status;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public LocalDateTime getFraDato() {
		return fraDato;
	}

	public void setFraDato(LocalDateTime fraDato) {
		this.fraDato = fraDato;
	}

	public LocalDateTime getTilDato() {
		return tilDato;
	}

	public void setTilDato(LocalDateTime tilDato) {
		this.tilDato = tilDato;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Reservation [reservationId=" + reservationId + ", fraDato=" + fraDato + ", tilDato=" + tilDato
				+ ", status=" + status + "]";
	}
}
