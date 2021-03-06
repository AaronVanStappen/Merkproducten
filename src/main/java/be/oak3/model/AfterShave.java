package be.oak3.model;

public class AfterShave extends Product {
    public enum Soort {VAPO, GEL}

    private Soort soort;

    public AfterShave(int productNr, String merk, String naam, int volume, double prijs, Soort soort) {
        super(productNr, merk, naam, volume, prijs);
        this.soort = soort;
    }

    public Soort getSoort() {
        return soort;
    }

    @Override
    public String toString() {
        return super.toString() + "\t" + soort.toString();
    }
}
