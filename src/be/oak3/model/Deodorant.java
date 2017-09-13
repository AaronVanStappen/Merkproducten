package be.oak3.model;

public class Deodorant extends Product {
    public enum DeoType {VAPO, STICK};
    private DeoType soort;

    public Deodorant(int productNr, String merk, String naam, int volume, double prijs, DeoType soort) {
        super(productNr, merk, naam, volume, prijs);
        this.soort = soort;
    }
}
