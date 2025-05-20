package Model;

public class customer {
    private int customerId;
    private String navn;
    private String telefon;
    private String email;
    private String adresse;

    public customer(String navn) {
        this.navn = navn;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Customer {" +
                "customerId=" + customerId +
                ", navn='" + navn + '\'' +
                ", telefon='" + telefon + '\'' +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
