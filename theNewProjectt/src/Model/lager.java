package Model;

public class lager {
	private int lagerID;
	private String Navn;
	private String location;

	public lager(int lagerID, String Navn, String location) {
		this.lagerID = lagerID;
		this.Navn = Navn;
		this.location = location;
	}

	public int getLagerID() {
		return lagerID;
	}

	public void setLagerID(int lagerID) {
		this.lagerID = lagerID;
	}

	public String getNavn() {
		return Navn;
	}

	public void setNavn(String Navn) {
		this.Navn = Navn;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation ( String  location) {
		this.location = location;
	
	}
	@Override
	public String toString() {
		return "lager [lagerID=" + lagerID + ",Navn=" + Navn +",location =" + location + "]";
		
	
	}
}

