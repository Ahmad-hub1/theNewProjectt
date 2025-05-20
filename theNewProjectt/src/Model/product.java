package Model;

import java.time.LocalDateTime;

public class product {
	private int productId;
	private String navn;
	private String beskrivelse;
	private String kategori;
	private double pris;
	private String status;
	private String placering;
	private LocalDateTime oprettelsesdato;

	public product(int productId, String navn, String beskrivelse, String kategori,
			double pris, String status, String placering, LocalDateTime oprettelsesdato) {
		this.productId = productId;
		this.navn = navn;
		this.beskrivelse = beskrivelse;
		this.kategori = kategori;
		this.pris = pris;
		this.status = status;
		this.placering = placering;
		this.oprettelsesdato = oprettelsesdato;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}

	public String getKategori() {
		return kategori;
	}

	public void setKategori(String kategori) {
		this.kategori = kategori;
	}

	public double getPris() {
		return pris;
	}

	public void setPris(double pris) {
		this.pris = pris;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPlacering() {
		return placering;
	}

	public void setPlacering(String placering) {
		this.placering = placering;
	}

	public LocalDateTime getOprettelsesdato() {
		return oprettelsesdato;
	}

	public void setOprettelsesdato(LocalDateTime oprettelsesdato) {
		this.oprettelsesdato = oprettelsesdato;
	}

	@Override
	public String toString() {
		return "product [productId=" + productId + ", navn=" + navn + ", beskrivelse=" + beskrivelse + ", kategori="
				+ kategori + ", pris=" + pris + ", status=" + status + ", placering=" + placering
				+ ", oprettelsesdato=" + oprettelsesdato + "]";
	}
}

