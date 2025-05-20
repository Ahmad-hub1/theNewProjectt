package Model;

import java.time.LocalDate;

public class salgOrdre { 
	private int ordreId;
	private LocalDate ordreDate;
	private double salgsPris;
	private String betaling;
	
	
public salgOrdre (int ordreId, LocalDate ordreDate, double salgsPris,String betaling){
	this.ordreId = ordreId;
	this.ordreDate = ordreDate;
	this.salgsPris = salgsPris;
	this.betaling = betaling;
}
	
public int getOrdreId() {
	return ordreId;
}

public void setOrdreId (int ordreId){
	this.ordreId = ordreId;
	}

public LocalDate getOrdreDate() {
	return ordreDate;
}

public void setOrdreDate(LocalDate ordreDate) {
	this.ordreDate = ordreDate;
}

public double getSalgsPris() {
	return salgsPris;
}

public void setSalgsPris(double salgsPris) {
	this.salgsPris = salgsPris;
}

public String getBetaling() {
	return betaling;
}

public void setBetaling(String betaling) {
	this.betaling = betaling;
}

@Override
public String toString() {
	return "salgOrdre [ordreId=" + ordreId + ", ordreDate=" + ordreDate + ", salgsPris=" + salgsPris + ", betaling="
			+ betaling + "]";
}



}
