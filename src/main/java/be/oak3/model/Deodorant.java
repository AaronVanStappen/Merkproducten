package be.oak3.model;

public class Deodorant extends Product {
    public enum DeoType {VAPO, STICK};
    public DeoType soort;

    public Deodorant(int productNr, String merk, String naam, int volume, double prijs, DeoType soort) {
        super(productNr, merk, naam, volume, prijs);
        this.soort = soort;
    }

    @Override
    public String toString() {
        return super.toString() + "\t" + soort.toString();
    }
}
