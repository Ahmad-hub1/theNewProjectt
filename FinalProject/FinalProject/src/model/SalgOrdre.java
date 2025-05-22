package model;

import java.time.LocalDate;

public class SalgOrdre {
    private int id;
    private String kundeNavn;
    private LocalDate ordreDato;

    public SalgOrdre(int id, String kundeNavn, LocalDate ordreDato) {
        this.id = id;
        this.kundeNavn = kundeNavn;
        this.ordreDato = ordreDato;
    }

    public int getId() {
        return id;
    }

    public String getKundeNavn() {
        return kundeNavn;
    }

    public LocalDate getOrdreDato() {
        return ordreDato;
    }


    @Override
	public String toString() {
        return "SalgOrdre [id=" + id + ", kundeNavn=" + kundeNavn + ", ordreDato=" + ordreDato + "]";
    }
}
