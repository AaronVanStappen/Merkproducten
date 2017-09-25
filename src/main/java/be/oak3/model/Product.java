package be.oak3.model;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.Comparator;

public abstract class Product implements Comparable<Product> {
    private int productNummer;
    private String merk;
    private String naam;
    private int volume;
    private double prijs;

    public Product(int productNummer, String merk, String naam, int volume, double prijs) {
        this.setProductNummer(productNummer);
        this.merk = merk;
        this.naam = naam;
        this.volume = volume;
        this.prijs = prijs;
    }

    public void setProductNummer(int nr) {
        this.productNummer = nr;
    }

    public int getProductNummer() {
        return productNummer;
    }

    public String getMerk() {
        return merk;
    }

    public String getNaam() {
        return naam;
    }

    public int getVolume() {
        return volume;
    }

    public double getPrijs() {
        return prijs;
    }

    public String getProductCode() {
        return join(left(merk, 3), left(naam, 3), volume).replaceAll(" ", "_")
                          .toUpperCase();
    }

    public static Comparator<Product> sorteerOpMerknaam() {
        return Comparator.comparing(Product::getMerk);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product)) {
            return false;
        }

        Product p = (Product) o;
        return this.productNummer == p.getProductNummer();
    }

    @Override
    public int hashCode() {
        return this.productNummer;
    }

    @Override
    public int compareTo(Product p) {
        return this.getProductNummer() - p.getProductNummer();
    }

    @Override
    public String toString() {
        return String.format("%d %s %-20s %10s %-24s %10s %3sml %8s %4.2f %5s %s",
                getProductNummer(), "Merk:", getMerk(), "Naam:", getNaam(), "Volume:", getVolume(),
                "Prijs:", getPrijs(), "Code:", getProductCode()) + "\n";
    }
}
